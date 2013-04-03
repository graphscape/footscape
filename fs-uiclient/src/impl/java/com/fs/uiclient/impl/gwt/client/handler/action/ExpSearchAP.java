/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
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
		ExpSearchViewI sv = this.getControl(MainControlI.class, true).openExpSearch(false);
		Boolean isMore = ae.getPayLoadAsBoolean("isMore", false);
		int size = 0;

		if (isMore != null && isMore.booleanValue()) {
			size = sv.getSize();
		}
		
		UserExpModel ue = sv.getExpId(false);
		String expId = ue == null ? null : ue.getExpId();
		String phrase = sv.getPhrase(false);
		
		
		MsgWrapper req = this.newRequest(Path.valueOf("/exps/search"));
		req.setPayload("firstResult", Integer.valueOf(size));
		req.setPayload("maxResult", Integer.valueOf(UiClientConstants.EXPSEARCH_LIMIT));

		// the selected expId for matching.
		req.getPayloads().setProperty("expId", (expId));

		// user input keywords.
		req.getPayloads().setProperty("phrase", phrase);

		// TODO keywords
		this.sendMessage(req);
	}

}
