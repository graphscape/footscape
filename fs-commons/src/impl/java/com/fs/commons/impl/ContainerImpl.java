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
import com.fs.commons.api.event.BeforeAttachEvent;
import com.fs.commons.api.event.EventBusI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.support.AttachableSupport;
import com.fs.commons.api.support.DescribedSupport;
import com.fs.commons.impl.event.EventBusImpl;

/**
 * @author wu
 * 
 */
public class ContainerImpl extends AttachableSupport implements ContainerI {

	private static class ObjectEntryImpl extends DescribedSupport implements ObjectEntryI {

		private Object object;

		public ObjectEntryImpl(SPI spi, String name, Object o, ContainerI c) {
			this.spi(spi);
			this.name(name);
			this.container = c;
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
			new BeforeAttachEvent(this.object).dispatch(this.container);
			((AttachableI) this.object).attach();

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

	private EventBusI eventBus;

	private ContainerI parent;

	private List<ObjectEntryImpl> entryList = new ArrayList<ObjectEntryImpl>();

	private Map<String, ObjectEntryImpl> hasIdEntryMap = new HashMap<String, ObjectEntryImpl>();

	public ContainerImpl() {
		this.eventBus = new EventBusImpl();
	}

	@Override
	public void addObject(SPI spi, Object o) {
		this.addObject(spi, "unknown", o);
	}

	/* */
	@Override
	public void addObject(SPI spi, String name, Object o) {//

		ObjectEntryImpl oe = new ObjectEntryImpl(spi, name, o, this);

		this.entryList.add(oe);
		String id = oe.getId();
		if (id != null) {
			this.hasIdEntryMap.put(id, oe);
		}

		if (this.attached) {
			oe.tryAttach();//
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

		for (ObjectEntryImpl oe : new ArrayList<ObjectEntryImpl>(this.entryList)) {
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

	/*
	 * Dec 17, 2012
	 */
	@Override
	public EventBusI getEventBus() {
		//
		return this.eventBus;
	}

	@Override
	public ContainerI parent(ContainerI prt) {
		if (this.parent != null) {
			throw new FsException("parent duplicated");
		}
		this.parent = prt;
		return this;

	}

}
