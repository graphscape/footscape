/**
 * Jun 19, 2012
 */
package com.fs.commons.api;

import java.util.List;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.describe.Describe;
import com.fs.commons.api.event.EventBusI;

/**
 * @author wu
 * 
 */
public interface ContainerI extends AttachableI {

	public interface AwareI {

		public void setContainer(ContainerI c);

	}

	public interface ObjectEntryI {
		public Object getObject();

		public SPI getSPI();

		public String getName();

		public Describe getDescribe();

		public String getId();

	}

	public ContainerI parent(ContainerI prt);
	
	public ContainerI getParent();

	public ContainerI getTop();

	public EventBusI getEventBus();

	public void addObject(SPI spi, Object o);
	
	public void addObject(SPI spi, String name, Object o);

	public void forEach(CallbackI<ObjectEntryI, Boolean> cb);

	public <T> T find(Class<T> cls);

	public <T> T find(Class<T> cls, boolean force);

	public <T> T find(Class<T> cls, String name);

	public <T extends HasIdI> T find(String id);

	public <T extends HasIdI> T find(String id, boolean force);

	public <T> T find(Class<T> cls, String name, boolean force);

	public <T> List<T> find(Describe des);

	public <T> FinderI<T> finder(Class<T> cls);

}
