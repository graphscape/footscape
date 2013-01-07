/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.properties;

import java.util.List;

import com.fs.uicommons.api.gwt.client.editor.properties.PropertiesEditorI;
import com.fs.uicommons.api.gwt.client.editor.support.EditorSupport;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.CellI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI.RowI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.SimpleValueDeliverI.ValueConverterI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.simple.SyncValueDeliver;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class PropertiesEditorImpl extends EditorSupport<ObjectPropertiesData>
		implements PropertiesEditorI {

	protected TableI table;

	/** */
	public PropertiesEditorImpl(String name) {
		super(name, DOM.createDiv());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.WidgetSupport#doAttach()
	 */
	@Override
	public WidgetI model(ModelI model) {
		super.model(model);
		this.table = this.factory.create(TableI.class);
		this.child(this.table);

		return this;
	}

	@Override
	protected ObjectPropertiesData newData() {
		return new ObjectPropertiesData();
	}

	/*
	 * This is called by setData(),it will set the default property in the model
	 * of editor;
	 */
	@Override
	protected void processModelDefaultValue(ObjectPropertiesData dt) {
		// model change,should update PropertyModel
		for (PropertyModel pm : this.getFieldModelList()) {
			Object value = dt == null ? null : dt.getProperty(pm.getKey());//
			Object old = (Object) pm.getDefaultValue();

			// to avoid dead loop , reversely,property model's change will
			// cause the data update also.

			if (!ObjectUtil.nullSafeEquals(value, old)) {
				pm.setDefaultValue(value);//
			}
		}

	}

	@Override
	public void processChildModelAdd(final ModelI p, final ModelI cm) {
		super.processChildModelAdd(p, cm);
		if (cm instanceof PropertyModel) {
			this.processChildPropertyModelAdd((PropertyModel) cm);
		}
	}

	protected void processChildPropertyModelAdd(final PropertyModel pm) {

		TableI.BodyI body = this.table.getBody();

		final String key = pm.getKey();
		Class<? extends EditorI> ecls = pm.getEditorClass();

		RowI r = body.createRow();

		LabelI l = this.factory.create(LabelI.class,
				SimpleModel.valueOf("unknown", key));// key i18n

		CellI cell1 = r.createCell();
		cell1.getElement().addClassName("position-left");
		cell1.child(l);

		// TODO by type to create editor
		TableI.CellI cel2 = r.createCell();
		cel2.getElement().addClassName("position-right");
		EditorI<?> editor = (EditorI<?>) this.factory.create(ecls);// editor
		editor.setName(key);//
		// class
		cel2.child(editor);

		pm.setEditor(editor);// NOTE TODO remove,this should be replaced by a
								// event
								// notification,
		pm.setValue(ModelI.L_WIDGET, editor);// TODO remove,for testting

		//
		this.syncPropertModelWithEditor(pm, editor);
		this.syncWithPropertyModel(key, pm);
	}

	protected void syncPropertModelWithEditor(final PropertyModel pm,
			EditorI editor) {
		SyncValueDeliver<Object, Object> svd = new SyncValueDeliver<Object, Object>(
				pm, ModelI.L_DEFAULT, editor.getModel(), ModelI.L_DEFAULT);

		svd.mapDefaultDirect();
		svd.getReverseValueDeliver().mapDefaultDirect();
		svd.start();// start

	}

	protected void syncWithPropertyModel(final String key,
			final PropertyModel pm) {
		pm.addValueHandler(PropertyModel.L_DEFAULT,
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						PropertiesEditorImpl.this.processPropertyModelValue(e);
					}
				});
	}

	/**
	 * Nov 18, 2012
	 */
	protected void processPropertyModelValue(ModelValueEvent e) {
		PropertyModel pm = (PropertyModel) e.getSource();
		String key = pm.getKey();
		Object value = (Object) e.getValue();

		ObjectPropertiesData data = this.getData();
		if (data == null) {
			data = new ObjectPropertiesData();
		}
		Object old = data.getProperty(key);
		if (!ObjectUtil.nullSafeEquals(old, value)) {
			data.setProperty(key, value);
			this.setData(data, true);// see: processModelDefaultValue();
		}

	}

	protected void syncWithPropertyModel_DEL(final String key, PropertyModel pm) {
		SyncValueDeliver<ObjectPropertiesData, Object> svd = new SyncValueDeliver<ObjectPropertiesData, Object>(
				this.getModel(), ModelI.L_DEFAULT, pm, ModelI.L_DEFAULT);
		svd.mapDefault(new ValueConverterI<ObjectPropertiesData, Object>() {

			@Override
			public Object convert(ObjectPropertiesData s) {
				//
				return s == null ? null : s.getProperty(key);

			}
		});

		svd.getReverseValueDeliver().mapDefault(
				new ValueConverterI<Object, ObjectPropertiesData>() {

					@Override
					public ObjectPropertiesData convert(Object s) {
						//
						ObjectPropertiesData old = PropertiesEditorImpl.this
								.getData();
						// copy old values to a new one,for calling equals when
						// the svd deliver to the editor model.
						ObjectPropertiesData rt = new ObjectPropertiesData();
						if (old != null) {
							rt.setProperties(old);//
						}

						rt.setProperty(key, s);// TODO null pointer.
						return rt;
					}
				});
		svd.start();//
	}

	@Override
	public PropertyModel addFieldModel(String key,
			Class<? extends EditorI> etype) {
		PropertyModel rt = new PropertyModel(key);
		rt.setEditorClass(etype);

		rt.parent(this.model);//

		return rt;
	}

	@Override
	public List<PropertyModel> getFieldModelList() {
		return this.model.getChildList(PropertyModel.class);
	}

	/* */
	@Override
	public PropertyModel getFieldModel(String pname) {
		return this.getChild(PropertyModel.class, name, false);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.editor.properties.PropertiesEditorI#
	 * getPropertyEditor(java.lang.String)
	 */
	@Override
	public EditorI getPropertyEditor(String name) {
		PropertyModel pm = this.getFieldModel(name);
		return pm.getWidget(EditorI.class, true);

	}

}
