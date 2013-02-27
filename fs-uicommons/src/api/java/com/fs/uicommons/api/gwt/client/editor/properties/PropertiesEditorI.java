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

		/**
		 * @param name
		 */
		public PropertyModel(String name, Class<? extends EditorI> editorClass) {
			this.name = name;
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

	public PropertyModel getFieldModel(String key);

	public List<PropertyModel> getFieldModelList();

	public EditorI getPropertyEditor(String name);

	public void addProperty(final PropertyModel pm);

}
