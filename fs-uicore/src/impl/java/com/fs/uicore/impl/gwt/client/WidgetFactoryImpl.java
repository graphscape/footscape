/**
 * Jun 13, 2012
 */
package com.fs.uicore.impl.gwt.client;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.core.WidgetI.CreaterI;
import com.fs.uicore.api.gwt.client.support.SimpleModel;

/**
 * @author wuzhen
 * 
 */
public class WidgetFactoryImpl implements WidgetFactoryI {
	/*
	
	 */
	private Map<Class, WidgetI.CreaterI> createrMap;

	public WidgetFactoryImpl() {
		this.createrMap = new HashMap<Class, WidgetI.CreaterI>();
	}

	@Override
	public <T extends WidgetI> T create(Class<T> cls, ModelI mp) {
		return this.create(cls, null, mp);
	}

	@Override
	public <T extends WidgetI> T create(Class<T> cls, String name, ModelI mp) {
		WidgetI.CreaterI<T> wic = this.createrMap.get(cls);
		if (wic == null) {
			throw new UiException("no creater found for widget type:" + cls);
		}

		T rt = wic.create(name);
		return this.initilize(rt, mp);
	}

	@Override
	public <T extends WidgetI> T initilize(T rt, ModelI mp) {
		// TODO assert not initiliazed?
		if (mp == null) {
			throw new UiException("model of widget is null:" + rt);
		}
		mp.setValue(ModelI.L_WIDGET_FACTORY, this);//
		rt.model(mp);

		return rt;
	}

	@Override
	public <T extends WidgetI> T create(Class<T> cls) {
		return create(cls, (String) null);
	}

	/*
	
	 */
	@Override
	public <T extends WidgetI> T create(Class<T> cls, String name) {
		return create(cls, name, SimpleModel.valueOf(name, null));

	}

	/* */
	@Override
	public void addCreater(CreaterI wic) {
		this.createrMap.put(wic.getWidgetType(), wic);
	}

}
