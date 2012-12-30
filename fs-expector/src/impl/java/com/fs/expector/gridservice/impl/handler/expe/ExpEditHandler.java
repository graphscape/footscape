/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 3, 2012
 */
package com.fs.expector.gridservice.impl.handler.expe;

import com.fs.commons.api.message.MessageI;
import com.fs.engine.api.HandleContextI;
import com.fs.engine.api.ResponseI;
import com.fs.engine.api.annotation.Handle;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpEditHandler extends ExpectorTMREHSupport {

	@Handle("submit")
	public void handleSubmit(TerminalMsgReceiveEW ew, HandleContextI hc, ResponseI res) {

		// the relation between activity and user.

		MessageI req = ew.getMessage();//
		String body = (String) req.getPayload("body", true);

		Expectation exp = new Expectation().forCreate(this.dataService);

		exp.setAccountId(this.getAccountId(ew, true));
		exp.setBody(body);
		exp.save(true);

		String eid = exp.getId();

		res.setPayload("expId", eid);//
	}
}
