/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 28, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class GetExpAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public GetExpAP(ContainerI c) {
		super(c);
	}

	/**
	 * Nov 28, 2012
	 * <p>
	 * Get the detail of the exp,user click in the msglist, to open the detail.
	 */
	@Override
	public void handle(ActionEvent ae) {
		ControlI c = (ControlI) ae.getSource();

	}

}
