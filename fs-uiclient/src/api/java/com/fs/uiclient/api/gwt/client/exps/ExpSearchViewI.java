/**
 * All right is from Author of the file,to be explained in comming days.
 * Feb 1, 2013
 */
package com.fs.uiclient.api.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;

/**
 * @author wu
 * 
 */
public interface ExpSearchViewI extends ViewI {

	public void addExpItem(ExpItemModel ei);

	public void reset();

	public void search();

	public UserExpModel getExpId(boolean b);

	public void setExpId(UserExpModel ue);

	public String getPhrase(boolean b);

	public int getSize();
	
	public void noMore();

}
