/**
 *  Dec 13, 2012
 */
package com.fs.gridservice.core.impl.hazelcast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.support.AttachableSupport;
import com.fs.commons.api.util.CollectionUtil;
import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.core.api.DataGridI;
import com.fs.gridservice.core.api.DgObjectI;
import com.fs.gridservice.core.api.event.BeforeDgCloseEvent;
import com.fs.gridservice.core.api.objects.DgMapI;
import com.fs.gridservice.core.api.objects.DgQueueI;
import com.fs.gridservice.core.api.objects.DgSetI;
import com.fs.gridservice.core.api.objects.DgTopicI;
import com.fs.gridservice.core.impl.hazelcast.codec.MessageData;
import com.fs.gridservice.core.impl.hazelcast.codec.PropertiesData;
import com.fs.gridservice.core.impl.hazelcast.objects.DgMapHC;
import com.fs.gridservice.core.impl.hazelcast.objects.DgQueueHC;
import com.fs.gridservice.core.impl.hazelcast.objects.DgSetHC;
import com.fs.gridservice.core.impl.hazelcast.objects.DgTopicHC;
import com.fs.gridservice.core.impl.proxy.ProxyWrapperMap;
import com.fs.gridservice.core.impl.proxy.ProxyWrapperQueue;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.Instance;
import com.hazelcast.core.Prefix;

/**
 * @author wuzhen
 * 
 */
public class DataGridHC extends AttachableSupport implements DataGridI {

	protected HazelcastClient client;

	protected Map<Instance.InstanceType, Map<String, DgObjectI>> objectCache;

	protected Map<Instance.InstanceType, Class<? extends HazelcastObjectWrapper>> wrapperTypes;

	protected Map<Instance.InstanceType, String> prefixMap;

	protected DgFactoryHC factory;

	protected final ReentrantLock factoryLock = new ReentrantLock();

	/**
	 * @param client
	 */
	public DataGridHC(HazelcastClient client, DgFactoryHC df) {

		this.client = client;
		this.factory = df;
		this.objectCache = Collections
				.synchronizedMap(new HashMap<Instance.InstanceType, Map<String, DgObjectI>>());

		this.wrapperTypes = new HashMap<Instance.InstanceType, Class<? extends HazelcastObjectWrapper>>();
		this.prefixMap = new HashMap<Instance.InstanceType, String>();
		// QUEUE
		this.wrapperTypes.put(Instance.InstanceType.QUEUE, DgQueueHC.class);
		this.prefixMap.put(Instance.InstanceType.QUEUE, Prefix.QUEUE);

		// MAP
		this.wrapperTypes.put(Instance.InstanceType.MAP, DgMapHC.class);
		this.prefixMap.put(Instance.InstanceType.MAP, Prefix.MAP);

		// Set
		this.wrapperTypes.put(Instance.InstanceType.SET, DgSetHC.class);
		this.prefixMap.put(Instance.InstanceType.SET, Prefix.SET);

		// TOPIC
		this.wrapperTypes.put(Instance.InstanceType.TOPIC, DgTopicHC.class);
		this.prefixMap.put(Instance.InstanceType.TOPIC, Prefix.TOPIC);

		//
		PropertiesData.CODEC = this.factory.propertiesCodec;// NOTE static ?
		MessageData.CODEC = this.factory.messageCodec;
		//
	}

	/**
	 * Dec 13, 2012
	 */
	protected Object encode(Object t) {
		//
		if (t == null) {
			return t;
		}
		Class cls = t.getClass();
		if (PropertiesI.class.isAssignableFrom(cls)) {
			PropertiesI pts = (PropertiesI) t;
			PropertiesData rt = new PropertiesData();
			rt.setData(pts);//
			return rt;
		} else if (MessageI.class.isAssignableFrom(cls)) {
			MessageI msg = (MessageI) t;
			MessageData rt = new MessageData();
			rt.setData(msg);
			return rt;
		} else {// TODO check the types
			return t;// remain same.
		}
	}

	/**
	 * Dec 13, 2012
	 */
	protected <X> X decode(Object o) {
		//
		if (o == null) {
			return null;
		}
		Class cls = o.getClass();
		if (PropertiesData.class.equals(cls)) {

			return (X) ((PropertiesData) o).getData();

		} else if (MessageData.class.equals(cls)) {
			return (X) ((MessageData) o).getData();//
		} else {// NOTE todo ensure types
			return (X) o;
		}
	}

	@Override
	public <T> DgQueueI<T> getQueue(String name) {
		return this.getOrCreateDgObject(Instance.InstanceType.QUEUE, name);
	}

	@Override
	public <V, W> DgQueueI<W> getQueue(String name, Class<W> wcls1) {

		return this.getQueue(name, wcls1, wcls1);
	}

	@Override
	public <V, W> DgQueueI<W> getQueue(String name, Class<W> wcls1, Class<? extends W> wcls) {
		DgQueueI<V> t = this.getQueue(name);
		return new ProxyWrapperQueue(t, wcls);
	}

	public Map<String, DgObjectI> getObjectMapByType(Instance.InstanceType prefix) {
		Map<String, DgObjectI> rt = this.objectCache.get(prefix);
		if (rt == null) {
			synchronized (this.objectCache) {
				rt = this.objectCache.get(prefix);
				if (rt == null) {
					rt = new HashMap<String, DgObjectI>();
					this.objectCache.put(prefix, rt);
				}
			}
		}
		return rt;
	}

	public <T extends DgObjectI> T getOrCreateDgObject(final Instance.InstanceType type, final String name) {
		this.factoryLock.lock();
		try {
			return DataGridHC.this.getOrCreateDgObjectInternal(type, name);
		} finally {
			this.factoryLock.unlock();
		}
	}

	protected <T extends DgObjectI> T getOrCreateDgObjectInternal(Instance.InstanceType type, String name) {

		Map<String, DgObjectI> objects = this.getObjectMapByType(type);

		DgObjectI rt = objects.get(name);

		if (rt == null) {
			synchronized (objects) {
				rt = objects.get(name);
				if (rt == null) {
					String prefix = this.prefixMap.get(type);
					if (prefix == null) {
						throw new FsException("no prefix for type:" + type);
					}
					Object ins = this.client.getClientProxy(prefix + name);

					rt = this.objectWrap(name, type, ins);
					objects.put(name, rt);
				}
			}
		}
		return (T) rt;
		// end of sync
	}

	protected HazelcastObjectWrapper objectWrap(String name, Instance.InstanceType type, Object ins) {
		Class<? extends HazelcastObjectWrapper> wtype = this.wrapperTypes.get(type);
		if (wtype == null) {
			throw new FsException("no class registered for type:" + type);
		}
		HazelcastObjectWrapper rt = ClassUtil.newInstance(wtype, new Class[] { String.class, Instance.class,
				DataGridHC.class }, new Object[] { name, ins, this });

		return rt;
	}

	@Override
	public <K, V> DgMapI<K, V> getMap(String name) {

		return this.getOrCreateDgObject(Instance.InstanceType.MAP, name);

	}

	@Override
	public <K, V, W> DgMapI<K, W> getMap(String name, Class<W> wcls1) {
		return this.getMap(name, wcls1, wcls1);
	}

	@Override
	public <K, V, W> DgMapI<K, W> getMap(String name, Class<W> wcls1, Class<? extends W> wrapperClass) {
		DgMapI<K, V> t = this.getMap(name);

		return new ProxyWrapperMap(t, wrapperClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.DataGridI#getTopic(java.lang.String)
	 */
	@Override
	public <T> DgTopicI<T> getTopic(String name) {
		return this.getOrCreateDgObject(Instance.InstanceType.TOPIC, name);
	}

	@Override
	public <T> DgSetI<T> getSet(String name) {
		return this.getOrCreateDgObject(Instance.InstanceType.SET, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.DataGridI#getObjectList()
	 */
	@Override
	public List<DgObjectI> getObjectList() {
		final List<DgObjectI> rt = new ArrayList<DgObjectI>();
		this.forEachObject(new CallbackI<DgObjectI, Boolean>() {

			@Override
			public Boolean execute(DgObjectI i) {
				rt.add(i);
				return null;
			}
		});
		return rt;
	}

	@Override
	public <T extends DgObjectI> List<T> getObjectList(final Class<T> cls) {
		final List<T> rt = new ArrayList<T>();
		this.forEachObject(new CallbackI<DgObjectI, Boolean>() {

			@Override
			public Boolean execute(DgObjectI i) {
				if (cls.isInstance(i)) {
					rt.add((T) i);
				}

				return null;
			}
		});
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.DataGridI#getObject(java.lang.Class,
	 * java.lang.String)
	 */
	@Override
	public <T extends DgObjectI> List<T> getObjectList(final Class<T> cls, final String name) {
		final List<T> rt = new ArrayList<T>();
		this.forEachObject(new CallbackI<DgObjectI, Boolean>() {

			@Override
			public Boolean execute(DgObjectI i) {
				if (cls.isInstance(i) && (name == null || name.equals(i.getName()))) {
					rt.add((T) i);
				}

				return null;
			}
		});
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.DataGridI#getObject(java.lang.Class,
	 * java.lang.String, boolean)
	 */
	@Override
	public <T extends DgObjectI> T getObject(Class<T> cls, String name, boolean force) {
		List<T> rt = this.getObjectList(cls, name);
		return CollectionUtil.single(rt, force);

	}

	@Override
	public void forEachObject(CallbackI<DgObjectI, Boolean> cb) {

		for (Map<String, DgObjectI> omap : this.objectCache.values()) {
			boolean brk = false;
			for (DgObjectI o : omap.values()) {
				Boolean rt = cb.execute(o);
				if (rt != null && rt) {// break
					brk = true;
					break;
				}
			}
			if (brk) {
				break;
			}

		}

	}

	// This only be called from the wrapper.
	public void remove(HazelcastObjectWrapper ow) {
		Instance.InstanceType itype = ow.getTarget().getInstanceType();
		String name = ow.getName();
		this.factoryLock.lock();
		try {
			Map<String, DgObjectI> typeM = this.objectCache.get(itype);
			DgObjectI old = typeM.remove(name);
			if (old == null) {
				throw new FsException("no this object,type:" + itype + ",name:" + name);
			}

		} finally {
			this.factoryLock.unlock();
		}
	}

	@Override
	protected void doAttach() {

	}

	/*
	 * Dec 19, 2012
	 */
	@Override
	public void dump() {
		List<DgObjectI> rt = this.getObjectList();
		for (DgObjectI i : rt) {
			i.dump();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.gridservice.core.api.DataGridI#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// clean remains
		Collection<Instance> is = this.client.getInstances();

		return is.isEmpty();
	}

	public void close() {
		//
		new BeforeDgCloseEvent(this).dispatch(this.factory.getContainer());
		this.client.shutdown();
	}

}
