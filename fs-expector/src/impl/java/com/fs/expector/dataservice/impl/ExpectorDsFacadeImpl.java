/**
 *  
 */
package com.fs.expector.dataservice.impl;

import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.result.NodeQueryResultI;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.expector.dataservice.api.ExpectorDsFacadeI;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.AccountInfo;
import com.fs.expector.dataservice.api.wrapper.Connection;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper.Profile;

/**
 * @author wu
 * 
 */
public class ExpectorDsFacadeImpl extends ConfigurableSupport implements ExpectorDsFacadeI {

	public static int maxSizeOfConnectQuery = 10000;// TODO remove this

	public static int maxAllowedConnectPerExp = 100;

	private String defaultUserIconDataUrl;

	private DataServiceI dataService;

	@Override
	public void configure(Configuration cfg) {
		// TODO Auto-generated method stub
		super.configure(cfg);
		this.defaultUserIconDataUrl = this.config.getProperty("defaultUserIconDataUrl");
	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		DataServiceFactoryI dsf = top.find(DataServiceFactoryI.class, true);
		this.dataService = dsf.getDataService();//
	}

	@Override
	public void processExpIcon(PropertiesI<Object> pts) {
		String expIcon = (String) pts.getProperty("icon");
		if (expIcon.equalsIgnoreCase("n/a")) {
			pts.setProperty("icon", null);
		}
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

	/*
	 * Mar 29, 2013
	 */
	@Override
	public String getIconByAccountId(String accId1) {
		//
		Profile p = this.dataService.getNewest(Profile.class, Profile.ACCOUNTID, accId1, false);
		if (p == null) {
			return null;
		}
		return p.getIcon();
	}

	/*
	 * Mar 31, 2013
	 */
	@Override
	public Account updatePassword(String aid, String pass) {
		//
		Account a = this.dataService.getNewestById(Account.class, aid, false);

		if (a == null) {

			return null;
		}
		Account rt = new Account().forCreate(this.dataService);
		rt.getTarget().setProperties(a.getUserProperties());
		rt.setId(a.getId());
		rt.setPassword(pass);
		rt.save(true);
		return rt;
	}

	public Account getAccountByEmail(String email) {

		AccountInfo ai = this.dataService.getNewest(AccountInfo.class, AccountInfo.EMAIL, email, false);
		if (ai == null) {
			return null;
		}
		String aid = ai.getAccountId();
		Account rt = this.dataService.getNewestById(Account.class, aid, false);

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.expector.dataservice.api.ExpectorDsFacadeI#convertToClientFormat
	 * (java.util.List)
	 */
	@Override
	public List<PropertiesI<Object>> convertToClientFormat(List<Expectation> list) {
		// convert
		List<PropertiesI<Object>> rt = NodeWrapperUtil.convert(list);
		for (PropertiesI<Object> pts : rt) {
			String accId = (String) pts.getProperty("accountId");
			// account must be exist
			Account acc = this.dataService.getNewestById(Account.class, accId, true);
			pts.setProperty("nick", acc.getNick());

			// profile may not exist.

			Profile pf = this.dataService.getNewest(Profile.class, Profile.ACCOUNTID, accId, false);
			String icon = pf == null ? null : pf.getIcon();
			if (icon == null) {
				icon = this.defaultUserIconDataUrl;
			}
			pts.setProperty("userIcon", icon);//
			//
			this.processExpIcon(pts);
		}
		return rt;
	}

	/* (non-Javadoc)
	 * @see com.fs.expector.dataservice.api.ExpectorDsFacadeI#getDefaultUserIconDataUrl()
	 */
	@Override
	public String getDefaultUserIconDataUrl() {
		return this.defaultUserIconDataUrl;
	}

}
