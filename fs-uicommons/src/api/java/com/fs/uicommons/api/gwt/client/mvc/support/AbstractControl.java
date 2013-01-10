/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.efilter.ModelValueEventFilter;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.model.ModelChildProcessorI;
import com.fs.uicore.api.gwt.client.support.UiObjectSupport;

/**
 * @author wuzhen
 * 
 */
public abstract class AbstractControl extends UiObjectSupport implements ControlI, ModelChildProcessorI {

	protected String name;

	protected ModelI model;

	public AbstractControl(String name) {
		this.name = name;
	}

	@Override
	public ControlI model(ModelI cm) {
		if (this.isAttached()) {
			throw new UiException("NOT support after already attached:" + this);
		}
		this.model = cm;
		this.doModel(cm);
		return this;
	}

	protected void doModel(ModelI cm) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#doAttach()
	 */
	@Override
	protected void doAttach() {
		super.doAttach();
		ModelChildProcessorI.Helper.onAttach(this.model, this);//
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public ControlManagerI getManager() {

		return (ControlManagerI) this.parent;

	}

	@Override
	public void processChildModelAdd(final ModelI parent, final ModelI cm) {
		if (cm instanceof ActionModelI) {
			this.processChildAModelAdd((ActionModelI) cm);
		}
	}

	protected void processChildAModelAdd(ActionModelI amodel) {
		amodel.addHandler(new ModelValueEventFilter(ActionModelI.L_STATE),
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						AbstractControl.this.handleActionStateChanging(e);
					}
				});
	}

	protected void handleActionStateChanging(ModelValueEvent e) {
		ActionModelI am = (ActionModelI) e.getSource();
		ValueWrapper vw = e.getValueWrapper();

		if (vw.isValue(ActionModelI.TRIGGERED)) {
			vw.setValue(ActionModelI.PROCESSING);// NOTE //TODO raise event.
			Path ap = am.getActionPath();
			new ActionEvent(this, ap).dispatch();

		}

	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public <T extends ModelI> T getModel() {
		//
		return (T) this.model;
	}

}
