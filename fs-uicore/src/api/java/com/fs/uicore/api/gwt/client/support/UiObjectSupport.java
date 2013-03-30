/**
 * Jun 25, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.LazyI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Holder;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.Event.Type;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.fs.uicore.api.gwt.client.logger.UiLoggerFactory;
import com.fs.uicore.api.gwt.client.logger.UiLoggerI;
import com.fs.uicore.api.gwt.client.message.MessageDispatcherI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.scheduler.SchedulerI;
import com.fs.uicore.api.gwt.client.util.OID;

/**
 * @author wuzhen
 * 
 */
public class UiObjectSupport extends MapProperties<Object> implements UiObjectI {

	protected Map<String, LazyI> lazyMap;

	protected UiObjectI parent;

	protected Set<String> marks = new HashSet<String>();

	protected List<UiObjectI> childList;

	protected List<Object> attacherList;// other any object attach to this
										// object

	protected boolean isInAddChild = false;

	protected boolean attached;

	protected MessageDispatcherI eventDispatcher;

	protected String name;

	protected UiLoggerI logger;

	protected String id;

	protected ContainerI container;

	public UiObjectSupport(ContainerI c) {
		this(c, null);
	}

	public UiObjectSupport(ContainerI c, String name) {
		this.init(c, name, null, null, null);
	}

	public UiObjectSupport(ContainerI c, String name, String id) {
		this.init(c, name, id, null, null);
	}

	public UiObjectSupport(ContainerI c, String name, UiPropertiesI<Object> pts) {
		this.init(c, name, null, null, pts);
	}

	public UiObjectSupport(ContainerI c, String name, String id, UiLoggerI log) {
		this.init(c, name, id, log, null);
	}

	protected void init(ContainerI c, String name, String id, UiLoggerI log, UiPropertiesI<Object> pts) {
		this.container = c;
		this.name = name == null ? "unkown" : name;
		this.id = id == null ? OID.next("oid-") : id;
		this.childList = new ArrayList<UiObjectI>();
		this.attacherList = new ArrayList<Object>();
		this.logger = log != null ? log : UiLoggerFactory.getLogger(this.getClass());//

		this.eventDispatcher = new MessageDispatcherImpl("unknow");//
		this.lazyMap = new HashMap<String, LazyI>();
		if (pts != null) {
			this.setProperties(pts);
		}
	}
	
	protected SchedulerI getScheduler(){
		return this.container.get(SchedulerI.class, true);
	}

	/*
	 * Feb 6, 2013
	 */
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}
		if (obj == null || !(obj instanceof UiObjectI)) {
			return false;
		}

		UiObjectI o2 = (UiObjectI) obj;
		return this.id.equals(o2.getId());
	}

	@Override
	public <T> T cast() {
		return (T) this;
	}

	@Override
	public boolean contains(UiObjectI c) {
		return this.contains(c, false);
	}

	@Override
	public boolean contains(UiObjectI c, boolean offspring) {
		UiObjectI p = c.getParent();
		while (p != null) {
			if (p == this) {
				return true;
			}

			if (!offspring) {
				break;
			}
			p = p.getParent();
		}

		return false;
	}

	@Override
	public void setParent(UiObjectI parent) {
		this.parent = parent;
	}

	@Override
	public UiObjectI parent(UiObjectI newParent) {

		if (this.parent != null) {
			this.parent.removeChild(this);
			if (this.parent.isAttached()) {// detach?
				this.detach();//
			}
			if (this instanceof ContainerI.AwareI) {
				ContainerI c = newParent.getContainer();
				((ContainerI.AwareI) this).init(null);// ?
			}
			this.parent = null;

		}
		// new parent
		if (newParent != null) {
			if (newParent.contains(this)) {
				throw new UiException("already parent:" + newParent + ",child:" + this.toDebugString());
			}

			if (this instanceof ContainerI.AwareI) {
				ContainerI c = newParent.getContainer();
				((ContainerI.AwareI) this).init(c);//
			}
			newParent.addChild(this);
			this.setParent(newParent);
			if (newParent.isAttached()) {
				this.attach();//
			}
		} else {
			this.setParent(newParent);
		}

		return this;
	}

	@Override
	public void addChild(UiObjectI c) {
		this.childList.add(c);
	}

	@Override
	public void removeChild(UiObjectI c) {
		this.childList.remove(c);
	}

	@Override
	public <T extends UiObjectI> T getChild(Class<T> cls, String name, boolean force) {
		List<T> rt = this.getChildList(cls, name);

		if (rt.isEmpty()) {
			if (force) {
				throw new UiException("force:" + cls + "/" + name + " in " + this.toDebugString());
			}
			return null;
		} else if (rt.size() > 1) {
			throw new UiException("too many,there are " + rt.size() + " " + cls + "/" + name + " in "
					+ this.toDebugString());

		}
		return rt.get(0);

	}

	@Override
	public <T extends UiObjectI> T getChild(Class<T> cls, boolean force) {
		return this.getChild(cls, null, force);
	}

	@Override
	public <T extends UiObjectI> List<T> getChildList(Class<T> cls) {
		return getChildList(cls, null);
	}

	public <T extends UiObjectI> List<T> getChildList(Class<T> cls, String name) {

		List<T> rt = new ArrayList<T>();
		for (UiObjectI oi : this.childList) {
			if ((cls == null || InstanceOf.isInstance(cls, oi))
					&& (name == null || name.equals(oi.getName()))) {
				rt.add((T) oi);
			}
		}
		return rt;
	}

	public List<UiObjectI> getCopiedChildList() {
		return new ArrayList<UiObjectI>(this.childList);
	}

	@Override
	public void clean() {
		List<UiObjectI> cL = this.copyChildList();
		for (UiObjectI c : cL) {
			c.parent(null);
		}

	}

	@Override
	public <T extends UiObjectI> void clean(Class<T> cls) {
		List<T> cL = this.getChildList(cls);
		for (UiObjectI c : cL) {
			c.parent(null);
		}
	}

	protected List<UiObjectI> copyChildList() {
		List<UiObjectI> rt = new ArrayList<UiObjectI>();
		rt.addAll(this.childList);

		return rt;
	}

	@Override
	public UiClientI getClient(boolean force) {
		
		return this.container.get(UiClientI.class, force);
		
	}

	protected <T extends UiObjectI> T getTopObject(Class<T> cls) {
		return (T) this.getTopObject();
	}

	protected <T extends UiObjectI> T findParent(Class<T> cls, boolean force) {
		UiObjectI pre = this;

		UiObjectI next = pre.getParent();
		while (next != null) {
			if (InstanceOf.isInstance(cls, next)) {//
				return (T) next;
			}
			pre = next;
			next = next.getParent();
		}
		if (force) {
			throw new UiException("force:" + cls);
		}
		return null;

	}

	@Override
	public UiObjectI getTopObject() {
		UiObjectI pre = this;

		UiObjectI next = pre.getParent();
		while (next != null) {
			pre = next;
			next = next.getParent();
		}
		return pre;
	}

	@Override
	public UiObjectI getParent() {
		return parent;
	}

	@Override
	public <E extends Event> void addHandler(EventHandlerI<E> l) {
		this.eventDispatcher.addHandler(Path.ROOT, l);
	}

	/* */
	@Override
	public <E extends Event> void addHandler(Type<E> ec, EventHandlerI<E> l) {
		this.eventDispatcher.addHandler(ec.getAsPath(), l);
	}

	/*
	 * Jan 8, 2013
	 */
	@Override
	public <W extends MsgWrapper> void addHandler(Path path, MessageHandlerI<W> mh) {
		this.eventDispatcher.addHandler(path, mh);
	}

	@Override
	public <E extends Event> void addHandler(final Event.FilterI ef, final EventHandlerI<E> l) {
		this.eventDispatcher.addHandler(Path.ROOT, new EventHandlerI<E>() {

			@Override
			public void handle(E t) {
				if (null == ef.filter(t)) {
					return;
				}
				l.handle(t);//
			}
		});
	}

	@Override
	public <E extends Event> void dispatch(E evt) {
		this.eventDispatcher.dispatch(evt);
		if (evt.isGlobal()) {
			EventBusI eb = this.getEventBus(false);
			if (eb == null || this == eb) {
				return;
			}
			eb.dispatch(evt);
		}
	}

	/* */
	@Override
	public <T extends UiObjectI> T find(Class<T> cls, String name, boolean force) {
		List<T> rt = this.findList(cls, name);

		if (rt.isEmpty()) {
			if (force) {
				throw new UiException("force:" + cls + "/" + name + " in:" + this.toDebugString());
			}
			return null;
		} else if (rt.size() > 1) {
			throw new UiException("too many:" + cls + "/" + name + " in:" + this.toDebugString());

		}
		return rt.get(0);

	}

	/* */
	@Override
	public <T extends UiObjectI> List<T> findList(Class<T> cls, String name) {
		List<T> rt = new ArrayList<T>();
		List<T> l = this.getChildList(cls, name);
		rt.addAll(l);
		for (UiObjectI c : this.childList) {
			List<T> cL = c.findList(cls, name);
			rt.addAll(cL);
		}

		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.core.UiObjectI#find(java.lang.Class,
	 * java.lang.String, boolean)
	 */
	@Override
	public <T extends UiObjectI> T find(Class<T> cls, boolean force) {

		return find(cls, (String) null, force);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.core.UiObjectI#findList(java.lang.Class,
	 * java.lang.String)
	 */
	@Override
	public <T extends UiObjectI> List<T> findList(Class<T> cls) {
		// TODO Auto-generated method stub
		return findList(cls, null);
	}

	/* */
	@Override
	public UiObjectI mark(String mark) {
		this.marks.add(mark);
		return this;

	}

	/* */
	@Override
	public Set<String> getMarks() {

		return this.marks;

	}

	/* */
	@Override
	public boolean hasMark(String mark) {

		return this.marks.contains(mark);

	}

	/* */
	@Override
	public boolean isAttached() {

		return this.attached;

	}

	@Override
	public void attach() {
		if (!this.attached) {

			this.doAttach();
			this.attached = true;// attached.
			new AttachedEvent(this).dispatch();// TODO sync event?

			//
			List<AttacherI> aL = this.getAttacherList(AttacherI.class);
			for (AttacherI a : aL) {
				a.ownerAttached();
			}

		}
		//
		//
		List<UiObjectI> copied = new ArrayList<UiObjectI>(this.childList);
		// NOTE when calling the child attach(),there may create new child for
		// this object and the child list will changed.
		// and the new added child will regard the parent isAttached and the new
		// add child is attached too.
		for (UiObjectI c : copied) {
			c.attach();
		}
	}

	protected void doAttach() {
		// this.eventHandlers.onOwnerAttach();
	}

	protected void doDetach() {
		// this.eventHandlers.onOwnerDettach();
	}

	@Override
	public void detach() {
		if (this.attached) {
			this.doDetach();
			this.attached = false;

			//
			List<AttacherI> aL = this.getAttacherList(AttacherI.class);
			for (AttacherI a : aL) {
				a.ownerAttached();
			}

		}

		for (UiObjectI c : this.childList) {
			c.detach();
		}
	}

	@Override
	public String dump() {
		return this.dump(0, this);
	}

	public String dump(int depth, UiObjectI o) {
		String line = "";
		for (int i = 0; i < depth; i++) {
			line += " ";
		}
		line += ">" + o.getClass().getName() + "/" + o.getName();
		String rt = line + "\n";//
		List<UiObjectI> cL = o.getChildList(UiObjectI.class);
		for (UiObjectI c : cL) {
			rt += this.dump(depth + 1, c);

		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.core.UiObjectI#getEventBus()
	 */
	@Override
	public EventBusI getEventBus(boolean force) {
		ContainerI c = this.getContainer();
		if (c == null) {
			if (force) {
				throw new UiException("force,container not found for object:" + this.toDebugString());
			}
			return null;
		}
		return c.getEventBus();//

	}

	@Override
	public ContainerI getContainer() {
		return this.container;
	}

	@Override
	public void assertAttached() {
		if (this.attached) {
			return;
		}
		throw new UiException("not attached:" + this.toDebugString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.core.UiObjectI#getParentList()
	 */
	@Override
	public List<UiObjectI> getParentList() {
		List<UiObjectI> rt = new ArrayList<UiObjectI>();
		UiObjectI p = this.parent;
		while (p != null) {
			rt.add(p);//
			p = p.getParent();
		}
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.core.UiObjectI#getPath()
	 */
	@Override
	public Path getPath() {
		if (this.parent == null) {
			return Path.valueOf(this.name);
		}
		return Path.valueOf(this.parent.getPath(), this.name);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.core.UiObjectI#child(com.fs.uicore.api.gwt
	 * .client.core.UiObjectI)
	 */
	@Override
	public UiObjectI child(UiObjectI c) {
		c.parent(this);
		return this;
	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public String toString() {
		//
		return "class:" + this.getClass().getName() + ",path:" + this.getPath() + "";
	}

	@Override
	public String toDebugString() {
		return this.dump();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.core.UiObjectI#attacher(java.lang.Object)
	 */
	@Override
	public UiObjectI attacher(Object obj) {
		this.attacherList.add(obj);
		if (obj instanceof AttacherI) {
			((AttacherI) obj).owner(this);//
		}
		return this;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.core.UiObjectI#getAttacher(java.lang.Class,
	 * boolean)
	 */
	@Override
	public <T> T getAttacher(Class<T> cls, boolean force) {
		List<T> l = this.getAttacherList(cls);
		if (l.isEmpty()) {
			if (force) {
				throw new UiException("no attacher:" + cls + " in uio:" + this.toDebugString());
			}
			return null;
		} else if (l.size() == 1) {
			return l.get(0);
		} else {
			throw new UiException("to many attacher:" + cls + " in uio:" + this.toDebugString());

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.core.UiObjectI#getAttacherList(java.lang
	 * .Class)
	 */
	@Override
	public <T> List<T> getAttacherList(Class<T> cls) {
		List<T> rt = new ArrayList<T>();
		for (Object o : this.attacherList) {
			if (InstanceOf.isInstance(cls, o)) {
				rt.add((T) o);
			}
		}
		return rt;
	}

	@Override
	public <T> LazyI<T> getLazy(String name, boolean force) {
		LazyI rt = this.lazyMap.get(name);

		if (rt == null && force) {
			throw new UiException("no lazy:" + name);
		}

		return rt;

	}

	/*
	 * Nov 23, 2012
	 */
	@Override
	public <T> void addLazy(String name, LazyI<T> lz) {
		if (null != this.getLazy(name, false)) {
			throw new UiException("lazy exist:" + name);
		}
		this.lazyMap.put(name, lz);
	}

	/*
	 * Nov 23, 2012
	 */
	@Override
	public <T> T getLazyObject(String name, boolean force) {
		//
		LazyI<T> lz = this.getLazy(name, force);
		if (lz == null) {
			return null;
		}
		return lz.get();
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public String getId() {
		//
		return this.id;
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public <T> T getChildById(String id, boolean force) {
		//
		for (UiObjectI c : this.childList) {
			if (id.equals(c.getId())) {
				return (T) c;
			}
		}
		if (force) {
			throw new UiException("no id:" + id + " in:" + this.toDebugString());
		}
		return null;
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public <T> T findById(String id, boolean force) {
		//
		for (UiObjectI c : this.childList) {

			if (id.equals(c.getId())) {
				return (T) c;
			}
			UiObjectI o = c.findById(id, false);
			if (o != null) {
				return (T) o;
			}
		}

		if (force) {
			throw new UiException("not found id:" + id + " in:" + this.toDebugString());
		}

		return null;
	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	public <T extends UiObjectI> T find(final UiCallbackI<UiObjectI, T> cb) {
		//
		final Holder<T> rtH = new Holder<T>();
		this.forEach(new UiCallbackI<UiObjectI, Boolean>() {

			@Override
			public Boolean execute(UiObjectI t) {
				//
				T rt = cb.execute(t);
				if (rt != null) {
					rtH.setTarget(rt);
					return true;
				}
				return null;
			}
		});
		return rtH.getTarget();
	}

	/*
	 * Jan 13, 2013
	 */
	@Override
	public void forEach(UiCallbackI<UiObjectI, Boolean> cb) {
		Holder<Boolean> bh = new Holder<Boolean>(false);
		UiObjectSupport.forEach(this, cb, bh);

	}

	private static void forEach(UiObjectI obj, UiCallbackI<UiObjectI, Boolean> cb, Holder<Boolean> bh) {
		//
		List<UiObjectI> cl = obj.getChildList(UiObjectI.class);
		for (UiObjectI uo : cl) {

			Boolean stop = cb.execute(uo);
			if (stop != null && stop) {
				bh.setTarget(true);
				return;
			}
		}
		for (UiObjectI uo : cl) {
			UiObjectSupport.forEach(uo, cb, bh);
			Boolean stop = bh.getTarget();
			if (stop != null && stop) {
				return;
			}

		}
	}

}
