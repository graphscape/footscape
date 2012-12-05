/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.core.impl.elastic;

import org.elasticsearch.client.Client;

import com.fs.commons.api.callback.CallbackI;

/**
 * @author wu
 * 
 */
public interface ElasticClientI {

	public String getIndex();

	public Client getClient();

	public <T> T executeInClient(CallbackI<Client, T> cb);
	
	public String getTimestampString();

}
