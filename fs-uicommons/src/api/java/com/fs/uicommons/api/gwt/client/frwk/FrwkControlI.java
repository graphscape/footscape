/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 24, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.frwk.login.LoginViewI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 */
public interface FrwkControlI extends ControlI{
	
	public void open();
	
	public void addHeaderItem(Path path);
	
	public HeaderViewI getHeaderView();
	
	public LoginViewI openLoginView();
	
}
