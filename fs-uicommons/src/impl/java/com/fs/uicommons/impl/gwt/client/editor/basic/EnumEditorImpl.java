/**
 * Jun 30, 2012
 */
package com.fs.uicommons.impl.gwt.client.editor.basic;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicommons.api.gwt.client.editor.basic.EnumEditorI;
import com.fs.uicommons.api.gwt.client.editor.support.EditorSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.OptionElement;
import com.google.gwt.dom.client.SelectElement;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 *         <p>
 *         see ListBox in GWT.
 */
public class EnumEditorImpl extends EditorSupport<StringData> implements
		EnumEditorI {

	/** */
	public EnumEditorImpl(String name) {
		super(name, DOM.createSelect());

		this.addGwtHandler(
				com.google.gwt.event.dom.client.ChangeEvent.getType(),
				new ChangeHandler() {

					@Override
					public void onChange(
							com.google.gwt.event.dom.client.ChangeEvent event) {
						EnumEditorImpl.this.onChange();
					}
				});
	}

	// change by user.
	protected void onChange() {
		String value = this.concreteElement().getValue();
		this.setData(StringData.valueOf(value), true);//

	}

	@Override
	protected void doModel(ModelI model) {
		super.doModel(model);

	}

	@Override
	protected void processModelValue(Location loc, ValueWrapper vw) {
		super.processModelValue(loc, vw);

	}

	@Override
	protected void processModelDefaultValue(ValueWrapper vw) {
		super.processModelDefaultValue(vw);
		this.updateElement();
	}

	protected void updateElement() {
		StringData value = (StringData) this.model.getDefaultValue();
		OptionElement oe = this.getOptionElementByValue(value, false);
		if (oe == null) {
			return;
		}
		oe.setSelected(true);// is need to set other unselected?
	}

	protected OptionElement getOptionElementByValue(StringData value,
			boolean force) {
		NodeList<OptionElement> oeL = this.concreteElement().getOptions();

		for (int i = 0; i < oeL.getLength(); i++) {
			OptionElement oe = oeL.getItem(i);
			if (ObjectUtil.nullSafeEquals(
					value == null ? null : value.getValue(), oe.getValue())) {
				return oe;
			}

		}
		if (force) {
			throw new UiException("no option found by value:" + value
					+ ",all values:" + this.getValueList());
		}
		return null;
	}

	protected List<String> getValueList() {
		NodeList<OptionElement> oeL = this.concreteElement().getOptions();

		List<String> rt = new ArrayList<String>();

		for (int i = 0; i < oeL.getLength(); i++) {
			OptionElement oe = oeL.getItem(i);
			rt.add(oe.getValue());//

		}
		return rt;
	}

	@Override
	public void addOption(String option) {
		SelectElement se = this.concreteElement();
		OptionElement oe = DOM.createOption().cast();
		oe.setValue(option);
		oe.setText(option);// TODO i18n
		se.add(oe, null);//
		this.updateElement();//
	}

	private SelectElement concreteElement() {
		return getElement().cast();
	}

	/* */
	@Override
	protected void processModelDefaultValue(StringData dt) {
		String txt = dt == null ? null : dt.getValue();

	}

}
