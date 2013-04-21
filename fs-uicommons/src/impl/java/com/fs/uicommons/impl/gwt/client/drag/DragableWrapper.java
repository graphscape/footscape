/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 19, 2012
 */
package com.fs.uicommons.impl.gwt.client.drag;

import com.fs.uicommons.api.gwt.client.drag.DragableI;
import com.fs.uicommons.api.gwt.client.drag.DraggerI;
import com.fs.uicommons.api.gwt.client.drag.event.DragEndEvent;
import com.fs.uicommons.api.gwt.client.drag.event.DragStartEvent;
import com.fs.uicommons.api.gwt.client.drag.event.DraggingEvent;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Point;
import com.fs.uicore.api.gwt.client.commons.Rectangle;
import com.fs.uicore.api.gwt.client.commons.Size;
import com.fs.uicore.api.gwt.client.dom.ElementWrapper;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtMouseDownHandler;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtMouseMoveHandler;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtMouseUpHandler;
import com.fs.uicore.api.gwt.client.support.ObjectElementHelper;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class DragableWrapper extends UiObjectSupport {

	public static Rectangle LARGE_ENOUGH = new Rectangle(Point.valueOf(-10000, -10000), Point.valueOf(10000,
			10000));

	protected DragableI dragable;

	protected ObjectElementHelper dragingOnHelper;

	protected ElementWrapper movingElement;

	protected boolean dragging;

	protected Point startMousePoint;

	protected Point startTopLeft;

	protected boolean pickupByDoubleClick = true;// double click

	protected Element logging;

	/**
	 * @param db
	 */
	public DragableWrapper(ContainerI c, DragableI db) {
		super(c);
		this.dragable = db;
		this.logging = db.getLogingElement();

		this.dragingOnHelper = new ObjectElementHelper(this.dragable.getDragingOnElement(), this);// this
		this.movingElement = new ElementWrapper(this.dragable.getMovingElement());//
		// or
		// that?
		this.movingElement.addClassName("moving");

		this.dragingOnHelper.addGwtHandler(MouseDownEvent.getType(), new GwtMouseDownHandler() {

			@Override
			protected void handleInternal(MouseDownEvent evt) {
				DragableWrapper.this.onMouseDown(evt);
			}
		});
		this.dragingOnHelper.addGwtHandler(MouseUpEvent.getType(), new GwtMouseUpHandler() {

			@Override
			protected void handleInternal(MouseUpEvent evt) {
				DragableWrapper.this.onMouseUp(evt);
			}
		});
		this.dragingOnHelper.addGwtHandler(MouseMoveEvent.getType(), new GwtMouseMoveHandler() {

			@Override
			protected void handleInternal(MouseMoveEvent evt) {
				DragableWrapper.this.onMouseMove(evt);
			}
		});

	}

	protected void log(String txt) {
		this.log(txt, true);
	}

	protected void log(String txt, boolean clear) {
		if (this.logging == null) {
			return;
		}
		String rt = txt;
		if (!clear) {
			String it = this.logging.getInnerText();
			if (it != null) {
				rt = it + rt;
			}
		}

		this.logging.setInnerText(rt);
	}

	protected void onMouseMove(MouseMoveEvent event) {
		if (!dragging) {
			return;
		}

		// we don't want the widget to go off-screen, so the top/left
		// values should always remain be positive.
		Point mousePoint = this.getClientPoint(event);
		Point offSet = mousePoint.minus(this.startMousePoint);//
		//
		Point elePoint2 = this.startTopLeft.plus(offSet);
		String txt = "smp:" + this.startMousePoint + ",stl:" + this.startTopLeft + ",msp:" + mousePoint
				+ ",ofs:" + offSet + ",rst:" + elePoint2;
		txt = offSet + "";
		this.log(txt);
		this.tryMoveTo(elePoint2);
		new DraggingEvent(this.dragable, this.getDragger(), mousePoint).dispatch();//
	}

	protected void tryMoveTo(Point pt) {// target mouse point
		Rectangle outRect = this.LARGE_ENOUGH;
		// Rectangle rec2 = this.movingElement.getAbsoluteRectangle();
		Rectangle rec2 = this.movingElement.getOffsetRectangle();
		Size size1 = outRect.getSize();
		Size size2 = rec2.getSize();

		int w = Math.max(0, size1.getWidth() - size2.getWidth());
		int h = Math.max(0, size1.getHeight() - size2.getHeight());
		// rec3 is the scope of the topLeft point .
		Rectangle rec3 = new Rectangle(outRect.getTopLeft(), Size.valueOf(w, h));

		Point p2 = rec3.getShortestPointTo(pt);//
		// Note: x,y is offset not absolute.
		this.movingElement.moveTo(p2);

		Point newP = this.movingElement.getOffsetRectangle().getTopLeft();//

		// this.log(",move-to:" + p2 + ",top-left:" + newP, false);
		this.log("," + newP, false);
	}

	public DraggerI getDragger() {
		return (DraggerI) this.parent;
	}

	protected void onMouseUp(MouseUpEvent event) {

		if (this.pickupByDoubleClick) {
			// if double click ,should listener down event
			return;
		}
		Point p = this.getClientPoint(event);
		this.endDrag(p);

	}

	protected void endDrag(Point p) {
		dragging = false;

		DOM.releaseCapture(this.dragingOnHelper.getElement());
		this.movingElement.removeClassName("moving");

		new DragEndEvent(this.dragable, this.getDragger(), p).dispatch();//

	}

	protected void onMouseDown(MouseDownEvent event) {
		Point clientPoint = this.getClientPoint(event);
		if (this.pickupByDoubleClick) {
			startOrEndDrag(clientPoint);// switch
		} else {//

			this.startDrag(clientPoint);

		}

	}

	protected void startOrEndDrag(Point clientPoint) {
		if (this.dragging) {
			this.endDrag(clientPoint);
		} else {
			this.startDrag(clientPoint);
		}
	}

	//
	protected void startDrag(Point p) {
		dragging = true;
		this.startMousePoint = p;
		this.startTopLeft = this.movingElement.getOffsetRectangle().getTopLeft();//

		this.movingElement.addClassName("moving");
		// capturing the mouse to the dragged widget.
		DOM.setCapture(this.dragingOnHelper.getElement());

		new DragStartEvent(this.dragable, this.getDragger(), this.startMousePoint).dispatch();//
	}

	protected Point getClientPoint(MouseEvent event) {
		return Point.valueOf(event.getClientX(), event.getClientY());
	}

	protected Point getRelativePoint(MouseEvent event, Element ele) {
		int x = event.getRelativeX(ele);
		int y = event.getRelativeY(ele);
		return Point.valueOf(x, y);

	}

	/*
	 * Nov 19, 2012
	 */
	@Override
	protected void doAttach() {
		//
		super.doAttach();
		this.dragingOnHelper.attach();// TODO add listener in helper.
	}

}
