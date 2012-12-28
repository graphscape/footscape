/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.expector.dataservice.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;
import com.fs.expector.dataservice.api.operations.CooperConfirmOperationI;
import com.fs.expector.dataservice.api.operations.CooperRequestOperationI;
import com.fs.expector.dataservice.api.operations.ExpCreateOperationI;
import com.fs.expector.dataservice.api.operations.ExpSearchOperationI;
import com.fs.expector.dataservice.api.operations.UserSnapshotOperationI;
import com.fs.expector.dataservice.api.wrapper.Account;
import com.fs.expector.dataservice.api.wrapper.AccountInfo;
import com.fs.expector.dataservice.api.wrapper.Activity;
import com.fs.expector.dataservice.api.wrapper.CooperConfirm;
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
import com.fs.expector.dataservice.impl.elastic.CooperConfirmOperationE;
import com.fs.expector.dataservice.impl.elastic.CooperRequestOperationE;
import com.fs.expector.dataservice.impl.elastic.ExpCreateOperationE;
import com.fs.expector.dataservice.impl.elastic.RandomExpSearchOperationE;
import com.fs.expector.dataservice.impl.elastic.UserSnapshotOperationE;

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
