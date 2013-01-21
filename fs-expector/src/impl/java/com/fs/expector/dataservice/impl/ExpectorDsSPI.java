/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.expector.dataservice.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.dataservice.api.core.DataServiceFactoryI;
import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.AccountInfo;
import com.fs.expector.dataservice.api.wrapper.Activity;
import com.fs.expector.dataservice.api.wrapper.CooperRequest;
import com.fs.expector.dataservice.api.wrapper.Expectation;
import com.fs.expector.dataservice.api.wrapper.Profile;
import com.fs.expector.dataservice.api.wrapper.Session;
import com.fs.expector.dataservice.api.wrapper.SignupConfirm;
import com.fs.expector.dataservice.api.wrapper.SignupRequest;
import com.fs.expector.dataservice.api.wrapper.User;
import com.fs.expector.dataservice.api.wrapper2.ExpActivity;
import com.fs.expector.dataservice.api.wrapper2.UserActivity;
import com.fs.expector.dataservice.api.wrapper2.UserSnapshot;

/**
 * @author wu
 * 
 */
public class ExpectorDsSPI extends SPISupport {

	/**
	 * @param id
	 */
	public ExpectorDsSPI(String id) {
		super(id);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public void doActive(ActiveContext ac) {

		DataServiceFactoryI dsf = ac.getContainer().find(DataServiceFactoryI.class, true);
		// Configurations
		DataSchema cfs = dsf.getSchema();

		Account.config(cfs);
		SignupRequest.config(cfs);
		SignupConfirm.config(cfs);
		Session.config(cfs);
		Activity.config(cfs);
		CooperRequest.config(cfs);
		Expectation.config(cfs);
		User.config(cfs);
		ExpActivity.config(cfs);
		UserActivity.config(cfs);
		Profile.config(cfs);
		AccountInfo.config(cfs);//
		UserSnapshot.config(cfs);

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public void doDeactive(ActiveContext ac) {
		//

	}

}
