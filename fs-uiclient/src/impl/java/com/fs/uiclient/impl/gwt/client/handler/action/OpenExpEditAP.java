/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.handler.action;

import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.handler.ActionHandlerSupport;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu create a new exp
 */
public class OpenExpEditAP extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public OpenExpEditAP(ContainerI c) {
		super(c);
	}

	@Override
	public void handle(ActionEvent ae) {
		ControlI c = (ControlI)ae.getSource();
		MainControlI mc = c.getManager().getControl(MainControlI.class, true);

		ExpEditModelI eem = mc.getExpExitModel();
		// TODO by control.
		eem.setValue(ExpEditModelI.L_ISOPEN, true);// listen by the view of the
													// model.
	}

}
