/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 19, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.image.old;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.drag.DragableI;
import com.fs.uicommons.api.gwt.client.drag.DraggerI;
import com.fs.uicommons.api.gwt.client.drag.event.DragEndEvent;
import com.fs.uicommons.api.gwt.client.drag.event.DragEvent;
import com.fs.uicommons.api.gwt.client.drag.event.DragStartEvent;
import com.fs.uicommons.api.gwt.client.drag.event.DraggingEvent;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Point;
import com.fs.uicore.api.gwt.client.commons.Rectangle;
import com.fs.uicore.api.gwt.client.commons.Size;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtClickHandler;
import com.fs.uicore.api.gwt.client.support.ElementObjectSupport;
import com.fs.uicore.api.gwt.client.support.ObjectElementHelper;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class ImageBox2 extends ElementObjectSupport implements DragableI {
	EventHandlerI<DragEndEvent> eh;

	public static final String DK_TOP = "top";
	public static final String DK_LEFT = "left";
	public static final String DK_BOTTOM = "bottom";
	public static final String DK_RIGHT = "right";

	// the outer most ,box cannot be out of this element's rectangle.
	protected ObjectElementHelper outer;

	// the 4 borders for opacity the backgraound

	protected Map<String, ElementWrapper> decoMap;

	// plus the size
	protected ObjectElementHelper plus;

	// minus the size
	protected ObjectElementHelper minus;

	// display the event point when moving
	protected ElementWrapper labelOfMouseMoving;// enter label;

	/**
	 * @param ele
	 */
	public ImageBox2(ContainerI c, Element outer) {
		super(c, DOM.createDiv());
		this.element.addClassName("image-select-box");
		//
		//
		// TODO the outer's parent is not this ,but the helper attach dependence
		// with this element.
		// ? what is the outer's function here?
		this.outer = this.helpers.addHelper("OUTER", outer);
		this.decoMap = new HashMap<String, ElementWrapper>();
		labelOfMouseMoving = this.addDeco("top");
		this.addDeco("left");
		this.addDeco("bottom");
		this.addDeco("right");
		//

		this.plus = this.helpers.addHelper("plus", DOM.createButton());
		this.plus.getElement().setInnerText("+");
		this.plus.addClassName("plus");
		this.plus.parent(this.elementWrapper);

		this.plus.addGwtHandler(ClickEvent.getType(), new GwtClickHandler() {

			@Override
			protected void handleInternal(ClickEvent evt) {
				ImageBox2.this.onPlus();
			}
		});
		this.minus = this.helpers.addHelper("minus", DOM.createButton());
		this.minus.getElement().setInnerText("-");
		this.minus.addClassName("minus");
		this.minus.parent(this.elementWrapper);
		this.minus.addGwtHandler(ClickEvent.getType(), new GwtClickHandler() {

			@Override
			protected void handleInternal(ClickEvent evt) {
				ImageBox2.this.onMinus();
			}
		});
	}

	@Override
	public void doAttach() {
		super.doAttach();
		UiClientI c = this.getClient(true);//
		DraggerI drag = c.getChild(DraggerI.class, true);
		drag.addDragable(this);
		this.addHandler(DragStartEvent.TYPE, new EventHandlerI<DragStartEvent>() {

			@Override
			public void handle(DragStartEvent e) {
				ImageBox2.this.onStart(e);
			}
		});
		this.addHandler(DragEndEvent.TYPE, new EventHandlerI<DragEndEvent>() {

			@Override
			public void handle(DragEndEvent e) {
				ImageBox2.this.onEnd(e);
			}
		});
		this.addHandler(DraggingEvent.TYPE, new EventHandlerI<DraggingEvent>() {

			@Override
			public void handle(DraggingEvent e) {
				ImageBox2.this.onDragging(e);
			}
		});

	}

	protected void onPlus() {
		Size size = this.elementWrapper.getAbsoluteRectangle().getSize().multiple(1.1);
		this.elementWrapper.resize(size);
		this.updateDecos();
	}

	protected void onMinus() {
		Size size = this.elementWrapper.getAbsoluteRectangle().getSize().multiple(0.9);
		this.elementWrapper.resize(size);
		this.updateDecos();
	}

	protected ElementWrapper getDeco(String name, boolean force) {
		ElementWrapper rt = this.decoMap.get(name);

		if (rt == null && force) {
			throw new UiException("no deco:" + name + " in:" + this);
		}
		return rt;
	}

	protected ElementWrapper addDeco(String name) {

		ElementWrapper rt = new ElementWrapper(DOM.createDiv());
		rt.addClassName("image-select-box-" + name);
		rt.addClassName("image-select-box-decorator");

		this.elementWrapper.append(rt);// absolute
		this.decoMap.put(name, rt);
		return rt;
	}

	// public void setImageDataUrl(String data) {
	// this.image.setAttribute("src", data);
	// }

	protected Rectangle skipThisPositionContext(Rectangle rect) {
		Point topLeft = this.elementWrapper.getAbsoluteRectangle().getTopLeft();

		return new Rectangle(rect.getTopLeft().minus(topLeft), rect.getSize());
	}

	/**
	 * @param e
	 */
	protected void onDragging(DraggingEvent e) {

		this.showEventPoint(e);
		this.updateDecos();

		log(e);
	}

	protected void showEventPoint(DragEvent e) {
		Point p = e.getPoint();
		String text = p.getX() + "," + p.getY();
		this.labelOfMouseMoving.getElement().setInnerText(text);
	}

	protected void updateDecos() {
		// assuming boxRect and outerRect is in the same position context.

		// the follwing 4 decorator must transform to the same position context
		// as the box and outer.
		Rectangle boxRect = skipThisPositionContext(this.elementWrapper.getAbsoluteRectangle());

		Rectangle outerRect = skipThisPositionContext(this.outer.getAbsoluteRectangle());
		// left deco

		{// left margin
			Point tl = outerRect.getTopLeft();
			Point br = Point.valueOf(boxRect.getTopLeft().getX(), outerRect.getBottomLeft().getY());

			Rectangle rect = new Rectangle(tl, br);
			this.getDeco(DK_LEFT, true).moveAndResize(rect);

		}
		{// right margin
			Point tl = Point.valueOf(boxRect.getRightX(), outerRect.getTopY());

			Point br = outerRect.getBottomRight();

			Rectangle rect = new Rectangle(tl, br);
			this.getDeco(DK_RIGHT, true).moveAndResize(rect);

		}
		{// top margin
			Point tl = Point.valueOf(boxRect.getLeftX(), outerRect.getTopY());

			Point br = boxRect.getTopRight();
			Rectangle rect = new Rectangle(tl, br);
			this.getDeco(DK_TOP, true).moveAndResize(rect);

		}
		{// bottom margin
			Point tl = boxRect.getBottomLeft();
			Point br = Point.valueOf(boxRect.getRightX(), outerRect.getBottomY());
			Rectangle rect = new Rectangle(tl, br);
			this.getDeco(DK_BOTTOM, true).moveAndResize(rect);

		}

	}

	/**
	 * @param e
	 */
	protected void onEnd(DragEndEvent e) {
		this.labelOfMouseMoving.getElement().setInnerText(null);
		this.updateDecos();
		log(e);
	}

	/**
	 * @param e
	 */
	protected void onStart(DragStartEvent e) {

		this.showEventPoint(e);
		log(e);
	}

	protected void log(DragEvent e) {
		log(e.toString());
	}

	protected void log(String msg) {
		// this.element.setInnerText(msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.drag.DragableI#getOuterMostElement()
	 */
	@Override
	public Element getOuterMostElement() {
		return this.outer.getElement();
	}

	/*
	 * Nov 24, 2012
	 */
	@Override
	public Element getDragingOnElement() {
		//
		return this.element;
	}

	/*
	 *Apr 21, 2013
	 */
	@Override
	public Element getMovingElement() {
		// 
		return this.element;
	}

	/*
	 *Apr 21, 2013
	 */
	@Override
	public Element getLogingElement() {
		// 
		return null;
	}

}
