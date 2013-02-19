/**
 * Jun 13, 2012
 */
package com.fs.uicore.impl.gwt.client;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.core.WidgetI.CreaterI;

/**
 * @author wuzhen
 * 
 */
public class WidgetFactoryImpl implements WidgetFactoryI {
	/*
	
	 */
	private Map<Class, WidgetI.CreaterI> createrMap;

	private ContainerI container;

	public WidgetFactoryImpl(ContainerI c) {
		this.container = c;
		this.createrMap = new HashMap<Class, WidgetI.CreaterI>();
	}

	@Override
	public <T extends WidgetI> T create(Class<T> cls) {
		return this.create(cls, null);
	}

	@Override
	public <T extends WidgetI> T create(Class<T> cls, UiPropertiesI<Object> pts) {
		return this.create(cls, null, pts);
	}

	@Override
	public <T extends WidgetI> T create(Class<T> cls, String name, UiPropertiesI<Object> pts) {
		WidgetI.CreaterI<T> wic = this.createrMap.get(cls);
		if (wic == null) {
			throw new UiException("no creater found for widget type:" + cls);
		}

		T rt = wic.create(this.container, name, pts);
		return rt;
	}

	/* */
	@Override
	public void addCreater(CreaterI wic) {
		this.createrMap.put(wic.getWidgetType(), wic);
	}

}
