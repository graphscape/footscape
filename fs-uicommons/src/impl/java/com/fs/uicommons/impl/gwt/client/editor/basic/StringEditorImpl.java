/**
 * Jun 30, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.basic;

import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.editor.support.EditorSupport;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class StringEditorImpl extends EditorSupport<StringData> implements
		StringEditorI {

	/** */
	public StringEditorImpl(String name) {
		super(name, DOM.createInputText());

		this.addGwtHandler(
				com.google.gwt.event.dom.client.ChangeEvent.getType(),
				new ChangeHandler() {

					@Override
					public void onChange(
							com.google.gwt.event.dom.client.ChangeEvent event) {
						StringEditorImpl.this.onChange();
					}
				});
	}

	protected void onChange() {
		String v = this.getText();
		this.setData(StringData.valueOf(v), true);//

	}

	/* */
	@Override
	protected void processModelDefaultValue(StringData dt) {
		String txt = dt == null ? "" : dt.getValue();
		this.setText(txt);
	}

	public String getText() {
		return DOM.getElementProperty(getElement(), "value");
	}

	protected void setText(String txt) {
		DOM.setElementProperty(getElement(), "value", txt);
	}

}
