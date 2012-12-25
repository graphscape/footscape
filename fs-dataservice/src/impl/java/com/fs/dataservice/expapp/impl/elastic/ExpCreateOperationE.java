/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 4, 2012
 */
package com.fs.dataservice.expapp.impl.elastic;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.ResultI;
import com.fs.dataservice.api.expapp.operations.ExpCreateOperationI;
import com.fs.dataservice.api.expapp.result.ExpCreateResultI;
import com.fs.dataservice.api.expapp.wrapper.Expectation;
import com.fs.dataservice.api.expapp.wrapper.Session;
import com.fs.dataservice.expapp.impl.elastic.support.AuthedOperationSupport;
import com.fs.dataservice.expapp.impl.result.ExpCreateResultImpl;

/**
 * @author wu
 * 
 */
public class ExpCreateOperationE extends
		AuthedOperationSupport<ExpCreateOperationI, ExpCreateResultI> implements
		ExpCreateOperationI {

	public static final String PK_BODY = "body";

	/**
	 * @param ds
	 */
	public ExpCreateOperationE(DataServiceI ds) {
		super(new ExpCreateResultImpl(ds));
	}

	/*
	 * Nov 4, 2012
	 */
	@Override
	public ExpCreateOperationI expBody(String body) {
		//
		this.parameter(PK_BODY, body);
		return this;
	}

	public String getBody() {
		return (String) this.getParameter(PK_BODY, true);
	}

	/*
	 * Nov 4, 2012
	 */
	@Override
	protected void executeInternal(Session login, ExpCreateResultI rst) throws Exception {
		//
		super.executeInternal(login, rst);

		Expectation exp = new Expectation().forCreate(this.dataService);
		exp.setAccountId(login.getAccountId());
		exp.setBody(this.getBody());
		exp.save(true);

		rst.setProperty(ExpCreateResultImpl.EXPID, exp.getId());
	}

}
