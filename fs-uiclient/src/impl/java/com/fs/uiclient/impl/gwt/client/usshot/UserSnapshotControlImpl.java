/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 7, 2012
 */
package com.fs.uiclient.impl.gwt.client.usshot;

import com.fs.uiclient.api.gwt.client.usshot.UserSnapshotControlI;
import com.fs.uiclient.api.gwt.client.usshot.UserSnapshotModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;

/**
 * @author wu
 * 
 */
public class UserSnapshotControlImpl extends ControlSupport implements
		UserSnapshotControlI {

	/**
	 * @param name
	 */
	public UserSnapshotControlImpl(String name) {
		super(name);
		this.addActionProcessor(UserSnapshotModelI.A_SNAPSHOT, new SnapshotAP());

	}

}
