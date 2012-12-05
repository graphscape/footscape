/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic.operations;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.IndicesExistsResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.index.query.MatchAllQueryBuilder;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.InterceptorI;
import com.fs.dataservice.api.core.ResultI;
import com.fs.dataservice.api.core.operations.DeleteAllOperationI;
import com.fs.dataservice.api.core.result.VoidResultI;
import com.fs.dataservice.api.core.support.OperationSupport;
import com.fs.dataservice.api.core.support.VoidResult;
import com.fs.dataservice.core.impl.elastic.ElasticClientI;

/**
 * @author wu
 * 
 */
public class DeleteAllOperationE extends
		OperationSupport<DeleteAllOperationI, VoidResultI> implements
		DeleteAllOperationI {

	private ElasticClientI elastic;

	/**
	 * @param ds
	 */
	public DeleteAllOperationE(DataServiceI ds) {
		super(new VoidResult(ds));
		this.elastic = (ElasticClientI) ds;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(VoidResultI rst) throws Exception {
		Client client = elastic.getClient();
		IndicesAdminClient iac = client.admin().indices();
		String idx = this.elastic.getIndex();
		IndicesExistsResponse ier = iac.prepareExists(idx).execute()
				.actionGet();
		if (ier.isExists()) {
			// this.dataService.refresh();//
			DeleteIndexRequestBuilder rb = iac.prepareDelete(this.elastic
					.getIndex());

			DeleteIndexResponse gr = rb.execute().actionGet();
			rst.set(gr.acknowledged());//
		} else {
			rst.set(true);
		}
	}

	protected void executeInternal_DEL(ResultI rst) throws Exception {
		Client client = elastic.getClient();

		DeleteByQueryRequestBuilder drb = client.prepareDeleteByQuery();
		// drb.setIndices(this.elastic.getIndex());// NOTE only delete all in
		// the
		// index.
		drb.setQuery(new MatchAllQueryBuilder());
		DeleteByQueryResponse gr = drb.execute().actionGet();

		rst.set(Boolean.TRUE);//
		this.dataService.refresh();//
	}

}
