/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client.core;

import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wuzhen
 * 
 */
public class Event extends MsgWrapper {

	public static class Type<E extends Event> extends UiType<E> {

		private String name;

		public Type(String name) {
			this(null, name);
		}

		public Type(Type<? extends Event> p, String name) {
			super(p);
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public Path getAsPath() {
			Type<?> pt = (Type<?>) this.getParent();
			if (pt == null) {
				return Path.valueOf(this.name);
			}
			return pt.getAsPath().getSubPath(this.name);
		}
	}

	@Deprecated
	public static interface FilterI {

		public <T extends Event> T filter(Event e);

	}

	public static interface SyncHandlerI<E extends Event> extends EventHandlerI<E>, SynchronizedI {

	}

	public static interface AsyncHandlerI<E extends Event> extends EventHandlerI<E> {

	}

	public static interface EventHandlerI<E extends Event> extends MessageHandlerI<E> {

	}

	protected UiObjectI source;

	protected boolean isGlobal = true;

	public Event(Type<? extends Event> type) {
		this(type, null);
	}

	public Event(Type<? extends Event> type, UiObjectI src) {
		this(type, src, type.getAsPath());
	}

	public Event(Type<? extends Event> type, UiObjectI src, Path path) {
		this(type, src, new MessageData(path));
	}

	protected Event(Type<? extends Event> type, UiObjectI src, MessageData msg) {
		this(type.getAsPath(), src, msg);
	}

	protected Event(Path path, UiObjectI src, MessageData msg) {
		super(msg);
		Path mpath = msg.getPath();
		if (!path.isSubPath(mpath, true)) {
			throw new UiException("event type path:" + path + " is not the super type of message path:"
					+ mpath);
		}
		this.source = src;

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

	public <E extends Event> E source(UiObjectI s) {
		this.source = s;

		return (E) this;
	}

	public <T extends UiObjectI> T getSource(Class<T> cls) {
		return (T) this.source;
	}

	public <T extends Event> T property(String name, Object value) {
		this.setProperty(name, value);
		return (T) this;
	}

	public <E extends Event> E dispatch() {
		this.source.dispatch(this);
		return (E) this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Event,class:" + this.getClass() + "," + super.toString() + ",source:" + this.source;
	}

}
