/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client.core;

import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;

/**
 * @author wuzhen
 * 
 */
public class Event {

	public static class Type<E extends Event> extends UiType<E> {

		public Type() {
			this(null);
		}

		public Type(Type<? extends Event> p) {
			super(p);
		}
	}

	public static interface FilterI {

		public <T extends Event> T filter(Event e);

	}

	public static interface SyncHandlerI<E extends Event> extends EventHandlerI<E>, SynchronizedI {

	}

	public static interface AsyncHandlerI<E extends Event> extends EventHandlerI<E> {

	}

	public static interface EventHandlerI<E extends Event> extends HandlerI<E> {

	}

	protected UiObjectI source;

	protected Type<? extends Event> type;

	protected PropertiesData<Object> properties;

	protected boolean isGlobal = true;

	public Event(Type<? extends Event> type) {
		this(type, null);
	}

	public Event(Type<? extends Event> type, UiObjectI src) {
		this(type, src, new PropertiesData<Object>());
	}

	public Event(Type<? extends Event> type, UiObjectI src, PropertiesData<Object> pts) {
		this.source = src;
		this.type = type;
		this.properties = pts;
	}

	public boolean isGlobal() {
		return this.isGlobal;
	}

	/**
	 * @return the source
	 */
	public UiObjectI getSource() {
		return source;
	}

	/**
	 * @return the type
	 */
	public <E extends Event> Type<E> getType() {
		return (Type<E>) type;
	}

	public <E extends Event> E source(UiObjectI s) {
		this.source = s;

		return (E) this;
	}

	public <T extends UiObjectI> T getSource(Class<T> cls) {
		return (T) this.source;
	}

	public <E extends Event> E dispatch() {
		this.source.dispatch(this);
		return (E) this;
	}

	public boolean isMatch(Type type, Class<? extends UiObjectI> srcCls) {
		return this.type.equals(type) && (this.source != null && InstanceOf.isInstance(srcCls, this.source));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Event,class:" + this.getClass().getName() + ",src:" + this.source;
	}

}
