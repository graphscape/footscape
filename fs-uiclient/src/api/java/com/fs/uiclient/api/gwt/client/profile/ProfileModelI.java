/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 16, 2012
 */
package com.fs.uiclient.api.gwt.client.profile;

import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wuzhen
 * 
 */
public interface ProfileModelI extends ModelI {

	public static final String F_PROFILE = FormsModel.DEFAULT_FORM;


	public static final Location L_AGE = Location.valueOf("age");

	public static final Location L_GENDER = Location.valueOf("gender");

	public static final Location L_ICON = Location.valueOf("icon");

}
