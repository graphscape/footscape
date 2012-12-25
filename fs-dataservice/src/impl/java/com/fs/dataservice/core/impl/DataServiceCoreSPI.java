/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.conf.NodeConfigurations;
import com.fs.dataservice.api.core.operations.DeleteAllOperationI;
import com.fs.dataservice.api.core.operations.DumpOperationI;
import com.fs.dataservice.api.core.operations.NodeCreateOperationI;
import com.fs.dataservice.api.core.operations.NodeGetOperationI;
import com.fs.dataservice.api.core.operations.NodeQueryOperationI;
import com.fs.dataservice.api.core.operations.RefreshOperationI;
import com.fs.dataservice.api.expapp.wrapper.Account;
import com.fs.dataservice.api.expapp.wrapper.Activity;
import com.fs.dataservice.api.expapp.wrapper.CooperConfirm;
import com.fs.dataservice.api.expapp.wrapper.CooperRequest;
import com.fs.dataservice.api.expapp.wrapper.Expectation;
import com.fs.dataservice.api.expapp.wrapper.Session;
import com.fs.dataservice.api.expapp.wrapper.SignupConfirm;
import com.fs.dataservice.api.expapp.wrapper.SignupRequest;
import com.fs.dataservice.api.expapp.wrapper.User;
import com.fs.dataservice.api.expapp.wrapper2.ExpActivity;
import com.fs.dataservice.api.expapp.wrapper2.UserActivity;
import com.fs.dataservice.core.impl.elastic.ElasticDataServiceImpl;
import com.fs.dataservice.core.impl.elastic.operations.DeleteAllOperationE;
import com.fs.dataservice.core.impl.elastic.operations.DumpOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeCreateOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeGetOperationE;
import com.fs.dataservice.core.impl.elastic.operations.NodeQueryOperationE;
import com.fs.dataservice.core.impl.elastic.operations.RefreshOperationE;

/**
 * @author wu
 * 
 */
public class DataServiceCoreSPI extends SPISupport {

	/**
	 * @param id
	 */
	public DataServiceCoreSPI(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public void doActive(ActiveContext ac) {
		ElasticDataServiceImpl ds = new ElasticDataServiceImpl();
		ac.active("DATA_SERVICE", ds);
		this.activeOperations(ac);
		this.activeNodeConfigs(ac);
	}

	/**
	 * Nov 2, 2012
	 */
	private void activeNodeConfigs(ActiveContext ac) {
		DataServiceI ds = ac.getContainer().find(DataServiceI.class, true);
		

	}

	protected void activeOperations(ActiveContext ac) {
		DataServiceI ds = ac.getContainer().find(DataServiceI.class, true);
		ds.registerOperation("core.nodeget", NodeGetOperationI.class,
				NodeGetOperationE.class);
		ds.registerOperation("core.nodecreate", NodeCreateOperationI.class,
				NodeCreateOperationE.class);
		ds.registerOperation("core.nodequery", NodeQueryOperationI.class,
				NodeQueryOperationE.class);
		ds.registerOperation("core.deleteall", DeleteAllOperationI.class,
				DeleteAllOperationE.class);
		ds.registerOperation("core.dump", DumpOperationI.class,
				DumpOperationE.class);
		ds.registerOperation("core.refresh", RefreshOperationI.class,
				RefreshOperationE.class);

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public void doDeactive(ActiveContext ac) {
		//

	}

}
