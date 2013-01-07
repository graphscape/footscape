/**
 * Jun 30, 2012
 */
package com.fs.uicommons.api.gwt.client.editor.support;

import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.api.gwt.client.widget.support.LayoutSupport;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class EditorSupport<T> extends LayoutSupport implements EditorI<T> {

	/** */
	public EditorSupport(String name, Element ele) {
		super(name, ele);
	}

	/* */
	@Override
	public T getData() {

		return (T) this.model.getValue(ModelI.L_DEFAULT);

	}

	@Override
	public WidgetI model(ModelI mo) {
		super.model(mo);
		this.setData(this.newData(), false);//
		return this;
	}

	protected T newData() {
		return null;
	}

	/* */
	@Override
	public void input(T d) {
		this.setData(d, true);//

	}

	protected void setData(T d, boolean dispatch) {
		// TODO for dispatch
		boolean iss = this.model.isSilent();
		this.model.silent(!dispatch);
		try {
			this.model.setValue(ModelI.L_DEFAULT, d);
		} finally {
			this.model.silent(iss);//
		}
	}

	protected void processModelDefaultValue(ValueWrapper vw) {
		T data = (T) vw.getValue();
		this.processModelDefaultValue(data);
	}

	protected void processModelDefaultValue(T data) {

	}

}
