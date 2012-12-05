/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 12, 2012
 */
package com.fs.uicommons.impl.gwt.client.frwk.commons.form;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicommons.api.gwt.client.editor.basic.BooleanEditorI;
import com.fs.uicommons.api.gwt.client.editor.basic.IntegerEditorI;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.editor.properties.PropertiesEditorI;
import com.fs.uicommons.api.gwt.client.editor.properties.PropertiesEditorI.PropertyModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.data.basic.BooleanData;
import com.fs.uicore.api.gwt.client.data.basic.IntegerData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.simple.SimpleValueDeliver;
import com.fs.uicore.api.gwt.client.simple.SyncValueDeliver;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class FormView extends ViewSupport {

	private static Map<Class, Class<? extends EditorI>> typeEditorMap = new HashMap<Class, Class<? extends EditorI>>();

	private Map<String, Class<? extends EditorI>> fieldEditorMap = new HashMap<String, Class<? extends EditorI>>();

	private PropertiesEditorI propertiesEditor;

	static {
		typeEditorMap.put(StringData.class, StringEditorI.class);
		typeEditorMap.put(BooleanData.class, BooleanEditorI.class);
		typeEditorMap.put(IntegerData.class, IntegerEditorI.class);

	}

	/**
	 * @param ctn
	 */
	public FormView(ContainerI ctn) {
		super(DOM.createDiv(), ctn);
		this.propertiesEditor = this.factory.create(PropertiesEditorI.class);
		this.propertiesEditor.parent(this);//
	}

	@Override
	public void doModel(ModelI model) {
		super.doModel(model);
		SyncValueDeliver<ObjectPropertiesData, ObjectPropertiesData> svd = new SyncValueDeliver<ObjectPropertiesData, ObjectPropertiesData>(
				this.propertiesEditor.getModel(), ModelI.L_DEFAULT, this.model,
				ModelI.L_DEFAULT);
		svd.mapDefaultDirect();
		svd.getReverseValueDeliver().mapDefaultDirect();//
		svd.start();

	}

	public EditorI getEditor(String name) {
		return this.propertiesEditor.getPropertyEditor(name);
	}

	@Override
	public void processChildModelAdd(final ModelI p, final ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof FieldModel) {
			this.processChildFieldModelAdd((FieldModel) cm);
		}
	}

	/**
	 * @param cm
	 */
	private void processChildFieldModelAdd(FieldModel cm) {

		Class<? extends EditorI> etype = this.resolveEditorClass(cm);//

		PropertyModel pm = this.propertiesEditor.addFieldModel(cm.getName(),
				etype);
		pm.addDefaultValueHandler(new HandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {

				FormView.this.onEditorPropertyValueChange(e);
			}
		});
		// pipe the editor from property model to field model,then listened by
		// the sub view impl such as SignupView.
		new SimpleValueDeliver<EditorI, EditorI>(pm, PropertyModel.L_EDITOR,
				cm, FieldModel.L_EDITOR).mapDefaultDirect().start();
	}

	protected Class<? extends EditorI> resolveEditorClass(FieldModel fm) {

		Class<? extends EditorI> rt = fm.getEditorClass();

		if (rt != null) {
			return rt;
		}

		String fname = fm.getName();
		rt = this.fieldEditorMap.get(fname);
		if (rt != null) {
			return rt;
		}
		Class<?> fType = fm.getFieldType();
		rt = typeEditorMap.get(fType);
		if (rt == null) {
			throw new UiException("no editor is configured for field:" + fname
					+ ",type:" + fType);
		}
		return rt;
	}

	private void onEditorPropertyValueChange(ModelValueEvent e) {
		String name = ((PropertyModel) e.getSource()).getName();
		Object fv = e.getValueWrapper().getValue();
		// move value from property to field:
		FieldModel fm = ((FormModel) this.model).getFieldModel(name, true);
		fm.setFieldValue(fv);

	}
}
