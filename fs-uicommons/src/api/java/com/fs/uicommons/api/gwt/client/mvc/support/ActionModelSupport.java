/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.efilter.ModelValueEventFilter;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.state.State;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ActionModelSupport extends ModelSupport implements ActionModelI {

	public static final Location L_LOCAL = Location.valueOf("_local");

	public static final Location L_STATE = Location.valueOf("_state");

	public ErrorInfosData errorInfos = new ErrorInfosData();

	public ActionModelSupport(String name) {
		super(name);

	}

	public State getState() {
		return this.getValue(State.class, L_STATE, NORMAL);
	}

	public boolean isLocal() {
		return this.getValue(Boolean.class, L_LOCAL, Boolean.FALSE);
	}

	@Override
	public void setState(State st) {
		this.setValue(L_STATE, st);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.mvc.ActionModelI#getControlModel()
	 */
	@Override
	public ModelI getControlModel() {
		return (ModelI) this.parent;
	}

	@Override
	public void trigger() {
		this.setState(TRIGGERED);
	}

	@Override
	public void addTriggerHandler(EventHandlerI<ModelValueEvent> eh) {
		this.addHandler(new ModelValueEventFilter(L_STATE, TRIGGERED), eh);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.ActionModelI#addAfterHandler(com.
	 * fs.uicore.api.gwt.client.core.Event.HandlerI)
	 */
	@Override
	public void addProcessedHandler(EventHandlerI<ModelValueEvent> ah) {
		this.addHandler(new ModelValueEventFilter(L_STATE, PROCESSED), ah);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.mvc.ActionModelI#processed()
	 */
	@Override
	public void processed(ErrorInfosData eis) {

		this.errorInfos.getErrorInfoList().clear();// clear previous error info.

		this.errorInfos.getErrorInfoList().addAll(eis.getErrorInfoList());//

		// NOTE ,trigger the processed value event.
		this.setState(ActionModelI.PROCESSED);
		//
		this.setState(ActionModelI.NORMAL);

	}

	/*
	 * Nov 13, 2012
	 */
	@Override
	public ErrorInfosData getErrorInfos() {
		//
		return this.errorInfos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicommons.api.gwt.client.mvc.ActionModelI#setHidden(boolean)
	 */
	@Override
	public void setHidden(boolean hidden) {
		this.setValue(L_HIDDEN, hidden);
	}

}
