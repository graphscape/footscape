/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.api.presence;

import com.fs.gridservice.commons.api.EntityGdManagerI;
import com.fs.gridservice.commons.api.presence.data.PresenceGd;

/**
 * @author wuzhen
 * 
 */
public interface PresenceManagerI extends EntityGdManagerI<PresenceGd> {

	/**
	 * @param accId
	 */
	public void available(String accId, String termId);

	public void leave(String accId);

	public void leave(String accId, String termId);

	public void unAvailable(String accId, String termId);

}
