/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 1, 2012
 */
package com.fs.dataservice.core.impl.elastic.ext;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.support.BaseIndicesRequestBuilder;
import org.elasticsearch.client.IndicesAdminClient;

/**
 * @author wu
 * 
 */
public class GetIndexTemplateRequestBuilder extends
		BaseIndicesRequestBuilder<GetIndexTemplateRequest, GetIndexTemplateResponse> {

	/**
	 * @param client
	 * @param request
	 */
	public GetIndexTemplateRequestBuilder(IndicesAdminClient client) {
		super(client, new GetIndexTemplateRequest());
	}

	/*
	 * Nov 1, 2012
	 */
	@Override
	protected void doExecute(
			ActionListener<GetIndexTemplateResponse> actionlistener) {
		//

	}

}
