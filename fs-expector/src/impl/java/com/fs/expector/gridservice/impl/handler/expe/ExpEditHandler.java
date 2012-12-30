/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.handler.expe;

import com.fs.commons.api.message.MessageI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.dataservice.api.operations.ExpCreateOperationI;
import com.fs.expector.dataservice.api.result.ExpCreateResultI;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpEditHandler extends ExpectorTMREHSupport {

	@Handle("submit")
	public void handleSubmit(TerminalMsgReceiveEW ew,HandleContextI hc,  ResponseI res) {

		// the relation between activity and user.
		ExpCreateOperationI eo = this.dataService
				.prepareOperation(ExpCreateOperationI.class);
		MessageI req = ew.getMessage();//
		String body = (String) req.getPayload("body", true);

		eo.expBody(body);
		eo.sessionId(this.getSessionId(ew));

		ExpCreateResultI rst = eo.execute().getResult()
				.assertNoError();

		String eid = rst.getExpId();

		res.setPayload("expId", eid);//
	}
}
