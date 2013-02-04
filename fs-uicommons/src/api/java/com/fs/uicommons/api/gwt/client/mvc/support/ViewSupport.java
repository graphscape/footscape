/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.efilter.ModelValueEventFilter;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class ViewSupport extends LayoutSupport implements ViewI {
	protected ContainerI container;

	public ViewSupport(Element ele, ContainerI ctn) {
		this(null, ele, ctn);
	}

	public ViewSupport(String name, Element ele, ContainerI ctn) {

		this(name, ele, ctn, new SimpleModel("unkown"));
	}

	public ViewSupport(Element ele, ContainerI ctn, ModelI model) {
		this(null, ele, ctn, model);
	}

	public ViewSupport(String name, Element ele, ContainerI ctn, ModelI model) {
		super(name, ele);
		this.container = ctn;
		this.factory = getWidgetFactory(ctn);
		this.model(model);
	}

	@Override
	public ContainerI getContainer() {
		return this.container;
	}

	public static WidgetFactoryI getWidgetFactory(ContainerI ctn) {
		WidgetFactoryI rt = ctn.get(WidgetFactoryI.class, true);// TODO
																// remove,should
		// same as
		// widget.
		return rt;
	}

	@Override
	public WidgetI model(ModelI model) {
		model.setValue(ModelI.L_WIDGET_FACTORY, this.factory);
		super.model(model);
		return this;
	}

	protected <T extends ModelI> T findModel(Class<T> cls, boolean force) {
		return this.getRootModel().find(cls, force);
	}

	protected ModelI getRootModel() {
		return this.getClient(true).getRootModel();
	}

	//
	// @Override
	// public CompositeI child(WidgetI w) {
	// super.child(w);
	//
	// // Element top = this.getElement();
	// // Element c = w.getElement();
	// // top.appendChild(c);
	//
	// return this;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.ViewI#clickAction(java.lang.String)
	 */
	@Override
	public void clickAction(Path a) {
		throw new UiException("TODO");
	}

}
