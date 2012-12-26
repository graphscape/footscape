/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.efilter.ActionEventFilter;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.session.SessionModelI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.SimpleValueDeliverI.ValueConverterI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.core.Event.Type;
import com.fs.uicore.api.gwt.client.simple.SimpleValueDeliver;
import com.fs.uicore.api.gwt.client.state.State;

/**
 * @author wuzhen
 * 
 */
public class ControlSupport extends AbstractControl {

	public ControlSupport(String name) {
		super(name);
	}

	public void triggerAction(String action) {
		ControlUtil.triggerAction(this.model, action);
	}

	@Override
	public void addActionEventHandler(String a, EventHandlerI<ActionEvent> eh) {
		this.addHandler(new ActionEventFilter(a), eh);
	}

	@Override
	public <T extends ActionEvent> void addActionEventHandler(Type<T> type,
			String action, EventHandlerI<T> eh) {
		this.addHandler(new ActionEventFilter(type, action), eh);
	}

	// the action will be triggered after auth success event.
	protected void addAuthProcessorAction(final String action) {

		SessionModelI sm = this.getSessionModel(true);
		final ActionModelI am = ControlUtil.getAction(ControlSupport.this.model,
				action, true);
		new SimpleValueDeliver<Boolean, State>(sm, SessionModelI.L_IS_AUTHED,
				am, ActionModelI.L_STATE)
				.mapDefault(new ValueConverterI<Boolean, State>() {

					@Override
					public State convert(Boolean s) {
						// 
						if(s != null && s){
							am.trigger();
						}
						return null;
					}
				}).start();

	}

	@Override
	public SessionModelI getSessionModel(boolean force) {
		return this.model.getTopObject().find(SessionModelI.class, force);

	}

	/*
	 * Oct 24, 2012
	 */
	@Override
	public void processChildModelRemove(ModelI parent, ModelI child) {
		//

	}

}
