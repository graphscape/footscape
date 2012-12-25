/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.expapp.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.expapp.operations.CooperConfirmOperationI;
import com.fs.dataservice.api.expapp.operations.CooperRequestOperationI;
import com.fs.dataservice.api.expapp.operations.ExpCreateOperationI;
import com.fs.dataservice.api.expapp.operations.ExpSearchOperationI;
import com.fs.dataservice.api.expapp.operations.UserSnapshotOperationI;
import com.fs.dataservice.api.expapp.wrapper.Account;
import com.fs.dataservice.api.expapp.wrapper.AccountInfo;
import com.fs.dataservice.api.expapp.wrapper.Activity;
import com.fs.dataservice.api.expapp.wrapper.CooperConfirm;
import com.fs.dataservice.api.expapp.wrapper.CooperRequest;
import com.fs.dataservice.api.expapp.wrapper.Expectation;
import com.fs.dataservice.api.expapp.wrapper.Profile;
import com.fs.dataservice.api.expapp.wrapper.Session;
import com.fs.dataservice.api.expapp.wrapper.SignupConfirm;
import com.fs.dataservice.api.expapp.wrapper.SignupRequest;
import com.fs.dataservice.api.expapp.wrapper.User;
import com.fs.dataservice.api.expapp.wrapper2.ExpActivity;
import com.fs.dataservice.api.expapp.wrapper2.UserActivity;
import com.fs.dataservice.api.expapp.wrapper2.UserSnapshot;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;
import com.fs.dataservice.expapp.impl.elastic.CooperConfirmOperationE;
import com.fs.dataservice.expapp.impl.elastic.CooperRequestOperationE;
import com.fs.dataservice.expapp.impl.elastic.ExpCreateOperationE;
import com.fs.dataservice.expapp.impl.elastic.RandomExpSearchOperationE;
import com.fs.dataservice.expapp.impl.elastic.UserSnapshotOperationE;

/**
 * @author wu
 * 
 */
public class DataServiceExpAppSPI extends SPISupport {

	/**
	 * @param id
	 */
	public DataServiceExpAppSPI(String id) {
		super(id);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public void doActive(ActiveContext ac) {
		DataServiceI ds = ac.getContainer().find(DataServiceI.class, true);
		ds.registerOperation("expapp.cooperconfirm",
				CooperConfirmOperationI.class, CooperConfirmOperationE.class);
		ds.registerOperation("expapp.expcreate", ExpCreateOperationI.class,
				ExpCreateOperationE.class);
		ds.registerOperation("expapp.cooperrequest",
				CooperRequestOperationI.class, CooperRequestOperationE.class);
		ds.registerOperation("expapp.expsearch1", ExpSearchOperationI.class,
				RandomExpSearchOperationE.class);
		ds.registerOperation("expapp.usersnap", UserSnapshotOperationI.class,
				UserSnapshotOperationE.class);

		//

		ElasticClientI ec = ac.getContainer().find(ElasticClientI.class, true);

		// Configurations
		NodeConfigurations cfs = ds.getConfigurations();

		Account.config(cfs);
		SignupRequest.config(cfs);
		SignupConfirm.config(cfs);
		Session.config(cfs);
		Activity.config(cfs);
		CooperRequest.config(cfs);
		CooperConfirm.config(cfs);
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
