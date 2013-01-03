/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.cooper;

import java.util.List;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.util.ListDataUtil;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 *         <p>
 *         Snapshot notify new crList:which will cause 1)some cr should
 *         delete,those are confirmed already. 2)the new created detail should
 *         be got from this AP.
 *         <p>
 *         New created got and add to the child of the CooperModelI.
 *         UserExpListControlI should monitor this event,including deleting and
 *         adding the CR here.
 *         <p>
 * 
 */
public class RefreshIncomingCrAP extends ActionHandlerSupport {

	/*
	 * Oct 20, 2012
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = ae.getControl();
		CooperModelI cm = c.getModel();
		List<String> crIdL = cm.getNewIncomingCrIdList();
		MsgWrapper req = this.newRequest(Path.valueOf("/coooper/incomingCr"));

		req.getPayloads().setProperty("cooperRequestIdList", ListDataUtil.toStringDataList(crIdL));

		this.sendMessage(ae, req);
	}

}
