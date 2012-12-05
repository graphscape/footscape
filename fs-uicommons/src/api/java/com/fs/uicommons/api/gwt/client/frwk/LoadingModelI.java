/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * 
 * @author wuzhen
 * 
 */
public interface LoadingModelI extends ModelI {

	public static final String MK_REQUEST_COUNTER = "REQUEST_COUNTER";

	public int getRequestCounter();

	public void beforeRequest();

	public void afterResponse();

}
