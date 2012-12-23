/**
 * Jun 13, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import java.util.List;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.UiFilterI.Context;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.event.ModelEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public abstract class WidgetBase extends ElementObjectSupport implements
		WidgetI {

	private static int INIT = -2;

	private static int NULL = -1;

	protected int version = INIT;// not

	protected ModelI model;

	protected WidgetFactoryI factory;

	protected Context context;

	protected WidgetBase(String name, Element element) {
		super(name, element);
	}

	@Override
	protected String getClassNamePrefix() {
		return "wgt-";
	}

	@Override
	public WidgetI model(ModelI txt) {
		this.model = txt;
		this.factory = this.model.getWidgetFactory();
		this.model.addHandler(ModelEvent.TYPE, new EventHandlerI<ModelEvent>() {

			@Override
			public void handle(ModelEvent e) {
				WidgetBase.this.onModelEvent(e);
			}

		});
		this.model.setValue(ModelI.L_LAST_WIDGET, this);// raise event for
														// notifying the model
														// related with a
														// widget.
		// call value process for the init values in model
		for (String k : txt.keyList()) {
			// NOTE:the value must be set by setValue(),which will provide a
			Object obj = txt.getProperty(k);
			if (obj instanceof ValueWrapper) {

				Location loc = Location.valueOf(k);
				ValueWrapper vw = txt.getValueWrapper(loc);
				this.processModelValue(loc, vw);//
			} else if (obj instanceof List) {
				throw new UiException("not supported,the value of  property:"
						+ k + " is a list");
			} else {
				throw new UiException("not supported,the property value type:"
						+ obj);
			}
		}

		this.doModel(model);

		return this;
	}

	protected void doModel(ModelI model) {

	}

	protected void onModelEvent(ModelEvent e) {

		if (e instanceof ModelValueEvent) {
			ModelValueEvent mve = (ModelValueEvent) e;
			this.processModelValue(mve.getLocation(), mve.getValueWrapper());
		}

	}

	// to be override
	protected void processModelValue(Location loc, ValueWrapper vw) {
		if (loc.isDefaultProperty()) {
			this.processModelDefaultValue(vw);
		} else if (loc.equals(IS_VISIBLE)) {
			this.processModelVisibleValue(vw);
		}
	}

	protected void processModelVisibleValue(ValueWrapper vw) {
		//
		boolean show = vw.getValue(Boolean.TRUE);

		this.elementWrapper.addAndRemoveClassName(show, "visible", "invisible");
//		// TODO remove following
//		Display display = (Boolean) vw.getValue() ? Display.BLOCK
//				: Display.NONE;
//
//		this.getElementWrapper().getStyle().setDisplay(display);

	}

	// to be override
	protected void processModelDefaultValue(ValueWrapper vw) {

	}

	@Override
	public ModelI getModel() {
		return this.model;
	}

	public ModelI addModel(String name) {
		return this.model.addModel(name);//
	}

}
