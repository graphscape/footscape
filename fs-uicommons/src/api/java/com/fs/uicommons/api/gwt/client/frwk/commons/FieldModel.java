/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 12, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk.commons;

import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;

/**
 * @author wu
 * 
 */
public class FieldModel {

	private String name;

	private EditorI editor;

	private Class<?> type;

	private Class<? extends EditorI> editorClass;

	private Object value;

	private UiCallbackI< EditorI, Object> editorCallback;
	
	/**
	 * @param name
	 */
	public FieldModel(String name, Class<?> type, Class<? extends EditorI> eclass,UiCallbackI< EditorI, Object> editorCallback) {
		this.name = name;
		this.type = type;
		this.editorClass = eclass;
		this.editorCallback = editorCallback;
	}
	public FieldModel(String name, Class<?> type, Class<? extends EditorI> eclass) {
		this(name,type,eclass,null);
	}
	
	public void setEditor(EditorI editor) {
		this.editor = editor;
		if(this.editorCallback!=null){
			this.editorCallback.execute(this.editor);
		}
	}

	public EditorI getEditor(boolean force) {
		if (editor == null && force) {
			throw new UiException("eidtor not init");
		}
		return this.editor;
	}

	public Class<?> getFieldType() {
		return this.type;
	}

	public void setFieldValue(Object fv) {
		this.value = fv;
	}

	public Object getFieldValue() {
		return this.value;
	}

	public Class<? extends EditorI> getEditorClass() {
		return editorClass;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 *Feb 23, 2013
	 */
	public void addValueHandler(HandlerI<Object> handlerI) {
		//
		throw new UiException("TODO");
		
	}


}
