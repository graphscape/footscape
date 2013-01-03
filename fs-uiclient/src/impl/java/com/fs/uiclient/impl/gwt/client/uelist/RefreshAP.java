/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.basic.DateData;

/**
 * @author wu
 * 
 */
public class RefreshAP extends ActionHandlerSupport {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		//

		UserExpListModel uelm = ae.getControl().getModel().cast();
		Long lts = uelm.getLastTimestamp(false);
		MsgWrapper req = this.newRequest(Path.valueOf("/uelist/refresh"));
		req.setPayload("lastTimestamp", DateData.valueOf(lts));// fresh from
																// here
		this.sendMessage(ae, req);
	}

}
