/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public abstract class WidgetBase extends ElementObjectSupport implements WidgetI {

	protected boolean visible;

	protected WidgetFactoryI factory;

	protected WidgetBase(ContainerI c, String name, Element element) {
		this(c, name, element, null);
	}

	protected WidgetBase(ContainerI c, String name, Element element, UiPropertiesI<Object> pts) {
		super(c, name, element, pts);
		this.factory = c.get(WidgetFactoryI.class, true);
	}

	@Override
	protected String getClassNamePrefix() {
		return "wgt-";
	}

	@Override
	public void setVisible(boolean vis) {
		this.visible = vis;
		//
		this.elementWrapper.addAndRemoveClassName(vis, "visible", "invisible");

	}

}
