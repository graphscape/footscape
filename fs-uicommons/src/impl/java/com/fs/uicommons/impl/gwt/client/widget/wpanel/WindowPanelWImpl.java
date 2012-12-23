/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicommons.impl.gwt.client.widget.wpanel;

import com.fs.uicommons.api.gwt.client.widget.wpanel.WindowPanelWI;
import com.fs.uicommons.impl.gwt.client.widget.panel.AbstractPanelWImpl;
import com.fs.uicore.api.gwt.client.WindowI;
import com.fs.uicore.api.gwt.client.commons.Point;
import com.fs.uicore.api.gwt.client.commons.Size;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.efilter.SimpleEventFilter;
import com.fs.uicore.api.gwt.client.event.SizeChangeEvent;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;

/**
 * @author wu
 *         <p>
 *         This can be the top widget and no parent,but it should be managed by
 *         RootI.
 * @see AbsolutePanel
 */
public class WindowPanelWImpl extends AbstractPanelWImpl implements
		WindowPanelWI {

	/**
	 * @param ele
	 */
	public WindowPanelWImpl(String name) {
		super(name, DOM.createDiv());

		// DOM.setStyleAttribute(this.element, "position", "relative");//

		this.element.getStyle().setPosition(Position.ABSOLUTE);
		this.element.getStyle().setOverflow(Overflow.HIDDEN);
		this.getElementWrapper().moveTo(Point.valueOf(0, 0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.WidgetSupport#doAttach()
	 */
	@Override
	public void doAttach() {
		super.doAttach();
		WindowI w = this.getContainer().get(WindowI.class, true);
		//
		this.addHandler(SimpleEventFilter.valueOf(SizeChangeEvent.TYPE,
				WindowI.class, w), new EventHandlerI<SizeChangeEvent>() {

			@Override
			public void handle(SizeChangeEvent e) {
				WindowPanelWImpl.this.onWindowSizeChange(e);
			}
		});
	}

	protected void onWindowSizeChange(SizeChangeEvent e) {

		Size s = e.getSize();
		DOM.setStyleAttribute(this.element, "width", s.getWidth() + "px");
		DOM.setStyleAttribute(this.element, "height", s.getHeight() + "px");

	}
}
