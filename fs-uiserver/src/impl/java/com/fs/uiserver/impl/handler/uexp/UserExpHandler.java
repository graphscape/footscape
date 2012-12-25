/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.uiserver.impl.handler.uexp;

import com.fs.dataservice.api.expapp.operations.ExpCreateOperationI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.uiserver.impl.handler.support.UiHandlerSupport;

/**
 * @author wu
 * 
 */
public class UserExpHandler extends UiHandlerSupport {

	@Handle("submit")
	public void handleSubmit(HandleContextI hc, RequestI req, ResponseI res) {
		// create new user exp
		String sid = this.getSessionId(hc);// TODO Converter.
		// (String) req.getPayload("accountId");

		ExpCreateOperationI eco = this.dataService
				.prepareOperation(ExpCreateOperationI.class);
		eco.sessionId(sid);
	}

}
