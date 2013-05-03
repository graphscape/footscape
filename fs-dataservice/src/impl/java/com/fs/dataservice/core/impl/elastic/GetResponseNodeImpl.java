/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic;

import java.util.Map;

import org.elasticsearch.action.get.GetResponse;

import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.meta.NodeMeta;
import com.fs.dataservice.api.core.support.NodeSupport;

/**
 * @author wu
 * 
 */
public class GetResponseNodeImpl extends NodeSupport {

	protected GetResponse response;

	/**
	 * @param type
	 * @param uid
	 */
	public GetResponseNodeImpl(NodeType ntype, DataServiceI ds, GetResponse gr) {
		super(ntype, gr.getId());
		this.response = gr;

		NodeMeta nc = ds.getConfigurations().getNodeConfig(ntype, true);
		Map<String, Object> old = this.response.sourceAsMap();
		SearchHitNode.convertPropertiesFromStore(old, nc, this);

	}

}
