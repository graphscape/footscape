/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 19, 2012
 */
package com.fs.uiclient.impl.gwt.client.expe;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsViewI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class ExpEditView extends FormsView implements ExpEditViewI {

	public static final String F_BODY = "title";
	private ViewReferenceI managed;

	/**
	 * @param ctn
	 */
	public ExpEditView(ContainerI ctn) {
		super(ctn, "expe");
		this.addAction(Actions.A_EXPE_SUBMIT);//

		FormViewI fv = this.getDefaultForm();
		fv.addField(F_BODY, String.class);
		fv.getFormModel().addAction(Actions.A_EXPE_SUBMIT);//
	}

}
