/**
 * Jun 30, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.basic;

import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.editor.support.EditorSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class StringEditorImpl extends EditorSupport<String> implements StringEditorI {

	/** */
	public StringEditorImpl(ContainerI c, String name) {
		super(c, name, DOM.createInputText());

		this.addGwtHandler(com.google.gwt.event.dom.client.ChangeEvent.getType(), new ChangeHandler() {

			@Override
			public void onChange(com.google.gwt.event.dom.client.ChangeEvent event) {
				StringEditorImpl.this.onChange();
			}
		});
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
		return DOM.getElementProperty(getElement(), "value");
	}

	protected void setText(String txt) {
		DOM.setElementProperty(getElement(), "value", txt);
	}

}
