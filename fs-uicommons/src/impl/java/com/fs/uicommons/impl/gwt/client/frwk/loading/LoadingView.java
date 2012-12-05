package com.fs.uicommons.impl.gwt.client.frwk.loading;

import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.commons.Point;
import com.fs.uicore.api.gwt.client.commons.Size;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;

/**
 * 
 * @author wuzhen
 * 
 */
public class LoadingView extends ViewSupport {

	public LoadingView(ContainerI c) {
		super(DOM.createDiv(), c);

		this.element.getStyle().setPosition(Position.ABSOLUTE);

		this.element.getStyle().setOverflow(Overflow.VISIBLE);//

		this.getElementWrapper().moveTo(Point.valueOf(0, 0));
	}

	public ElementWrapper getReferenceElement() {
		/*
		 * WindowCI wc = this.getControl().getTopObject() .find(WindowCI.class,
		 * false); ElementWrapper rt = null; if (wc == null) {// WidgetI wp =
		 * (WidgetI) this.getParent(); rt = wp.getElementWrapper(); } else { rt
		 * = wc.getView().getElementWrapper(); } return rt;
		 */
		return null;
	}

	public void center() {

		ElementWrapper ew = this.getReferenceElement();//

		Point pc = ew.getAbsoluteRectangle().getCenter();

		Size size = this.elementWrapper.getAbsoluteRectangle().getSize();

		pc = pc.add(-size.getWidth() / 2, -size.getHeight() / 2);

		this.getElementWrapper().moveTo(pc);
	}

	@Override
	protected void processModelDefaultValue(ValueWrapper md) {

		// LoadingCI lc = this.getControl(LoadingCI.class);
		// a model value.
		// int rc = lc.getRequestCounter();
		// this.element.setInnerText("Loading...");

		// this.center();

		// this.setVisible(rc > 0);

	}

}
