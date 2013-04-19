/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 19, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.image;

import com.fs.uicore.api.gwt.client.commons.Size;
import com.fs.uicore.api.gwt.client.support.ElementObjectSupport;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class Canvas extends ElementObjectSupport {

	protected CanvasElement canvas;

	// protected ImageElement image;// source image
	protected boolean functional;

	/**
	 * @param ele
	 */
	public Canvas(int width, int height) {
		super(null, DOM.createDiv());
		this.canvas = Document.get().createCanvasElement();
		if (this.canvas.getContext2d() == null) {
			this.functional = false;

		} else {
			this.functional = true;
			this.canvas.setWidth(width);
			this.canvas.setHeight(height);
			this.elementWrapper.append((Element) this.canvas.cast());
		}

		//
		// this.image = ImageElement.as(DOM.createImg());
		// this.elementWrapper.append((Element) this.image.cast());

	}

	public void clear() {

		double h = this.canvas.getHeight();
		double w = this.canvas.getWidth();
		double x = 0;
		double y = 0;
		this.canvas.getContext2d().clearRect(x, y, w, h);
	}

	/*
	 * private void drawImage(String dataUrl) { //this.image.setAttribute("src",
	 * dataUrl); Rectangle rec1 = ElementWrapper.valueOf(this.image)
	 * .getAbsoluteRectangle();
	 * 
	 * double sx = 0; double sy = 0; double sw = rec1.getSize().getWidth();
	 * double sh = rec1.getSize().getHeight(); double dx = 0; double dy = 0;
	 * double dw = sw; double dh = sh; //this.drawImage(dataUrl, sx, sy, sw, sh,
	 * dx, dy, dw, dh); }
	 */

	public void drawImage(ImageElement img, double sx, double sy, double sw, double sh, double dx, double dy,
			double dw, double dh) {

		// this.image.setAttribute("src", dataUrl);
		img.getAttribute("src");

		this.canvas.getContext2d().drawImage(img, sx, sy, sw, sh, dx, dy, dw, dh);

	}

	public Size getCanvasSize() {

		int w = this.canvas.getWidth();
		int h = this.canvas.getHeight();
		return Size.valueOf(w, h);

	}

	//
	// public Size getImageSize() {
	// Rectangle rec1 = ElementWrapper.valueOf(this.image)
	// .getAbsoluteRectangle();
	// return rec1.getSize();
	// }

	public String toDataUrl() {
		return this.canvas.toDataUrl();
	}

}
