/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;

/**
 * @author wu
 * 
 */
public interface UserExpListControlI extends ControlI {

	public void refresh(String expId);

	public void detailExp(String expId);

	public void addOrUpdateUserExp(UserExpModel uem);
	
	public void addOrUpdateExpMessage(ExpMessage msg);
	
	public void addOrUpdateExpConnect(ExpConnect ec);
	
	public void deleteExp(String expId);

}
