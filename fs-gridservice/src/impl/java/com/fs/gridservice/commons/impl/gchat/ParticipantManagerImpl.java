/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.impl.gchat;

import com.fs.commons.api.ActiveContext;
import com.fs.gridservice.commons.api.gchat.ParticipantManagerI;
import com.fs.gridservice.commons.api.gchat.data.ParticipantGd;
import com.fs.gridservice.commons.api.support.EntityGdManagerSupport;

/**
 * @author wuzhen
 * 
 */
public class ParticipantManagerImpl extends EntityGdManagerSupport<ParticipantGd> implements
		ParticipantManagerI {

	/**
	 * @param name
	 * @param wcls
	 */
	public ParticipantManagerImpl() {
		super("participant", ParticipantGd.class);
	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
	}

}
