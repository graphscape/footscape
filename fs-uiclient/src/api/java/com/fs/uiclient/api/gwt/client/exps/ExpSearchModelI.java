/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.exps;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public interface ExpSearchModelI extends ModelI {

	public static final String A_SEARCH = "search";

	public static final Location L_EXP_ID = Location.valueOf("expId");
	
	public static final Location L_PAGENUMBER = Location.valueOf("pageFrom");

	public static final Location L_PAGESIZE = Location.valueOf("pageSize");

	public String getKeyword();

	public String getExpId(boolean force);

	public ExpItemModel addExpItem(String id);
	
	public int getPageNumber();
	
	public int getPageSize();
	
	public void pageNumber(int pg);
	
	public boolean nextPage();
	
	public boolean previousPage();

}
