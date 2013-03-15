/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 8, 2013
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class TextMessageView extends ExpMessageView {

	public static CreatorI CRT = new CreatorI() {

		@Override
		public ExpMessageView create(ContainerI c, ExpMessage msg) {
			//
			return new TextMessageView(c, msg);
		}
	};

	/**
	 * @param c
	 * @param msg
	 */
	public TextMessageView(ContainerI c, ExpMessage msg) {
		super(c, msg);
		Element ele = DOM.createDiv();
		String text = msg.getPayLoadAsString("text", true);
		ele.setInnerText(text);
		this.element.appendChild(ele);
	}

	protected void onOk() {
		
		this.dispatchActionEvent(Actions.A_UEXP_COOPER_CONFIRM);
	}

	/*
	 * Mar 9, 2013
	 */
	@Override
	protected void beforeActionEvent(ActionEvent ae) {
		super.beforeActionEvent(ae);

		String crId = msg.getPayload("cooperRequestId", true);

		ae.setProperty("cooperRequestId", crId);
	}
}
