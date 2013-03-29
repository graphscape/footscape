/**
 *  
 */
package com.fs.expector.dataservice.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.expector.dataservice.api.ExpectorDsFacadeI;
import com.fs.expector.dataservice.api.wrapper.Connection;

/**
 * @author wu
 * 
 */
public class ExpectorDsFacadeImpl extends ConfigurableSupport implements ExpectorDsFacadeI {

	public static int maxSizeOfConnectQuery = 10000;// TODO remove this

	public static int maxAllowedConnectPerExp = 100;

	private DataServiceI dataService;

	/* */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		DataServiceFactoryI dsf = top.find(DataServiceFactoryI.class, true);
		this.dataService = dsf.getDataService();//

	}

	@Override
	public int getConnectedExpCount(String expId) {
		NodeQueryOperationI<Connection> qo = this.dataService.prepareNodeQuery(Connection.class);

		qo.first(0);
		qo.maxSize(this.maxSizeOfConnectQuery);// TODO application determine
												// this?
		qo.propertyEq(Connection.EXP_ID1, expId);
		NodeQueryResultI<Connection> rst = qo.execute().getResult().assertNoError();

		return rst.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.expector.dataservice.api.ExpectorDsFacadeI#getExceedConnectionOfExp
	 * (java.lang.String, boolean)
	 */
	@Override
	public int getOverflowConnectedExpCount(String expId) {
		int c = this.getConnectedExpCount(expId);
		return c - this.maxAllowedConnectPerExp;
	}

}
