/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 1, 2012
 */
package com.fs.dataservice.core.impl.elastic.ext;

import org.elasticsearch.action.admin.indices.IndicesAction;
import org.elasticsearch.client.IndicesAdminClient;

/**
 * @author wu
 * 
 */
public class GetIndexTemplateAction
		extends
		IndicesAction<GetIndexTemplateRequest, GetIndexTemplateResponse, GetIndexTemplateRequestBuilder> {

	public static final GetIndexTemplateAction INSTANCE = new GetIndexTemplateAction();
	public static final String NAME = "indices/template/get";

	private GetIndexTemplateAction() {
		super(NAME);
	}

	@Override
	public GetIndexTemplateResponse newResponse() {
		return new GetIndexTemplateResponse();
	}

	@Override
	public GetIndexTemplateRequestBuilder newRequestBuilder(
			IndicesAdminClient client) {
		return new GetIndexTemplateRequestBuilder(client);
	}
}
