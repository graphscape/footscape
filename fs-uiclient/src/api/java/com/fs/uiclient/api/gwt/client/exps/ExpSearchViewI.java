/**
 * All right is from Author of the file,to be explained in comming days.
 * Feb 1, 2013
 */
package com.fs.uiclient.api.gwt.client.exps;

import com.fs.uicommons.api.gwt.client.mvc.ViewI;

/**
 * @author wu
 * 
 */
public interface ExpSearchViewI extends ViewI {

	public void addExpItem(ExpItemModel ei);

	public void reset();

	public String getExpId(boolean b);

	public void setExpId(String expId);

	public String getPhrase(boolean b);

	/**
	 * @return
	 */
	public int getFirstResult();

	/**
	 * @return
	 */
	public int getMaxResult();

}
