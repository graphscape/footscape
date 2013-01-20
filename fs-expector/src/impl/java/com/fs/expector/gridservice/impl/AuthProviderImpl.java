/**
 *  Dec 19, 2012
 */
package com.fs.expector.gridservice.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.AccountInfo;
import com.fs.gridservice.commons.api.data.SessionGd;
import com.fs.gridservice.commons.api.session.AuthProviderI;

/**
 * @author wuzhen
 * 
 */
public class AuthProviderImpl extends ConfigurableSupport implements AuthProviderI {

	protected DataServiceFactoryI factory;

	public AuthProviderImpl() {
	}

	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		this.factory = this.container.find(DataServiceFactoryI.class, true);
	}

	@Override
	public PropertiesI<Object> auth(PropertiesI<Object> credential) {
		//
		PropertiesI<Object> rt = new MapProperties<Object>();
		String type = (String) credential.getProperty("type");// anonymous/registered
		// boolean isSaved = credential.getProperty(Boolean.class, "isSaved",
		// Boolean.FALSE);//

		String accountId;
		if (type.equals("registered")) {// registered

			String email = (String) credential.getProperty("email", true);
			rt.setProperty("email", email);
			AccountInfo ai = this.factory.getDataService().getNewest(AccountInfo.class, AccountInfo.EMAIL,
					email, false);
			if (ai == null) {// not found account by email.

				return null;
			}
			accountId = ai.getAccountId();

		} else {
			accountId = (String) credential.getProperty("accountId", true);
		}

		Account acc = this.factory.getDataService().getNewestById(Account.class, accountId, false);

		if (acc == null) {// no this account or password
			return null;
		}

		String nick = acc.getNick();
		rt.setProperty("isAnonymous", acc.getIsAnonymous());
		rt.setProperty(SessionGd.ACCID, accountId);
		rt.setProperty("nick", nick);
		return rt;
	}

}
