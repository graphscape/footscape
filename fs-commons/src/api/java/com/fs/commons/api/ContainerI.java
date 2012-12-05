/**
 * Jun 19, 2012
 */
package com.fs.commons.api;

import java.util.List;

import com.fs.commons.api.callback.CallbackI;
import com.fs.commons.api.describe.Describe;

/**
 * @author wu
 * 
 */
public interface ContainerI {

	public interface AwareI {

		public void setContainer(ContainerI c);

	}

	public interface FactoryI {
		public ContainerI newContainer();

		public ContainerI newContainer(ContainerI parent);
	}

	public interface ObjectEntryI {
		public Object getObject();

		public SPI getSPI();

		public String getName();

		public Describe getDescribe();

	}

	public ContainerI getParent();
	
	public ContainerI getTop();

	public void addObject(SPI spi, String name, Object o);

	public void forEach(CallbackI<ObjectEntryI, Boolean> cb);

	public <T> T find(Class<T> cls);

	public <T> T find(Class<T> cls, boolean force);

	public <T> List<T> find(Describe des);

	public <T> FinderI<T> finder(Class<T> cls);

}
