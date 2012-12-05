/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.uexp;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public interface UserExpListModelI extends ModelI {

	public static final String A_CREATE = "create";// link to new exp edit view.

	public static final String A_GET = "get";// hidden,when user click the
												// detail icon,expend the detail
												// in the list item.

	public static final String A_SELECT = "open";// open the default view of the
													// exp,only when the exp is
													// coppered?.

	public static final String A_REFRESH = "refresh";// get the list of exp for
														// this
	// user.
	// when new exp is created,this value is set and the control will get the
	// content from server side.

	public static final Location L_EXP_ID_GET_REQUIRED = Location
			.valueOf("expIdGetRequired");//
	// when one selected.
	/**
	 * @see ExpSearchControl.onUserExpSelected()
	 * @see SearchAP of ExpSearchControl
	 */
	public static final Location L_SELECTED_EXP_ID = Location
			.valueOf("selectedExpId");

	public UserExpModel getOrAddUserExp(String id);

	public UserExpModel getSelected(boolean force);

	public UserExpModel getUserExp(String id, boolean force);// id /name?

	public void select(String expId);// the current user exp,use select this
										// one,for search or open it.

}
