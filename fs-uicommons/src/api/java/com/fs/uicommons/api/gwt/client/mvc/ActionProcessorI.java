/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc;

import com.fs.uicore.api.gwt.client.UiRequest;
import com.fs.uicore.api.gwt.client.UiResponse;

/**
 * @author wu
 * 
 */
public interface ActionProcessorI {

	public void processRequest(final ControlI c, final String a, UiRequest req);

	public void processResponse(final ControlI c, final String a, UiResponse res);

}
