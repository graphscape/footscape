/**
 * Jul 16, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public abstract class WidgetCreaterSupport<T extends WidgetI> implements
		WidgetI.CreaterI<T> {
	protected Class<T> widgetType;

	public WidgetCreaterSupport(Class<T> wt) {
		this.widgetType = wt;
	}

	/* */
	@Override
	public Class<T> getWidgetType() {

		return this.widgetType;

	}

}
