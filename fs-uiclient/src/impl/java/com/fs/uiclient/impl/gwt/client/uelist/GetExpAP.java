/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 28, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

/**
 * @author wu
 * 
 */
public class GetExpAP extends ActionHandlerSupport {

	/**
	 * Nov 28, 2012
	 * <p>
	 * Get the detail of the exp,user click in the list, to open the detail.
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = ae.getControl();
		String expId = (String) c.getModel().getValue(UserExpListModelI.L_EXP_ID_GET_REQUIRED);
		MsgWrapper req = this.newRequest(Path.valueOf("/exps/get"));
		req.setPayload("expId", StringData.valueOf(expId));//

		this.sendMessage(ae, req);
	}

}
