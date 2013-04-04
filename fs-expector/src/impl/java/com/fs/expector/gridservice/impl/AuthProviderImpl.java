/**
 *  Dec 19, 2012
 */
package com.fs.expector.gridservice.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.ObjectUtil;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.api.value.PropertiesI;
import com.fs.dataservice.api.core.DataServiceFactoryI;
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
	public void auth(PropertiesI<Object> credential,ErrorInfos eis,PropertiesI<Object> rt) {
		//
		String type = (String) credential.getProperty("type", true);// anonymous/registered

		String accountId = null;
		String password = (String) credential.getProperty("password", false);
		if (Account.TYPE_ANONYMOUS.equals(type)) {
			accountId = (String) credential.getProperty("accountId", true);
		} else if (Account.TYPE_REGISTERED.equals(type)) {
			String email = (String) credential.getProperty("email", true);
			rt.setProperty("email", email);
			AccountInfo ai = this.factory.getDataService().getNewest(AccountInfo.class, AccountInfo.EMAIL,
					email, false);
			if (ai == null) {// not found account by email.
				eis.addError("/error/login/failure");
				return;
			}
			accountId = ai.getAccountId();
		} else if (Account.TYPE_EXTERNAL.equals(type)) {
			String source = (String) credential.getProperty("source", true);
			String xuid = (String) credential.getProperty("userId", true);
			String nick = (String) credential.getProperty("nick");

			// already external authed
			String aid = source + "-" + xuid;
			Account acc = this.factory.getDataService().getNewestById(Account.class, aid, false);
			password = "todo";
			if (acc == null) {//
				// create a account for this external user
				acc = new Account().forCreate(this.factory.getDataService());
				acc.setId(aid);
				acc.setType(Account.TYPE_EXTERNAL);// external means the user
													// should login by
				// external system.
				acc.setNick(nick);
				acc.setPassword(password);
				acc.save(true);
			}
			accountId = aid;
		}

		Account acc = this.factory.getDataService().getNewestById(Account.class, accountId, false);

		if (acc == null) {// no this account or password,auth failed,
							// see:TerminalAuthHandler in grid service.
			eis.addError("/error/login/failure");
			return ;
		}

		if (!ObjectUtil.nullSafeEquals(acc.getPassword(), password)) {// password
																		// error
			eis.addError("/error/login/failure");
			return;
		}

		String nick = acc.getNick();
		rt.setProperty("isAnonymous", "anonymous".equals(acc.getType()));
		rt.setProperty(SessionGd.ACCID, accountId);
		rt.setProperty("nick", nick);
	}

}
