/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.profile;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.profile.ProfileViewI;
import com.fs.uicommons.api.gwt.client.editor.basic.EnumEditorI;
import com.fs.uicommons.api.gwt.client.editor.image.ImageCropEditorI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class ProfileView extends FormsView implements ProfileViewI {

	public static String HEADER_ITEM_PROFILE = "profile";//

	private Element image;

	private boolean listenIcon;// listen icon data

	ProfileModel model;

	/**
	 * @param ctn
	 */
	public ProfileView(ContainerI ctn, ProfileModel pm) {
		super(ctn, "profile");
		this.model = pm;
		//this.addAction(Actions.A_PROFILE_INIT);
		this.addAction(Actions.A_PROFILE_SUBMIT);
		if (this.listenIcon) {
			this.image = DOM.createImg();
			DOM.appendChild(this.body, this.image);
		}

		FormViewI def = this.getDefaultForm();
		// def.addField("email", String.class);
		//def.addField("age", Integer.class);
		def.addField("birthDay", DateData.class);
		def.addField("gender", String.class, EnumEditorI.class, new UiCallbackI<EnumEditorI, Object>() {

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

		def.getFormModel().addAction(Actions.A_PROFILE_SUBMIT);//
	}

	@Override
	public void attach() {
		super.attach();
		this.dispatchActionEvent(Actions.A_PROFILE_INIT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.profile.ProfileViewI#setAge(int)
	 */
	@Override
	public void setBirthDay(DateData bd){
		FormViewI def = this.getDefaultForm();
		def.setFieldValue("birthDay", bd);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.profile.ProfileViewI#setGender(java.lang
	 * .String)
	 */
	@Override
	public void setGender(String gender) {
		FormViewI def = this.getDefaultForm();
		def.setFieldValue("gender", gender);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.profile.ProfileViewI#setIcon(java.lang
	 * .String)
	 */
	@Override
	public void setIcon(String icon) {
		FormViewI def = this.getDefaultForm();
		def.setFieldValue("icon", icon);

	}

}
