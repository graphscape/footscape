/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 12, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk.commons;

import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class FieldModel extends ModelSupport {

	public static final Location L_FIELD_TYPE = Location.valueOf("_fieldType");

	public static final Location L_EDITOR = Location.valueOf("_editor");//

	private Class<? extends EditorI> editorClass;

	/**
	 * @param name
	 */
	public FieldModel(String name) {
		super(name);
	}

	public void setFieldType(Class<?> ftype) {
		this.setValue(L_FIELD_TYPE, ftype);

	}

	public void setEditor(EditorI editor) {
		this.setValue(L_EDITOR, editor);
	}

	public EditorI getEditor(boolean force) {
		return (EditorI) this.getValue(L_EDITOR, force);
	}

	public Class<?> getFieldType() {
		return (Class<?>) this.getValue(L_FIELD_TYPE);
	}

	public void setFieldValue(Object fv) {
		this.setValue(ModelI.L_DEFAULT, fv);
	}

	public Object getFieldValue() {
		return this.getValue(ModelI.L_DEFAULT);
	}

	public Class<? extends EditorI> getEditorClass() {
		return editorClass;
	}

	public void setEditorClass(Class<? extends EditorI> editorClass) {
		this.editorClass = editorClass;
	}

}
