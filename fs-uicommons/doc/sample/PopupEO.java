/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 24, 2012
 */
package com.fs.uicommons.impl.gwt.client.manage;

import com.fs.uicommons.api.gwt.client.drag.DragableI;
import com.fs.uicommons.api.gwt.client.drag.DraggerI;
import com.fs.uicommons.impl.gwt.client.dom.TDWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TRWrapper;
import com.fs.uicommons.impl.gwt.client.dom.TableWrapper;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Rectangle;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtClickHandler;
import com.fs.uicore.api.gwt.client.support.ElementObjectSupport;
import com.fs.uicore.api.gwt.client.support.ObjectElementHelper;
import com.fs.uicore.api.gwt.client.window.UiWindow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class PopupEO extends ElementObjectSupport implements DragableI {

	protected ObjectElementHelper close;

	protected Element dragged;

	/**
	 * @param ele
	 */

	public PopupEO(Element content) {
		super(DOM.createDiv());
		this.elementWrapper.addClassName("popup");
		TableWrapper header = new TableWrapper();
		header.addClassName("outer");//

		DOM.appendChild(this.element, header.getElement());

		this.dragged = DOM.createDiv();
		this.dragged.addClassName("dragged");

		TRWrapper tr = header.addTr();
		TDWrapper td = tr.addTd();
		td.addClassName("left");
		td.append(dragged);

		//
		this.close = this.helpers.addHelper("close", DOM.createDiv());
		this.close.getElement().setInnerText("close");
		this.close.getElement().addClassName("button");
		this.close.addGwtHandler(ClickEvent.getType(), new GwtClickHandler() {

			@Override
			protected void handleInternal(ClickEvent evt) {
				PopupEO.this.onClose();
			}
		});
		td = tr.addTd();
		td.addClassName("right");
		td.append(this.close.getElement());//
		// bod

		this.elementWrapper.append(content);
		// TODO footer.

	}

	protected void onClose() {
		this.setVisible(false);
	}

	@Override
	public void setVisible(boolean vis) {
		super.setVisible(vis);
		if (!vis) {
			return;
		}
		Rectangle rect = UiWindow.getRectangle();
		this.getElementWrapper().moveToCenterOf(rect);// TODO center

	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public Element getOuterMostElement() {
		//
		return null;
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public void doAttach() {
		super.doAttach();
		UiClientI c = this.getClient(true);//
		DraggerI drag = c.getChild(DraggerI.class, true);
		drag.addDragable(this);

	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public Element getDragedElement() {
		//
		return this.dragged;
	}

}
