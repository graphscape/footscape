/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package com.fs.expector.dataservice.impl.elastic.support;

import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.ResultI;
import com.fs.expector.dataservice.api.operations.AuthedOperationI;
import com.fs.expector.dataservice.api.wrapper.Session;
import com.fs.expector.dataservice.impl.elastic.util.AuthedUtil;

/**
 * @author wu
 * 
 */
public class AuthedOperationSupport<O extends OperationI<O, T>, T extends ResultI<T, ?>>
		extends ElasticOperationSupport<O, T> implements AuthedOperationI<O, T> {

	/**
	 * @param ds
	 */
	public AuthedOperationSupport(T t) {
		super(t);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	protected void executeInternal(T rst) throws Exception {
		Session login = AuthedUtil.beforeAuthedOperation(this, this.dataService,
				rst);
		if (login == null) {
			return;
		}
		this.executeInternal(login, rst);
	}

	/**
	 * Oct 30, 2012
	 */
	protected void executeInternal(Session login, T rst) throws Exception {

	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public O sessionId(String id) {
		//
		this.parameter(AuthedOperationI.PK_SESSION_ID, id);
		return (O) this;
	}

}