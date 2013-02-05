/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.profile;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.editor.basic.EnumEditorI;
import com.fs.uicommons.api.gwt.client.editor.image.ImageCropEditorI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormViewI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormsViewI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class ProfileView extends FormsView {

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
		this.addAction(Actions.A_PROFILE_INIT);
		this.addAction(Actions.A_PROFILE_SUBMIT);
		if (this.listenIcon) {
			this.image = DOM.createImg();
			DOM.appendChild(this.body, this.image);
		}

		FormViewI def = this.addForm(FormsViewI.FM_DEFAULT);
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

		def.getFormModel().addAction(Actions.A_PROFILE_SUBMIT);//
	}

}
