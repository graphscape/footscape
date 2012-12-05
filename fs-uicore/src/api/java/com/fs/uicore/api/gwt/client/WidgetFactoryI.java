/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface WidgetFactoryI {

	public static interface AwareI {
		public void setWidgetFactory(WidgetFactoryI wf);
	}

	public <T extends WidgetI> T create(Class<T> cls);
	
	public <T extends WidgetI> T create(Class<T> cls, String name);
	
	public <T extends WidgetI> T create(Class<T> cls, ModelI dp);

	public <T extends WidgetI> T create(Class<T> cls, String name, ModelI dp);

	// like the class parameter,to be init with the same logic with the other
	// create method.
	public <T extends WidgetI> T initilize(T w, ModelI md);

	public <T extends WidgetI> void addCreater(WidgetI.CreaterI<T> wic);

}
