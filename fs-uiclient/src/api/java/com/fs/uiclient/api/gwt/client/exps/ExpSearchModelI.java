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


	public static final Location L_PAGENUMBER = Location.valueOf("pageFrom");

	public static final Location L_PAGESIZE = Location.valueOf("pageSize");

	public String getKeyword();

	public String getExpId(boolean force);

	public ExpItemModel addExpItem(String id);

	public List<ExpItemModel> getExpItemList();

	public int getPageNumber();

	public int getPageSize();

	public void pageNumber(int pg);

	public boolean nextPage();

	public boolean previousPage();

	public void setExpId(String expId);//for search parameter

	public String getExpId();//for search parameter

}
