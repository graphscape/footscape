/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 7, 2012
 */
package com.fs.uiclient.api.gwt.client.usshot;

import java.util.List;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public interface UserSnapshotModelI extends ModelI {

	public static String A_SNAPSHOT = "snapshot";

	public void setActivityIdList(List<String> ls);

	public void setExpIdList(List<String> ls);

	public void setCooperRequestIdList(List<String> ls);

	public List<String> getActivityIdList();

	public List<String> getExpIdList();

	public List<String> getCooperRequestIdList();

}
