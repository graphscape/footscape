/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.exps;

import java.util.List;

import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;

/**
 * @author wu
 * 
 */
public interface ExpSearchModelI {

	public UserExpModel getExpId(boolean force);

	public String getPhrase(boolean force);

	public void setPhrase(String phrase);

	public ExpItemModel addExpItem(ExpItemModel ei);

	public List<ExpItemModel> getExpItemList();

	public void setExpId(UserExpModel expId);// for search parameter

	public UserExpModel getExpId();// for search parameter

	public void reset();

}
