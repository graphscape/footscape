/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package com.fs.dataservice.expapp.impl.elastic;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.expapp.operations.AuthedQueryOperationI;
import com.fs.dataservice.api.expapp.operations.CooperConfirmOperationI;
import com.fs.dataservice.api.expapp.operations.ExpSearchOperationI;
import com.fs.dataservice.api.expapp.wrapper.Activity;
import com.fs.dataservice.api.expapp.wrapper.CooperConfirm;
import com.fs.dataservice.api.expapp.wrapper.CooperRequest;
import com.fs.dataservice.api.expapp.wrapper.Expectation;
import com.fs.dataservice.api.expapp.wrapper.Session;
import com.fs.dataservice.api.expapp.wrapper2.ExpActivity;
import com.fs.dataservice.api.expapp.wrapper2.UserActivity;
import com.fs.dataservice.core.impl.elastic.operations.NodeQueryOperationE;
import com.fs.dataservice.expapp.impl.result.CooperConfirmResultImpl;

/**
 * @author wu
 * 
 */
public class RandomExpSearchOperationE extends NodeQueryOperationE<Expectation>
		implements ExpSearchOperationI {

	/**
	 * @param ds
	 */
	public RandomExpSearchOperationE(DataServiceI ds) {
		super(ds);
	}

	@Override
	public ExpSearchOperationI forExp(String expId) {
		//
		this.parameter(ExpSearchOperationI.PK_EXP_ID, expId);
		return this;
	}

	/*
	 * Nov 30, 2012
	 */
	@Override
	public ExpSearchOperationI keywords(String kws) {
		//
		this.parameter(ExpSearchOperationI.PK_KEYWORDS, kws);
		return this;
	}

	/*
	 *Nov 30, 2012
	 */
	@Override
	protected void executeInternal(NodeQueryResultI<Expectation> rst)
			throws Exception {
		// 
		this.nodeType(Expectation.class);
		//
		super.executeInternal(rst);
	}

}
