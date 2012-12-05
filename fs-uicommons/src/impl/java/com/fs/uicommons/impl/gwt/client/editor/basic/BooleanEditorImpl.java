/**
 * Jun 30, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.basic;

import com.fs.uicommons.api.gwt.client.editor.basic.BooleanEditorI;
import com.fs.uicommons.api.gwt.client.editor.support.EditorSupport;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class BooleanEditorImpl extends EditorSupport<BooleanData> implements
		BooleanEditorI {

	private InputElement element;

	/** */
	public BooleanEditorImpl(String name) {
		super(name, DOM.createInputCheck());//
		this.element = super.element.cast();//
		this.addGwtHandler(
				com.google.gwt.event.dom.client.ChangeEvent.getType(),
				new ChangeHandler() {

					@Override
					public void onChange(
							com.google.gwt.event.dom.client.ChangeEvent event) {
						BooleanEditorImpl.this.onChange();
					}
				});
	}

	protected void onChange() {
		boolean isc = this.isChecked();

		this.setData(BooleanData.valueOf(isc), true);//

	}

	/* */
	@Override
	protected void processModelDefaultValue(BooleanData dt) {
		Boolean ck = dt == null ? false : dt.getValue();
		this.setChecked(ck);
	}

	public boolean isChecked() {
		return this.element.isChecked();
	}

	public void setChecked(boolean checked) {
		this.element.setChecked(checked);
	}

}
