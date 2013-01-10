/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 16, 2012
 */
package com.fs.uiclient.impl.gwt.client.profile;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.profile.ProfileModelI;
import com.fs.uicommons.api.gwt.client.editor.basic.EnumEditorI;
import com.fs.uicommons.api.gwt.client.editor.image.ImageCropEditorI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;

/**
 * @author wuzhen
 * 
 */
public class ProfileModel extends FormsModel implements ProfileModelI {

	/**
	 * @param name
	 */
	public ProfileModel(String name) {
		super(name);
		ControlUtil.addAction(this, Actions.A_PROFILE_INIT);
		ControlUtil.addAction(this, Actions.A_PROFILE_SUBMIT);
		//
		FormModel def = this.getDefaultForm();
		def.addField("email", String.class);
		def.addField("age", Integer.class);
		FieldModel genderFM = def.addField("gender", String.class, EnumEditorI.class,
				new UiCallbackI<EnumEditorI, Object>() {

					@Override
					public Object execute(EnumEditorI t) {
						t.addOption("n/a");//
						t.addOption("male");//
						t.addOption("female");
						return null;
					}
				});

		// options
		// FieldModel iconFM = def.addField("icon", String.class,
		// ImageFileUrlDataEditorI.class);

		FieldModel iconFM = def.addField("icon", String.class, ImageCropEditorI.class);

		def.addAction(Actions.A_PROFILE_SUBMIT);//

	}

}
