/**
 * All right is from Author of the file,to be explained in comming days.
 * Feb 1, 2013
 */
package com.fs.uiclient.api.gwt.client.exps;

import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uiclient.api.gwt.client.uexp.ExpConnect;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;

/**
 * @author wu
 * 
 */
public interface MyExpViewI extends ViewI {

	//TODO myexp model as parameter
	public void setMyExp(String body);
	
	/**
	 * Mar 6, 2013
	 */
	public void addOrUpdateMessage(ExpMessage msg);

	public void addOrUpdateConnected(ExpConnect exp);
}