/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 7, 2012
 */
package com.fs.uiclient.impl.gwt.client.usshot;

import java.util.List;

import com.fs.uiclient.api.gwt.client.usshot.UserSnapshotModelI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class UserSnapshotModelImpl extends ModelSupport implements
		UserSnapshotModelI {

	protected List<String> activityIdList;

	protected List<String> expIdList;

	protected List<String> cooperRequestIdList;

	/**
	 * @param name
	 */
	public UserSnapshotModelImpl(String name) {
		super(name);
		ControlUtil.addAction(this, UserSnapshotModelI.A_SNAPSHOT);

	}

	/*
	 * Dec 7, 2012
	 */
	@Override
	public List<String> getActivityIdList() {
		//
		return this.activityIdList;
	}

	/*
	 * Dec 7, 2012
	 */
	@Override
	public List<String> getExpIdList() {
		//
		return this.expIdList;
	}

	/*
	 * Dec 7, 2012
	 */
	@Override
	public List<String> getCooperRequestIdList() {
		//
		return this.cooperRequestIdList;
	}

	/*
	 * Dec 7, 2012
	 */
	@Override
	public void setActivityIdList(List<String> ls) {
		//
		this.activityIdList = ls;
	}

	/*
	 * Dec 7, 2012
	 */
	@Override
	public void setExpIdList(List<String> ls) {
		//
		this.expIdList = ls;
	}

	/*
	 * Dec 7, 2012
	 */
	@Override
	public void setCooperRequestIdList(List<String> ls) {
		//
		this.cooperRequestIdList = ls;
	}

}
