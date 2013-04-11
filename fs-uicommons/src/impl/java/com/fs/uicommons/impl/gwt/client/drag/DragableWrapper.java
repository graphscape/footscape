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

	public static Rectangle LARGE_ENOUGH = new Rectangle(Point.valueOf(-10000, 0),
			Point.valueOf(10000, 10000));

	protected DragableI dragable;

	protected ObjectElementHelper draggedHelper;

	protected ElementWrapper dragableElement;

	protected ElementWrapper outerElement;

	protected boolean dragging;

	protected Point startMousePoint;

	protected Point startTopLeft;

	protected boolean pickupByDoubleClick = true;// double click

	/**
	 * @param db
	 */
	public DragableWrapper(ContainerI c, DragableI db) {
		super(c);
		this.dragable = db;
		this.outerElement = this.dragable.getOuterMostElement() == null ? null : new ElementWrapper(
				this.dragable.getOuterMostElement());//
		this.draggedHelper = new ObjectElementHelper(this.dragable.getDragedElement(), this);// this
		this.dragableElement = this.dragable.getElementWrapper();//
		// or
		// that?
		this.dragableElement.addClassName("draggable");

		this.draggedHelper.addGwtHandler(MouseDownEvent.getType(), new GwtMouseDownHandler() {

			@Override
			protected void handleInternal(MouseDownEvent evt) {
				DragableWrapper.this.onMouseDown(evt);
			}
		});
		this.draggedHelper.addGwtHandler(MouseUpEvent.getType(), new GwtMouseUpHandler() {

			@Override
			protected void handleInternal(MouseUpEvent evt) {
				DragableWrapper.this.onMouseUp(evt);
			}
		});
		this.draggedHelper.addGwtHandler(MouseMoveEvent.getType(), new GwtMouseMoveHandler() {

			@Override
			protected void handleInternal(MouseMoveEvent evt) {
				DragableWrapper.this.onMouseMove(evt);
			}
		});

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
		this.tryMoveTo(elePoint2);
		new DraggingEvent(this.dragable, this.getDragger(), mousePoint).dispatch();//
	}

	protected Rectangle getOutermost() {
		Rectangle rt = LARGE_ENOUGH;
		if (this.outerElement != null) {
			this.outerElement.getAbsoluteRectangle();
		}
		return rt;
	}

	protected void tryMoveTo(Point pt) {// target mouse point
		Rectangle outRect = this.getOutermost();
		Rectangle rec2 = this.dragableElement.getAbsoluteRectangle();
		Size size1 = outRect.getSize();
		Size size2 = rec2.getSize();

		int w = Math.max(0, size1.getWidth() - size2.getWidth());
		int h = Math.max(0, size1.getHeight() - size2.getHeight());
		// rec3 is the scope of the topLeft point .
		Rectangle rec3 = new Rectangle(outRect.getTopLeft(), Size.valueOf(w, h));

		Point p2 = rec3.getShortestPointTo(pt);//
		this.dragableElement.moveTo(p2);

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

		DOM.releaseCapture(this.draggedHelper.getElement());
		this.dragableElement.removeClassName("dragging");

		new DragEndEvent(this.dragable, this.getDragger(), p).dispatch();//

	}

	protected void onMouseDown(MouseDownEvent event) {
		Point p = this.getClientPoint(event);
		Point rp = this.getRelativePoint(event, this.dragableElement.getElement());
		if (this.pickupByDoubleClick) {
			startOrEndDrag(p,rp);//switch
		} else {//
			this.tryStartDrag(p,rp);

		}

	}

	protected void startOrEndDrag(Point cp,Point rp) {
		if (this.dragging) {
			this.endDrag(cp);
		} else {
			this.tryStartDrag(cp,rp);
		}
	}
	protected void tryStartDrag(Point cp,Point rp) {
		Size size = this.dragableElement.getAbsoluteRectangle().getSize();
		
		if(rp.isFirstArea(true)&&rp.getX()<size.getWidth()&&rp.getY()<size.getHeight()){
			this.startDrag(cp);
		}
		
	}
	protected void startDrag(Point p) {
		dragging = true;
		this.startMousePoint = p;
		this.startTopLeft = this.dragableElement.getAbsoluteRectangle().getTopLeft();//

		this.dragableElement.addClassName("dragging");
		// capturing the mouse to the dragged widget.
		DOM.setCapture(this.draggedHelper.getElement());

		new DragStartEvent(this.dragable, this.getDragger(), this.startMousePoint).dispatch();//
	}
	
	protected Point getClientPoint(MouseEvent event){
		return Point.valueOf(event.getClientX(), event.getClientY());
	}
	
	protected Point getRelativePoint(MouseEvent event,Element ele) {
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
		this.draggedHelper.attach();// TODO add listener in helper.
	}

}
