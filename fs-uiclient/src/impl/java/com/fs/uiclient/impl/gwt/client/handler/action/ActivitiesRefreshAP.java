/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import java.util.List;

import com.fs.uiclient.api.gwt.client.util.ListDataUtil;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class ActivitiesRefreshAP extends ActionHandlerSupport {

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = ae.getControl();
		
		
		List<String> actIdL = null;//
		MsgWrapper req = this.newRequest(Path.valueOf("/activities/activities"));
		
		req.setPayload("idList", ListDataUtil.toStringDataList(actIdL));//
		this.sendMessage(ae, req);
	}

	/*
	 * Oct 21, 2012
	 */
	

}
