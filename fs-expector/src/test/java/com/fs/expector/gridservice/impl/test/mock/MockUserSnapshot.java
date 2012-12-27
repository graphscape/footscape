/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
 */
package com.fs.expector.gridservice.impl.test.mock;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wu
 * 
 */
public class MockUserSnapshot {

	private List<String> activityIdList = new ArrayList<String>();
	private List<String> expIdList = new ArrayList<String>();
	private List<String> cooperRequestIdList = new ArrayList<String>();
	private String accountId;

	public MockUserSnapshot(String accId) {
		this.accountId = accId;
	}

	public List<String> getActivityIdList() {
		return this.activityIdList;
	}

	public List<String> getExpIdList() {
		return this.expIdList;
	}

	public List<String> getCooperRequestIdList() {
		return this.cooperRequestIdList;
	}

	public String getAccountId() {
		return this.accountId;
	}

	public void addActivityIdList(List<String> activityIds) {
		this.activityIdList.addAll(activityIds);
	}

	public void addExpIdList(List<String> eL) {
		this.expIdList.addAll(eL);
	}

	public void addCooperRequestIdList(List<String> crL) {
		this.cooperRequestIdList.addAll(crL);
	}

}
