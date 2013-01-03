/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 20, 2012
 */
package com.fs.uiclient.impl.gwt.client.uelist;

import com.fs.uiclient.api.gwt.client.main.MainModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uicommons.api.gwt.client.mvc.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.mvc.support.ActionHandlerSupport;

/**
 * @author wu
 */
public class SelectExpAP extends ActionHandlerSupport {

	@Override
	public void handle(ActionEvent ae) {
		MainModelI fm = ae.getControl().getModel().getTopObject().find(MainModelI.class, true);
		// TODO create only when required.
		UserExpModel eem = fm.getChild(UserExpModel.class, true);
		eem.setValue(UserExpModel.L_ISSELECTED, true);// listen by the view of
														// the
														// model.
	}

}
