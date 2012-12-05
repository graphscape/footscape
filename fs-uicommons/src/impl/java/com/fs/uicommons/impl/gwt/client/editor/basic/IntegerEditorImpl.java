/**
 * Jun 30, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.basic;

import com.fs.uicommons.api.gwt.client.editor.basic.IntegerEditorI;
import com.fs.uicommons.api.gwt.client.editor.support.EditorSupport;
import com.fs.uicore.api.gwt.client.data.basic.IntegerData;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class IntegerEditorImpl extends EditorSupport<IntegerData> implements
		IntegerEditorI {

	/** */
	public IntegerEditorImpl(String name) {
		super(name, DOM.createInputText());

		this.addGwtHandler(
				com.google.gwt.event.dom.client.ChangeEvent.getType(),
				new ChangeHandler() {

					@Override
					public void onChange(
							com.google.gwt.event.dom.client.ChangeEvent event) {
						IntegerEditorImpl.this.onChange();
					}
				});
	}

	protected void onChange() {
		String s = this.getText();
		Integer v = null;
		try {
			v = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			// TODO error indicator
		}

		this.setData(IntegerData.valueOf(v), true);//

	}

	/* */
	@Override
	protected void processModelDefaultValue(IntegerData dt) {
		String txt = dt == null ? "" : dt.getValue() + "";

		this.setText(txt);
	}

	public String getText() {
		return DOM.getElementProperty(getElement(), "value");
	}

	protected void setText(String txt) {
		DOM.setElementProperty(getElement(), "value", txt);
	}

}
