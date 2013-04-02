/**
 * Jun 30, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.basic;

import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.editor.support.EditorSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.UiPropertiesI;
import com.fs.uicore.api.gwt.client.event.KeyDownEvent;
import com.fs.uicore.api.gwt.client.event.KeyUpEvent;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtChangeHandler;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtKeyDownHandler;
import com.fs.uicore.api.gwt.client.gwthandlers.GwtKeyUpHandler;
import com.fs.uicore.api.gwt.client.support.ObjectElementHelper;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class StringEditorImpl extends EditorSupport<String> implements StringEditorI {

	private boolean trim = true;
	private boolean emptyAsNull = true;
	private boolean isTextArea;
	private Element stringElement;

	/** */
	public StringEditorImpl(ContainerI c, String name, UiPropertiesI pts) {
		super(c, name, DOM.createDiv(), pts);
		this.isTextArea = (Boolean) this.getProperty(StringEditorI.PK_TEXAREA, Boolean.FALSE);
		if (this.isTextArea) {
			stringElement = DOM.createTextArea();
		} else {
			stringElement = DOM.createInputText();
		}
		this.element.appendChild(this.stringElement);
		ObjectElementHelper oeh = this.helpers.addHelper("stringElement", stringElement);
		oeh.addGwtHandler(com.google.gwt.event.dom.client.ChangeEvent.getType(), new GwtChangeHandler() {

			@Override
			public void handleInternal(com.google.gwt.event.dom.client.ChangeEvent event) {
				StringEditorImpl.this.onChange();
			}
		});
		oeh.addGwtHandler(com.google.gwt.event.dom.client.KeyDownEvent.getType(), new GwtKeyDownHandler() {

			@Override
			protected void handleInternal(com.google.gwt.event.dom.client.KeyDownEvent evt) {
				StringEditorImpl.this.onKeyDown(evt);
			}
		});
		oeh.addGwtHandler(com.google.gwt.event.dom.client.KeyUpEvent.getType(), new GwtKeyUpHandler() {

			@Override
			protected void handleInternal(com.google.gwt.event.dom.client.KeyUpEvent evt) {
				StringEditorImpl.this.onKeyUp(evt);
			}
		});
	}

	protected void onKeyUp(com.google.gwt.event.dom.client.KeyUpEvent evt) {
		this.onChange();
		new KeyUpEvent(this, evt.getNativeKeyCode(), evt.isControlKeyDown()).dispatch();
	}

	/**
	 * Apr 2, 2013
	 */
	protected void onKeyDown(com.google.gwt.event.dom.client.KeyDownEvent evt) {
		new KeyDownEvent(this, evt.getNativeKeyCode(), evt.isControlKeyDown()).dispatch();
	}

	protected void onChange() {
		String v = this.getText();
		this.setData((v), true);//

	}

	/* */
	@Override
	public void setData(String dt, boolean dis) {
		super.setData(dt, dis);
		String txt = dt == null ? "" : dt;
		this.setText(txt);
	}

	public String getText() {
		String rt = DOM.getElementProperty(this.stringElement, "value");

		if (this.trim) {
			if (rt != null) {
				rt = rt.trim();
			}
		}

		if (this.emptyAsNull && "".equals(rt)) {
			rt = null;
		}

		return rt;

	}

	protected void setText(String txt) {
		DOM.setElementProperty(this.stringElement, "value", txt);
	}

}
