/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 12, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk.commons;

import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class FormsModel extends ModelSupport {

	public static final String DEFAULT_FORM = "default";

	public static final Location L_CURRENT_FORM = Location
			.valueOf("_currentForm");

	/**
	 * @param name
	 */
	public FormsModel(String name) {
		super(name);
		this.addForm(DEFAULT_FORM);
		this.setCurrentFormName(DEFAULT_FORM);//
	}

	public FormModel getForm(String name, boolean force) {
		return this.getChild(FormModel.class, name, force);
	}

	public FormModel getDefaultForm() {
		return this.getForm(DEFAULT_FORM, true);
	}

	public void setCurrentFormName(String name) {
		this.setValue(L_CURRENT_FORM, name);
	}

	public String getCurrentFormName() {
		return this.getValue(String.class, L_CURRENT_FORM, DEFAULT_FORM);
	}

	public FormModel getCurrentForm() {
		String fname = this.getCurrentFormName();
		return this.getForm(fname, true);
	}

	public void trySetCurrentFormName(String fname) {
		if (fname.equals(this.getCurrentFormName())) {
			return;
		}
		this.setValue(L_CURRENT_FORM, fname);
	}

	public FormModel addForm(String name) {
		if (getForm(name, false) != null) {
			throw new UiException("existed form:" + name + ",in:" + this);
		}
		FormModel rt = new FormModel(name);
		rt.parent(this);//
		this.setValue(L_CURRENT_FORM, name);// TODO policy of select the current
											// form.
		// this will cause the view updated into the right screen.
		return rt;
	}

}
