/**
 * All right is from Author of the file,to be explained in comming days.
 * Feb 1, 2013
 */
package com.fs.uiclient.api.gwt.client.exps;

import java.util.List;

import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;

/**
 * @author wu
 * 
 */
public interface UserExpListViewI extends ViewI {

	public void incomingCr(String expId, String crId);

	@Deprecated //REMOVE
	public void select(String expId);

	public void update(UserExpModel uem);

	public void delete(String expId);

	public List<UserExpModel> getUserExpList();

	public UserExpModel getUserExp(String id, boolean b);

	public void addUserExp(UserExpModel uem);

	public void expClosed(String expId);
	
	public String getStatus();

}
