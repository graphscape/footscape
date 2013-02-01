/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.exps;

import java.util.List;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public interface ExpSearchModelI extends ModelI {

	public static final Location L_FIRSTRESULT = Location.valueOf("firstResult");

	public static final Location L_MAXRESULT = Location.valueOf("maxResult");

	public String getExpId(boolean force);

	public String getPhrase(boolean force);
	
	public void setPhrase(String phrase);

	public void addExpItem(ExpItemModel ei);

	public List<ExpItemModel> getExpItemList();

	public int getFirstResult();

	public int getMaxResult();

	public boolean nextPage();

	public boolean previousPage();

	public void setExpId(String expId);// for search parameter

	public String getExpId();// for search parameter

}
