/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 30, 2012
 */
package com.fs.expector.gridservice.impl.handler.exps;

import java.util.List;

import com.fs.commons.api.message.MessageContext;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.ResponseI;
import com.fs.commons.api.service.Handle;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.expector.dataservice.api.operations.ExpSearchOperationI;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper.Profile;
import com.fs.expector.gridservice.api.support.ExpectorTMREHSupport;
import com.fs.gridservice.commons.api.wrapper.TerminalMsgReceiveEW;

/**
 * @author wu
 * 
 */
public class ExpSearchHandler extends ExpectorTMREHSupport {

	@Handle("search")
	public void handleSearch(MessageContext hc, TerminalMsgReceiveEW ew, ResponseI res) {
		MessageI req = ew.getMessage();//
		int pageNumber = (Integer) req.getPayload("pageNumber", true);
		int pageSize = (Integer) req.getPayload("pageSize", true);
		String expId = (String) req.getPayload("expId");// may null
		String keywords = (String) req.getPayload("keywords");// may null

		ExpSearchOperationI eso = this.dataService
				.prepareOperation(ExpSearchOperationI.class);
		int from = pageNumber * pageSize;

		eso.first(from);
		eso.maxSize(pageSize);
		eso.forExp(expId);
		eso.keywords(keywords);

		NodeQueryResultI<Expectation> rst = eso.execute().getResult()
				.assertNoError();
		// convert
		List<PropertiesI<Object>> el = NodeWrapperUtil.convert(rst.list(),
				new String[] { NodeI.PK_ID, Expectation.BODY,
						NodeI.PK_TIMESTAMP, Expectation.ACCOUNT_ID },//
				new boolean[] { true, true, true, true }, // force
				new String[] { "id", "body", "timestamp", "accountId" }//
				);
		for (PropertiesI<Object> pts : el) {
			String accId = (String) pts.getProperty("accountId");
			// account must be exist
			Account acc = this.dataService.getNewestById(Account.class, accId,
					true);
			pts.setProperty("nick", acc.getNick());
			
			// profile may not exist.
			
			Profile pf = this.dataService.getNewest(Profile.class,
					Profile.ACCOUNTID, accId, false);
			String icon = pf == null ? null : pf.getIcon();
			if (icon == null) {
				icon = this.config.getProperty("defaultIconDataUrl");//
			}
			pts.setProperty("iconDataUrl", icon);//

		}

		res.setPayload("expectations", el);

	}
}