/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.api.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.core.Cause;

/**
 * @author wu
 * @deprecated use view.
 */
public interface UserExpListControlI extends ControlI {

	public void refresh(String expId);

	public void detailExp(String expId);

	public void addOrUpdateUserExp(UserExpModel uem);
	
	public void addOrUpdateExpMessage(Cause cause,ExpMessage msg);
	
	public void addOrUpdateExpConnect(Cause cause,ExpConnect ec);
	
	public void deleteExp(String expId);

}
