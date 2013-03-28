/**
 * Jul 1, 2012
 */
package com.fs.uicommons.api.gwt.client.editor.properties;

import java.util.List;

import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public interface PropertiesEditorI extends EditorI<ObjectPropertiesData> {

	public class PropertyModel {
		private String name;
		private EditorI editor;
		private Class<? extends EditorI> editorClass;
		private String i18nKey;

		public String getI18nKey() {
			return i18nKey;
		}

		/**
		 * @param name
		 */
		public PropertyModel(String name, String i18nKey, Class<? extends EditorI> editorClass) {
			this.name = name;
			this.i18nKey = i18nKey;
			this.editorClass = editorClass;
		}

		public String getKey() {
			return this.name;
		}

		public void setEditor(EditorI editor) {
			this.editor = editor;
		}

		public EditorI getEditor(boolean force) {
			return this.editor;
		}

		public Class<? extends EditorI> getEditorClass() {
			return this.editorClass;
		}

	}

	public PropertyModel addFieldModel(String key, Class<? extends EditorI> etype);

	public PropertyModel addFieldModel(String key, Class<? extends EditorI> etype, String i18nKey);

	public PropertyModel getFieldModel(String key);

	public List<PropertyModel> getFieldModelList();

	public EditorI getPropertyEditor(String name);
	
	public void setFieldValue(String fname, Object v);

	public void addProperty(final PropertyModel pm);

}
