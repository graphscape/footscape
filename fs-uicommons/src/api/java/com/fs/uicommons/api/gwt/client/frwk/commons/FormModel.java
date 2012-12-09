/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 12, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class FormModel extends ModelSupport {

	public static final Location L_ACTION_LIST = Location.valueOf("actionList");

	/**
	 * @param name
	 */
	public FormModel(String name) {
		super(name);
	}

	public FieldModel addField(String name, Class<? extends UiData> dcls) {
		return this.addField(name, dcls, null);// default editor class
	}

	public <T extends EditorI> FieldModel addField(String name,
			Class<? extends UiData> dcls, Class<T> editorClass) {
		return this.addField(name, dcls, editorClass, null);
	}

	public <T extends EditorI> FieldModel addField(String name,
			Class<? extends UiData> dcls, Class<T> editorClass,
			final UiCallbackI<T, Object> editorCallback) {
		FieldModel rt = new FieldModel(name);
		rt.setEditorClass(editorClass);
		rt.setFieldType(dcls);
		if (editorCallback != null) {// this should be some thing like
										// "EditorInitializer".
			rt.addValueHandler(FieldModel.L_EDITOR,
					new HandlerI<ModelValueEvent>() {

						@Override
						public void handle(ModelValueEvent e) {
							T editor = (T) e.getValue();
							editorCallback.execute(editor);

						}
					});// TODO add a direct value callbackI?
		}
		rt.parent(this);
		return rt;
	}

	public ObjectPropertiesData getData() {
		return (ObjectPropertiesData) this.getDefaultValue();

	}

	public <T> T getFieldValue(String fname, T def) {
		FieldModel fm = this.getFieldModel(fname, false);

		T rt = (T) fm.getFieldValue();

		return rt == null ? def : rt;
	}

	public FieldModel getFieldModel(String name, boolean force) {

		List<FieldModel> fmL = this.getChildList(FieldModel.class);
		for (FieldModel fm : fmL) {
			if (fm.getName().equals(name)) {
				return fm;
			}
		}
		if (force) {
			throw new UiException("force:" + name + ",in:" + this);
		}
		return null;
	}

	public void addAction(String... names) {
		List<String> actionList = this.getActionList();
		actionList.addAll(Arrays.asList(names));
		this.setValue(L_ACTION_LIST, actionList);

	}

	public List<String> getActionList() {
		List<String> rt = (List<String>) this.getValue(L_ACTION_LIST);
		return rt == null ? new ArrayList() : rt;
	}

}
