/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.basic.IntegerData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wu
 * 
 */
public class SearchAP extends ActionHandlerSupport {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		//
		ControlI c = ae.getControl();
		ExpSearchModelI sm = (ExpSearchModelI) c.getModel();
		String expId = sm.getExpId(true);

		int pg = sm.getPageNumber();
		MsgWrapper req = this.newRequest(Path.valueOf("/exps/search"));
		req.setPayload("pageNumber", IntegerData.valueOf(pg));
		req.setPayload("pageSize", IntegerData.valueOf(sm.getPageSize()));

		// the selected expId for matching.
		req.getPayloads().setProperty("expId", StringData.valueOf(expId));

		// user input keywords.
		req.getPayloads().setProperty("keywords", StringData.valueOf(""));

		// TODO keywords
		this.sendMessage(ae, req);
	}

}
