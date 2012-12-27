/**
 *  Dec 19, 2012
 */
package com.fs.gridservice.commons.impl.presence;

import com.fs.gridservice.commons.api.presence.PresenceManagerI;
import com.fs.gridservice.commons.api.presence.data.PresenceGd;
import com.fs.gridservice.commons.api.support.EntityGdManagerSupport;

/**
 * @author wuzhen
 * 
 */
public class PresenceManagerImpl extends EntityGdManagerSupport<PresenceGd>
		implements PresenceManagerI {

	/**
	 * @param name
	 * @param wcls
	 */
	public PresenceManagerImpl() {
		super("presence", PresenceGd.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.presence.PresenceManagerI#available(java
	 * .lang.String)
	 */
	@Override
	public void available(String accId, String terminalId) {
		PresenceGd p = this.getEntity(accId);
		p = new PresenceGd(accId);
		p.setStatus("available");
		p.setTerminalId(terminalId);
		this.addEntity(p);//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.presence.PresenceManagerI#leave(java.lang
	 * .String)
	 */
	@Override
	public void leave(String accId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.presence.PresenceManagerI#leave(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public void leave(String accId, String termId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.gridservice.commons.api.presence.PresenceManagerI#unAvailable(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void unAvailable(String accId, String termId) {
		PresenceGd p = this.getEntity(accId);
		String tId = p.getTerminalId();

		this.removeEntity(accId);// TODO multiple term

	}

}
