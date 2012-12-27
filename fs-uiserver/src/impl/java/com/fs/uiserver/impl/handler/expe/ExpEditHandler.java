/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.uiserver.impl.handler.expe;

import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.RequestI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.dataservice.api.operations.ExpCreateOperationI;
import com.fs.expector.dataservice.api.result.ExpCreateResultI;
import com.fs.uiserver.impl.handler.support.UiHandlerSupport;

/**
 * @author wu
 * 
 */
public class ExpEditHandler extends UiHandlerSupport {

	@Handle("submit")
	public void handleSubmit(HandleContextI hc, RequestI req, ResponseI res) {

		// the relation between activity and user.
		ExpCreateOperationI eo = this.dataService
				.prepareOperation(ExpCreateOperationI.class);
		String body = (String) req.getPayload("body", true);

		eo.expBody(body);
		eo.sessionId(this.getSessionId(hc));

		ExpCreateResultI rst = eo.execute().getResult()
				.assertNoError();

		String eid = rst.getExpId();

		res.setPayload("expId", eid);//
	}
}
