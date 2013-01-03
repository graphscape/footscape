/**
 *  Dec 25, 2012
 */
package com.fs.gridservice.commons.api.client;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.EntityGdManagerI;
import com.fs.gridservice.commons.api.data.ClientGd;

/**
 * @author wuzhen
 * 
 */
public interface ClientManagerI extends EntityGdManagerI<ClientGd> {

	public ClientGd createClient(String tid);

	public ClientGd createClient(String tid, PropertiesI<Object> pts);

	public void bindingSession(String cid, String sid);
	
	public void unBindingSession(String cid);

}
