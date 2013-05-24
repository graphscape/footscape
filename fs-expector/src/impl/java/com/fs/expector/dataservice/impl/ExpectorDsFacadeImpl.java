/**
 *  
 */
package com.fs.expector.dataservice.impl;

import java.util.Date;
import java.util.List;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.util.ImageUrl;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeI;
import com.fs.dataservice.api.core.operations.NodeCountOperationI;
import com.fs.dataservice.api.core.operations.NodeSearchOperationI;
import com.fs.dataservice.api.core.result.NodeSearchResultI;
import com.fs.dataservice.api.core.util.NodeWrapperUtil;
import com.fs.expector.dataservice.api.ExpectorDsFacadeI;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.AccountInfo;
import com.fs.expector.dataservice.api.wrapper.Acknowledge;
import com.fs.expector.dataservice.api.wrapper.Connection;
import com.fs.expector.dataservice.api.wrapper.ExpMessage;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper.ImageContent;
import com.fs.expector.dataservice.api.wrapper.Profile;

/**
 * @author wu
 * 
 */
public class ExpectorDsFacadeImpl extends ConfigurableSupport implements ExpectorDsFacadeI {

	public static int maxSizeOfConnectQuery = 10000;// TODO remove this

	public static int maxAllowedConnectPerExp = 100;

	public static int maxLimitOfOpenExpectation = 200;

	private ImageUrl defaultUserIconDataUrl;

	private ImageUrl defaultUserIconDataUrl_male;

	private ImageUrl defaultUserIconDataUrl_female;

	private DataServiceI dataService;

	@Override
	public void configure(Configuration cfg) {
		// TODO Auto-generated method stub
		super.configure(cfg);
		this.defaultUserIconDataUrl = ImageUrl.parse(this.config.getProperty("defaultUserIconDataUrl", true),
				true);
		this.defaultUserIconDataUrl_male = ImageUrl.parse(
				this.config.getProperty("defaultUserIconDataUrl_male", true), true);
		this.defaultUserIconDataUrl_female = ImageUrl.parse(
				this.config.getProperty("defaultUserIconDataUrl_female", true), true);

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
		NodeSearchOperationI<Connection> qo = this.dataService.prepareNodeSearch(Connection.class);

		qo.first(0);
		qo.maxSize(this.maxSizeOfConnectQuery);// TODO application determine
												// this?
		qo.propertyEq(Connection.EXP_ID1, expId);
		NodeSearchResultI<Connection> rst = qo.execute().getResult().assertNoError();

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
	public ImageUrl getIconByAccountId(String accId1) {
		//
		Profile p = this.dataService.getNewest(Profile.class, Profile.ACCOUNTID, accId1, false);
		if (p == null) {
			return this.defaultUserIconDataUrl;
		}

		ImageUrl rt = ImageUrl.parse(p.getIcon(), true);
		if (!rt.isNone()) {
			return rt;
		}
		String gender = p.getGender();
		if ("male".equals(gender)) {
			return this.defaultUserIconDataUrl_male;
		} else if ("female".equals(gender)) {
			return this.defaultUserIconDataUrl_female;
		} else {
			return this.defaultUserIconDataUrl;
		}
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
			String expId = (String) pts.getProperty(NodeI.PK_ID, true);
			String accId = (String) pts.getProperty("accountId");
			// account must be exist
			Account acc = this.dataService.getNewestById(Account.class, accId, true);
			pts.setProperty("nick", acc.getNick());
			{// total connections
				long count = this.getExpConnectCount(expId);
				pts.setProperty("connectionCount", count);

			}
			{// total messages
				long count = this.getExpMessageCount(expId);
				pts.setProperty("messageCount", count);

			}
			{// total new messages,not acknowledged
				long count = this.getNewExpMessageCount(expId);
				pts.setProperty("newMessageCount", count);
			}
			{// profile may not exist.

				
				ImageUrl icon = this.getIconByAccountId(accId);
				
				pts.setProperty("userIcon", icon.toString());//
			}
			//
			this.processExpIcon(pts);
		}
		return rt;
	}

	/**
	 * @param expId
	 */
	private long getExpConnectCount(String expId) {
		NodeCountOperationI<Connection> nco = this.dataService.prepareNodeCount(Connection.class);
		nco.propertyEq(Connection.EXP_ID1, expId);
		return nco.execute().getResult().assertNoError().get(true);

	}

	/**
	 * @param expId
	 */
	private long getExpMessageCount(String expId) {
		NodeCountOperationI<ExpMessage> nco = this.dataService.prepareNodeCount(ExpMessage.class);
		nco.propertyEq(ExpMessage.EXP_ID2, expId);
		return nco.execute().getResult().assertNoError().get(true);

	}

	/**
	 * @param expId
	 */
	private long getNewExpMessageCount(String expId) {
		Acknowledge ack = this.getExpMessageAcknowledge(expId);
		NodeCountOperationI<ExpMessage> nco = this.dataService.prepareNodeCount(ExpMessage.class);
		nco.propertyEq(ExpMessage.EXP_ID2, expId);
		Date timestamp1 = ack == null ? null : ack.getAcknowledged();
		if (timestamp1 != null) {
			nco.propertyGt(NodeI.PK_TIMESTAMP, timestamp1, true);
		}

		return nco.execute().getResult().assertNoError().get(true);

	}

	public Acknowledge getExpMessageAcknowledge(String expId) {
		Acknowledge rt = this.dataService.getNewest(Acknowledge.class, new String[] {
				Acknowledge.PK_CATEGORY, Acknowledge.PK_OBJECT_ID }, new Object[] { Acknowledge.CAT_EXPM,
				expId }, false);

		return rt;
	}

	/*
	 * Apr 25, 2013
	 */
	@Override
	public void acknowledgeExpMessage(String accId, String expId, Date ts) {
		//

		Acknowledge ack = new Acknowledge().forCreate(this.dataService);
		ack.setAccountId(accId);
		ack.setCategory(Acknowledge.CAT_EXPM);
		ack.setObjectId(expId);
		ack.setAcknowledged(ts);
		ack.save(true);
		// TODO delete by query? or in a common place for all node types.

	}

	/*
	 * May 3, 2013
	 */
	@Override
	public int getExpectationOpenCount(String aid) {
		//
		NodeCountOperationI<Expectation> nco = this.dataService.prepareNodeCount(Expectation.class);
		nco.propertyEq(Expectation.STATUS, Expectation.ST_OPEN);

		long rt = nco.execute().getResult().assertNoError().get(true);
		return (int) rt;
	}

	/*
	 * May 3, 2013
	 */
	@Override
	public int getMaxLimitOfExpectationOpenCount() {
		//
		return maxLimitOfOpenExpectation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.expector.dataservice.api.ExpectorDsFacadeI#saveImage(java.lang
	 * .String)
	 */
	@Override
	public ImageUrl saveImage(ImageUrl diu) {

		if (!"data".equals(diu.getProtocol())) {

			return diu;
		}
		// only data url need to save.
		ImageContent ic = new ImageContent().forCreate(this.dataService);
		ic.setProtocol(diu.getProtocol());
		ic.setData(diu.getData());
		ic.setEncode(diu.getEncode());
		ic.setFormat(diu.getFormat());
		ic.save(false);
		ImageUrl rt = new ImageUrl(IMG_PROTOCOL_IID, "none", "none", ic.getId());

		return rt;
	}

	/*
	 * May 24, 2013
	 */
	@Override
	public ImageUrl getDefaultUserIconImageUrl() {
		return this.defaultUserIconDataUrl;
	}

}
