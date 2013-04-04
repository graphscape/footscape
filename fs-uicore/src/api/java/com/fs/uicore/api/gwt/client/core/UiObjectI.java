/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client.core;

import java.util.List;
import java.util.Set;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.LazyI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.message.MessageHandlerI;

/**
 * @author wuzhen
 * 
 */
public interface UiObjectI extends UiPropertiesI<Object> {

	public interface AttacherI {

		public void owner(UiObjectI obj);

		public UiObjectI getOwner(boolean force);

		public void ownerAttached();

		public void ownerDettached();

	}

	public UiObjectI mark(String mark);

	public Set<String> getMarks();

	public boolean hasMark(String mark);

	public UiObjectI getParent();

	public UiClientI getClient(boolean force);

	@Deprecated
	public void setParent(UiObjectI p);

	@Deprecated
	public void addChild(UiObjectI c);

	public UiObjectI parent(UiObjectI p);

	public <T> T cast();

	public void clean();

	public <T extends UiObjectI> void clean(Class<T> cls);

	public UiObjectI child(UiObjectI c);

	public boolean contains(UiObjectI c);

	public boolean contains(UiObjectI c, boolean offspring);

	public void removeChild(UiObjectI c);

	// use EventBusI.addHandler
	public <E extends Event> void addHandler(Event.Type<E> ec, EventHandlerI<E> l);

	@Deprecated
	public <E extends Event> void addHandler(EventHandlerI<E> l);

	public <W extends MsgWrapper> void addHandler(Path path, MessageHandlerI<W> mh);

	@Deprecated
	public <E extends Event> void addHandler(final Event.FilterI ef, final EventHandlerI<E> l);

	public <E extends Event> void dispatch(E evt);

	public UiObjectI getTopObject();

	public void setName(String name);

	public String getName();

	public String getId();

	public <T extends UiObjectI> T getChild(Class<T> cls, String name, boolean force);

	public <T extends UiObjectI> T getChild(Class<T> cls, boolean force);

	public <T extends UiObjectI> T find(Class<T> cls, boolean force);

	public <T extends UiObjectI> T find(Class<T> cls, String name, boolean force);

	public <T extends UiObjectI> List<T> findList(Class<T> cls);

	public <T extends UiObjectI> List<T> findList(Class<T> cls, String name);

	public <T extends UiObjectI> T find(UiCallbackI<UiObjectI, T> cb);

	public void forEach(UiCallbackI<UiObjectI, Boolean> cb);

	public <T extends UiObjectI> List<T> getChildList(Class<T> cls);

	public String dump();

	public EventBusI getEventBus(boolean force);

	public ContainerI getContainer();

	public List<UiObjectI> getParentList();

	public boolean isAttached();

	public void attach();

	public void assertAttached();

	public void detach();

	public Path getPath();

	public UiObjectI attacher(Object obj);

	public <T> T getAttacher(Class<T> cls, boolean force);

	public <T> List<T> getAttacherList(Class<T> cls);

	public <T> void addLazy(String name, LazyI<T> lazy);

	public <T> LazyI<T> getLazy(String name, boolean force);

	public <T> T getLazyObject(String name, boolean force);

	public <T> T getChildById(String id, boolean force);

	public <T> T findById(String id, boolean force);

	public String toDebugString();
}
