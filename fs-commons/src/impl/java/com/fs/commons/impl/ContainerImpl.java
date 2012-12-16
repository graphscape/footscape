/**
 * Jun 19, 2012
 */
package com.fs.commons.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.commons.api.AttachableI;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.FinderI;
import com.fs.commons.api.HasIdI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.describe.DescribableI;
import com.fs.commons.api.describe.Describe;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.AttachableSupport;
import com.fs.commons.api.support.DescribedSupport;

/**
 * @author wu
 * 
 */
public class ContainerImpl extends AttachableSupport implements ContainerI {

	public static class FactoryImpl implements ContainerI.FactoryI {
		@Override
		public ContainerI newContainer() {
			return newContainer(null);
		}

		@Override
		public ContainerI newContainer(ContainerI prt) {
			return new ContainerImpl(prt);
		}

	}

	private static class ObjectEntryImpl extends DescribedSupport implements ObjectEntryI {

		private SPI spi;

		private Object object;

		public ObjectEntryImpl(Object o) {

			this.describe = new Describe();
			this.object = o;
			this.clazz(o.getClass());

			if (DescribableI.class.isInstance(o)) {
				DescribableI de = DescribableI.class.cast(o);
				Describe ad = de.describe();
				this.describe.addAll(ad);
			}
		}

		@Override
		public Object getObject() {
			return this.object;
		}

		public <T> T getObject(Class<T> cls) {
			return cls.cast(this.object);
		}

		@Override
		public String toString() {
			return this.describe.toString() + "," + "object:" + this.object;
		}

		/**
		 * Dec 11, 2012
		 */
		public void tryAttach() {
			if (!(this.object instanceof AttachableI)) {
				return;
			}
			((AttachableI) this.object).attach();
		}

		/**
		 * 
		 */
		public void tryDettach() {
			if (!(this.object instanceof AttachableI)) {
				return;
			}
			((AttachableI) this.object).dettach();
		}

		/*
		 * Dec 15, 2012
		 */
		@Override
		public String getId() {
			//
			if (!(this.object instanceof HasIdI)) {
				return null;
			}

			return ((HasIdI) this.object).getId();
		}

	}

	private ContainerI parent;

	private List<ObjectEntryImpl> entryList = new ArrayList<ObjectEntryImpl>();

	private Map<String, ObjectEntryImpl> hasIdEntryMap = new HashMap<String, ObjectEntryImpl>();

	private ContainerImpl(ContainerI prt) {
		this.parent = prt;
	}

	/* */
	@Override
	public void addObject(SPI spi, String name, Object o) {// TODO more describe

		if (ContainerI.AwareI.class.isAssignableFrom(o.getClass())) {
			ContainerI.AwareI ca = ContainerI.AwareI.class.cast(o);
			ca.setContainer(this);
		}
		ObjectEntryImpl oe = new ObjectEntryImpl(o);
		oe.spi(spi).name(name);// NOTE
		this.entryList.add(oe);
		String id = oe.getId();
		if (id != null) {
			this.hasIdEntryMap.put(id, oe);
		}

		if (this.attached) {
			oe.tryAttach();//
		}
	}

	@Override
	public void removeObject(Object obj) {
		ObjectEntryImpl rt = null;
		for (ObjectEntryImpl oe : this.entryList) {
			if (oe.getObject() == obj) {
				rt = oe;
				break;
			}
		}
		if (rt == null) {
			throw new FsException("no this object :" + obj);
		}
		this.entryList.remove(rt);
		String id = rt.getId();
		if (rt != null) {
			this.hasIdEntryMap.remove(id);
		}

	}

	/* */
	@Override
	public <T> FinderI<T> finder(Class<T> cls) {
		FinderI<T> rt = new ObjectFinderImpl<T>(this, cls);

		return rt;
	}

	public <T> List<T> find(Describe des) {
		List<T> rt = new ArrayList<T>();
		for (ObjectEntryImpl oe : this.entryList) {
			boolean ism = oe.getDescribe().isMatchTo(des);

			if (ism) {//

				T t = (T) (oe.getObject());

				rt.add(t);
			}
		}
		return rt;

	}

	/* */
	@Override
	public <T> T find(Class<T> cls) {

		return this.find(cls, false);

	}

	/* */
	@Override
	public <T> T find(Class<T> cls, boolean force) {

		return this.finder(cls).find(force);

	}

	/*
	
	 */
	@Override
	public void forEach(CallbackI<ObjectEntryI, Boolean> cb) {
		for (ObjectEntryImpl oe : this.entryList) {
			if (cb.execute(oe)) {
				break;
			}

		}
	}

	/*
	
	 */
	@Override
	public ContainerI getParent() {

		return this.parent;
	}

	/*
	
	 */
	@Override
	public ContainerI getTop() {
		ContainerI rt = this;
		ContainerI pr = rt.getParent();
		while (pr != null) {
			rt = pr;
			pr = pr.getParent();
		}
		return rt;
	}

	/*
	 * Dec 11, 2012
	 */
	@Override
	public void doAttach() {
		for (ObjectEntryImpl oe : this.entryList) {
			oe.tryAttach();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.AttachableSupport#doDettach()
	 */
	@Override
	protected void doDettach() {
		for (ObjectEntryImpl oe : this.entryList) {
			oe.tryDettach();
		}

	}

	/*
	 * Dec 14, 2012
	 */
	@Override
	public <T> T find(Class<T> cls, String name) {
		//
		return this.finder(cls).name(name).find(false);

	}

	/*
	 * Dec 14, 2012
	 */
	@Override
	public <T> T find(Class<T> cls, String name, boolean force) {
		//
		return this.finder(cls).name(name).find(force);

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public <T extends HasIdI> T find(String id) {
		//
		ObjectEntryImpl oe = this.hasIdEntryMap.get(id);

		return oe == null ? null : (T) oe.getObject();

	}

	/*
	 * Dec 15, 2012
	 */
	@Override
	public <T extends HasIdI> T find(String id, boolean force) {
		//
		T rt = this.find(id);
		if (force && rt == null) {
			throw new FsException("no object with id:" + id);
		}
		return rt;
	}

}
