/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.contactus;

import java.util.HashMap;
import java.util.Map;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.contactus.ContactUsViewI;
import com.fs.uiclient.impl.gwt.client.profile.ProfileModel;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;

/**
 * @author wu
 * 
 */
public class ContactUsView extends FormsView implements ContactUsViewI {

	/**
	 * @param ctn
	 */
	public ContactUsView(ContainerI ctn) {
		super(ctn, "cttus");

		this.addAction(Actions.A_CONTACTUS_SUBMIT);

		FormViewI def = this.getDefaultForm();
		// def.addField("email", String.class);
		// def.addField("age", Integer.class);
		{

			def.addField("name", String.class);
		}
		{

			def.addField("email", String.class);
		}
		{

			def.addField("subject", String.class);
		}
		{
			Map<String, Object> pts = new HashMap<String, Object>();
			pts.put(StringEditorI.PK_TEXAREA, true);

			FieldModel fm = def.addField("body", String.class, pts);
		}

		def.getFormModel().addAction(Actions.A_CONTACTUS_SUBMIT);//
	}

}
