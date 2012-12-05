/**
 * Jul 1, 2012
 */
package com.fs.uicommons.api.gwt.client.editor.properties;

import java.util.List;

import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.efilter.ModelValueEventFilter;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public interface PropertiesEditorI extends EditorI<ObjectPropertiesData> {

	public class PropertyModel extends ModelSupport {

		public static String EDITOR_CLASS = "_editorClass";

		public static final Location L_EDITOR = Location.valueOf("_editor");//

		/**
		 * @param name
		 */
		public PropertyModel(String name) {
			super(name);
		}

		public String getKey() {
			return this.name;
		}

		public void setEditor(EditorI editor) {
			this.setValue(L_EDITOR, editor);
		}

		public EditorI getEditor(boolean force) {
			return (EditorI) this.getValue(L_EDITOR, force);
		}
		
		public void setEditorClass(Class<? extends EditorI> ecls) {
			this.setValue(EDITOR_CLASS, ecls);
		}

		public Class<? extends EditorI> getEditorClass() {
			return (Class<? extends EditorI>) this.getValue(EDITOR_CLASS);
		}

	}

	public PropertyModel addFieldModel(String key,
			Class<? extends EditorI> etype);

	public PropertyModel getFieldModel(String key);

	public List<PropertyModel> getFieldModelList();

	public EditorI getPropertyEditor(String name);

}
