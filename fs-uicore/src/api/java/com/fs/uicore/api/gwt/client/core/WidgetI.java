/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client.core;

import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public interface WidgetI extends ElementObjectI {

	public static interface CreaterI<T extends WidgetI> {
		public Class<T> getWidgetType();

		public T create(ContainerI c, String name);
	}

	public void setVisible(boolean v);

	public boolean isVisible();
	
	@Deprecated
	// use ElementWrapper.click()
	public void _click();

}
