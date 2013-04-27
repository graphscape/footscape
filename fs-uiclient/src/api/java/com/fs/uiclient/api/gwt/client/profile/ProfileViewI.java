/**
 * All right is from Author of the file,to be explained in comming days.
 * Feb 1, 2013
 */
package com.fs.uiclient.api.gwt.client.profile;

import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicore.api.gwt.client.data.basic.DateData;

/**
 * @author wu
 * 
 */
public interface ProfileViewI extends ViewI {

	/**
	 * 
	 */
	// public void setEmail(String email);

	public void setBirthDay(DateData bd);

	public void setGender(String gender);

	public void setIcon(String icon);

}
