package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.google.gwt.user.client.DOM;

/**
 * This view actually is sharing the same Element and ModelI with the widget.It
 * is a proxy to the underlying widget.
 * 
 * @author wuzhen
 * 
 * @param <T>
 */
public class ProxyViewSupport<T extends WidgetI> extends ViewSupport {

	protected T widget;

	public ProxyViewSupport(ContainerI ctn, T widget) {
		super(ctn, DOM.createDiv());//
		this.widget = widget;
		// this.child(widget);// NOTE this proxy widget and the child widget
		// point
		// to the same model and element,does this cause
		// problem?
		// if not set the widget's parent as this, which one should be the
		// parent of the widget?
		// the target widget add as the child.Or other ways
		// // to do this?
	}

	@Override
	public void doAttach() {
		super.doAttach();
		this.widget.attach();//
	}

}
