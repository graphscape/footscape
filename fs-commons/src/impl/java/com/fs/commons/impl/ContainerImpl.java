/**
 * Jun 19, 2012
 */
package com.fs.commons.impl;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.ContainerI;
import com.fs.commons.api.FinderI;
import com.fs.commons.api.SPI;
import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.describe.DescribableI;
import com.fs.commons.api.describe.Describe;
import com.fs.commons.api.support.DescribedSupport;

/**
 * @author wu
 * 
 */
public class ContainerImpl implements ContainerI {

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

	private static class ObjectEntryImpl extends DescribedSupport implements
			ObjectEntryI {

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

	}

	private ContainerI parent;

	private List<ObjectEntryImpl> entryList = new ArrayList<ObjectEntryImpl>();

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

}
