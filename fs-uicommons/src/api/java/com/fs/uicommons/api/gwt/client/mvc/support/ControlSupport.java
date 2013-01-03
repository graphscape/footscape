/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc.support;

import com.fs.uicommons.api.gwt.client.mvc.efilter.ActionEventFilter;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

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

	/*
	 * Oct 24, 2012
	 */
	@Override
	public void processChildModelRemove(ModelI parent, ModelI child) {
		//

	}

}
