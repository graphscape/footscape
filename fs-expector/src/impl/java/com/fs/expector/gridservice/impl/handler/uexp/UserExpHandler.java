/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.expector.gridservice.impl.handler.uexp;

import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.dataservice.api.operations.ExpCreateOperationI;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class UserExpHandler extends ExpectorTMREHSupport {

	@Handle("submit")
	public void handleSubmit(TerminalMsgReceiveEW ew,HandleContextI hc, RequestI req, ResponseI res) {
		// create new user exp
		String sid = this.getSessionId(ew);// TODO Converter.
		// (String) req.getPayload("accountId");

		ExpCreateOperationI eco = this.dataService
				.prepareOperation(ExpCreateOperationI.class);
		eco.sessionId(sid);
	}

}
