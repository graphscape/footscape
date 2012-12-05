/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic;

import org.elasticsearch.action.get.GetResponse;

import com.fs.dataservice.api.core.NodeType;
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
	public GetResponseNodeImpl(GetResponse gr) {
		super(NodeType.valueOf(gr.getType()), gr.getId());
		this.response = gr;
		this.setProperties(this.response.getSource());

	}

}
