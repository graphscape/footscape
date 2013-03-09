/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 8, 2013
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.PropertiesData;
import com.fs.uicore.api.gwt.client.event.ClickEvent;

/**
 * @author wu
 * 
 */
public class CooperRequestMessageView extends ExpMessageView {

	public static CreatorI CRT = new CreatorI() {

		@Override
		public ExpMessageView create(ContainerI c, ExpMessage msg) {
			//
			return new CooperRequestMessageView(c, msg);
		}
	};

	/**
	 * @param c
	 * @param msg
	 */
	public CooperRequestMessageView(ContainerI c, ExpMessage msg) {
		super(c, msg);
		ButtonI ok = this.factory.create(ButtonI.class);
		ok.setText(true, "ok");
		ok.parent(this.actions);
		ok.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent t) {
				CooperRequestMessageView.this.onOk();
			}
		});
	}

	protected void onOk() {
		this.dispatchActionEvent(Actions.A_UEXP_COOPER_CONFIRM);
	}

	/*
	 *Mar 9, 2013
	 */
	@Override
	protected void beforeActionEvent(ActionEvent ae) {
		super.beforeActionEvent(ae);
		
		String crId = msg.getPayload("cooperRequestId", true);
		
		ae.setProperty("cooperRequestId", crId);
	}
}
