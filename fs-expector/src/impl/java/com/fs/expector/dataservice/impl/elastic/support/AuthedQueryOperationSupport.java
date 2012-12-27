/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package com.fs.expector.dataservice.impl.elastic.support;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.InterceptorI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.core.impl.elastic.operations.NodeQueryOperationE;
import com.fs.expector.dataservice.api.operations.AuthedOperationI;
import com.fs.expector.dataservice.api.operations.AuthedQueryOperationI;
import com.fs.expector.dataservice.api.wrapper.Session;
import com.fs.expector.dataservice.impl.elastic.util.AuthedUtil;

/**
 * @author wu
 * 
 */
public class AuthedQueryOperationSupport extends NodeQueryOperationE implements
		AuthedQueryOperationI {
	/**
	 * @param ds
	 */
	public AuthedQueryOperationSupport(DataServiceI ds) {
		super(ds);
		this.addBeforeInterceptor(new InterceptorI<NodeQueryOperationI>() {

			@Override
			public void intercept(NodeQueryOperationI opt) {
				AuthedQueryOperationSupport.this.beforeQuery();
			}
		});
	}

	protected void beforeQuery() {

		Session login = AuthedUtil.beforeAuthedOperation(this, this.dataService,
				this.getResult());
		if (login == null) {
			return;
		}

	}

	/*
	 * Nov 28, 2012
	 */
	@Override
	public AuthedQueryOperationI loginId(String id) {
		//
		this.parameter(AuthedOperationI.PK_SESSION_ID, id);
		return this;
	}

	/*
	 * Nov 28, 2012
	 */
	@Override
	protected void executeInternal(NodeQueryResultI rst) throws Exception {

		super.executeInternal(rst);
	}

}
