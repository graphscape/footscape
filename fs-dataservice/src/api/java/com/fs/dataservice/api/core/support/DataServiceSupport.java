/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.core.support;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.lang.FsException;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.operations.NodeDeleteOperationI;
import com.fs.dataservice.api.core.operations.NodeGetOperationI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.result.NodeResultI;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu
 * 
 */
public abstract class DataServiceSupport extends ConfigurableSupport implements DataServiceI {

	private NodeConfigurations configurations;

	protected Map<String, Class<? extends OperationI>> operationInterfaceMap;

	protected Map<Class<? extends OperationI>, Class<? extends OperationI>> operationInterfaceImplementMap;

	public DataServiceSupport() {
		this.configurations = new NodeConfigurations();
		this.operationInterfaceImplementMap = new HashMap<Class<? extends OperationI>, Class<? extends OperationI>>();
		this.operationInterfaceMap = new HashMap<String, Class<? extends OperationI>>();
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T extends OperationI> T prepareOperation(String name) {
		//
		Class<? extends OperationI> cls = this.operationInterfaceMap.get(name);
		return (T) this.prepareOperation(cls);

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T extends OperationI> T prepareOperation(Class<T> opc) {
		//
		Class<? extends T> cls2 = (Class<? extends T>) this.operationInterfaceImplementMap.get(opc);
		if (cls2 == null) {
			throw new FsException("no impl class for:" + opc);
		}
		T rt = this.perpareOperationInternal(opc, cls2);
		rt.prepare();//
		return rt;
	}

	public abstract <T extends OperationI> T perpareOperationInternal(Class<T> itf, Class<? extends T> cls2);

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T extends OperationI> void registerOperation(String name, Class<T> cls, Class<? extends T> cls2) {
		//
		this.operationInterfaceMap.put(name, cls);

		this.operationInterfaceImplementMap.put(cls, cls2);

	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public <W extends NodeWrapper> NodeQueryOperationI<W> prepareNodeQuery(Class<W> cls) {
		NodeQueryOperationI<W> rt = this.prepareOperation(NodeQueryOperationI.class);
		rt.nodeType(cls);
		return rt;
	}

	@Override
	public <W extends NodeWrapper> NodeQueryOperationI<W> prepareNodeQuery(NodeType ntype) {
		//
		NodeQueryOperationI<W> rt = this.prepareOperation(NodeQueryOperationI.class);
		rt.nodeType(ntype);
		return rt;
	}

	@Override
	public <T extends NodeWrapper> T getNewest(Class<T> wpcls, String field, Object value, boolean force) {
		return this.getNewest(wpcls, new String[] { field }, new Object[] { value }, force);
	}

	@Override
	public <T extends NodeWrapper> T getNewest(Class<T> wpcls, String[] fields, Object[] values, boolean force) {
		NodeQueryOperationI<T> qo = this.prepareNodeQuery(wpcls);
		for (int i = 0; i < fields.length; i++) {
			qo.propertyEq(fields[i], values[i]);
		}
		qo.sortTimestamp(true);
		NodeQueryResultI<T> rst = qo.execute().getResult();
		T node = rst.first(false);
		if (node == null) {
			if (force) {
				throw new FsException("no node found for cls:" + wpcls + ",field:" + Arrays.asList(fields)
						+ ",value:" + Arrays.asList(values));
			}
			return null;
		}

		return node;

	}

	/*
	 * Oct 29, 2012
	 */
	@Override
	public <T extends NodeWrapper> T getByUid(Class<T> wpcls, String uid, boolean force) {
		NodeGetOperationI op = this.prepareOperation(NodeGetOperationI.class);
		T tt = ClassUtil.newInstance(wpcls);
		// TODO by class
		op.nodeType(tt.getNodeType());
		op.uniqueId(uid);
		op = op.execute();

		NodeResultI rst = op.getResult().assertNoError();
		T rt = rst.wrapNode(wpcls, force);

		return rt;
	}

	@Override
	public NodeConfigurations getConfigurations() {
		return configurations;
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public <T extends NodeWrapper> boolean deleteByUid(Class<T> wpcls, String uid) {
		//
		NodeDeleteOperationI<T> nr = this.prepareOperation(NodeDeleteOperationI.class);
		nr.nodeType(wpcls);
		nr.uniqueId(uid);
		nr.execute().getResult().assertNoError();
		return true;
	}

	/*
	 * Dec 30, 2012TODO delete by query.
	 */
	@Override
	public <T extends NodeWrapper> int deleteById(Class<T> wpcls, String id) {
		int rt = 0;
		while (true) {
			T t = this.getNewestById(wpcls, id, false);

			if (t == null) {
				return rt;
			}
			rt++;
			String uid = t.getUniqueId();
			this.deleteByUid(wpcls, uid);
		}
	}
}
