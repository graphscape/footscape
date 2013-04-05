/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
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
	
	public <T extends WidgetI> T create(Class<T> cls, String name, Object...pts);

	public <T extends WidgetI> T create(Class<T> cls, String name, UiPropertiesI<Object> pts);

	public <T extends WidgetI> void addCreater(WidgetI.CreaterI<T> wic);

}
