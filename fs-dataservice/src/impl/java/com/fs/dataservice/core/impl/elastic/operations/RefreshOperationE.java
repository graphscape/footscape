/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic.operations;

import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.operations.RefreshOperationI;
import com.fs.dataservice.api.core.result.VoidResultI;
import com.fs.dataservice.api.core.support.VoidResult;
import com.fs.dataservice.core.impl.elastic.support.ElasticOperationSupport;

/**
 * @author wu
 * 
 */
public class RefreshOperationE extends
		ElasticOperationSupport<RefreshOperationI, VoidResultI> implements
		RefreshOperationI {

	/**
	 * @param ds
	 */
	public RefreshOperationE(DataServiceI ds) {
		super(new VoidResult(ds));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.dataservice.api.core.support.OperationSupport#executeInternal(
	 * com.fs.dataservice.api.core.ResultI)
	 */
	@Override
	protected void executeInternal(VoidResultI rst) throws Exception {
		Client client = elastic.getClient();

		AdminClient aclient = client.admin();
		aclient.indices().prepareRefresh(this.elastic.getIndex()).execute()
				.actionGet();
		rst.set(Boolean.TRUE);//
	}

}
