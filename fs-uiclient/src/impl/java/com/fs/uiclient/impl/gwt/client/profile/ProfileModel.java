/**
 * All right is from Author of the file.
 * Any usage of the code must be authorized by the the auther.
 * If not sure the detail of the license,please distroy the copies immediately.  
 * Nov 16, 2012
 */
package com.fs.uiclient.impl.gwt.client.profile;

import com.fs.uiclient.api.gwt.client.profile.ProfileModelI;
import com.fs.uicommons.api.gwt.client.editor.basic.EnumEditorI;
import com.fs.uicommons.api.gwt.client.editor.image.ImageCropEditorI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.data.basic.IntegerData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;

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
		ControlUtil.addAction(this, ProfileModelI.A_INIT);
		ControlUtil.addAction(this, "submit");
		//
		FormModel def = this.getDefaultForm();
		def.addField("email", StringData.class);
		def.addField("age", IntegerData.class);
		FieldModel genderFM = def.addField("gender", StringData.class,
				EnumEditorI.class, new UiCallbackI<EnumEditorI, Object>() {

					@Override
					public Object execute(EnumEditorI t) {
						t.addOption("n/a");//
						t.addOption("male");//
						t.addOption("female");
						return null;
					}
				});

		// options
		//FieldModel iconFM = def.addField("icon", StringData.class,
		//		ImageFileUrlDataEditorI.class);
		
		FieldModel iconFM = def.addField("icon", StringData.class,
						ImageCropEditorI.class);
		
		def.addAction(ProfileModelI.A_SUBMIT);//
				
	}

}
