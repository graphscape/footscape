/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.expe;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;

/**
 * @author wu
 * 
 */
public class ExpEditModel extends FormsModel implements ExpEditModelI {

	/**
	 * @param name
	 */
	public ExpEditModel(String name) {
		super(name);

		ControlUtil.addAction(this, Actions.A_EXPE_SUBMIT);//

		FormModel fm = this.getDefaultForm();
		fm.addField(F_BODY, String.class);
		fm.addAction(Actions.A_EXPE_SUBMIT);//
	}

}
