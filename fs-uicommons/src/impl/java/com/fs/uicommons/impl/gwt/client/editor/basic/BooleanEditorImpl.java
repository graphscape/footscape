/**
 * Jun 30, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.basic;

import com.fs.uicommons.api.gwt.client.editor.basic.BooleanEditorI;
import com.fs.uicommons.api.gwt.client.editor.support.EditorSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class BooleanEditorImpl extends EditorSupport<Boolean> implements BooleanEditorI {

	private InputElement element;

	/** */
	public BooleanEditorImpl(ContainerI c, String name) {
		super(c, name, DOM.createInputCheck());//
		this.element = super.element.cast();//
		this.addGwtHandler(com.google.gwt.event.dom.client.ChangeEvent.getType(), new ChangeHandler() {

			@Override
			public void onChange(com.google.gwt.event.dom.client.ChangeEvent event) {
				BooleanEditorImpl.this.onChange();
			}
		});
	}

	protected void onChange() {
		boolean isc = this.isChecked();

		this.setData((isc), true);//

	}

	/* */
	@Override
	public boolean setData(Boolean dt, boolean dis) {
		boolean rt = super.setData(dt, dis);
		if(rt){
			
		Boolean ck = dt == null ? false : dt;
		this.element.setChecked(ck);
		}
		return rt;
	}

	public boolean isChecked() {
		return this.element.isChecked();
	}

}
