/**
 *  Feb 1, 2013
 */
package com.fs.uicommons.api.gwt.client.frwk.commons;

import java.util.List;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wuzhen
 * 
 */
public interface FormViewI extends ViewI {

	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls);

	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Map<String, Object> editorPts);

	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Class<T> editorClass,
			final UiCallbackI<T, Object> editorCallback);

	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Class<T> editorClass);

	public <T extends EditorI> FieldModel addField(String name, Class<?> dcls, Class<T> editorClass,
			Map<String, Object> editorPts);

	public FormModel getFormModel();

	public ObjectPropertiesData getData();

	public List<Path> getActionList();

	public <T> T getFieldData(String fname, T def);

	public <T> T getFieldData(String fname);

	public void setFieldValue(String fname, Object value);

}
