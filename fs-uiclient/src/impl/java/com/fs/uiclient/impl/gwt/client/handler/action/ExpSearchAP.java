/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class ExpSearchAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public ExpSearchAP(ContainerI c) {
		super(c);
	}

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		//
		ExpSearchControlI sm = this.getControl(ExpSearchControlI.class, true);

		String expId = sm.getExpId(false);
		String phrase = sm.getPhrase(false);
		int pg = sm.getFirstResult();
		MsgWrapper req = this.newRequest(Path.valueOf("/exps/search"));
		req.setPayload("firstResult", Integer.valueOf(pg));
		req.setPayload("maxResult", Integer.valueOf(sm.getMaxResult()));

		// the selected expId for matching.
		req.getPayloads().setProperty("expId", (expId));

		// user input keywords.
		req.getPayloads().setProperty("phrase", phrase);

		// TODO keywords
		this.sendMessage(req);
	}

}
