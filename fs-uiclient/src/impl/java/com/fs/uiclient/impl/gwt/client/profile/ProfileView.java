/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.profile;

import com.fs.uiclient.api.gwt.client.profile.ProfileModelI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.frwk.ViewReferenceI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.ModelI.ValueWrapper;
import com.fs.uicore.api.gwt.client.simple.OffspringValueDeliver;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public class ProfileView extends FormsView implements ViewReferenceI.AwareI {

	public static String HEADER_ITEM_PROFILE = "profile";//

	protected ItemModel headerItem;

	private ViewReferenceI managed;

	private Element image;

	private boolean listenIcon;// listen icon data

	/**
	 * @param ctn
	 */
	public ProfileView(String name, ContainerI ctn) {
		super(name, ctn);
		if (this.listenIcon) {
			this.image = DOM.createImg();
			DOM.appendChild(this.body, this.image);
		}
	}

	@Override
	public ProfileModelI getModel() {
		return (ProfileModelI) this.model;
	}

	// @Override
	// public void manage() {
	// //
	// ManagerModelI cm = this.getCenterModel();
	// this.managed = cm.manage(this.model, this);// this view will be add to
	// // the
	// // child of center's some
	// // child widgetI.
	//
	// // add sign up header.
	// HeaderModelI hm = this.getFrwkModel().getHeader();
	//
	// this.headerItem = hm.addItem(HEADER_ITEM_PROFILE,
	// HeaderModelI.ItemModel.P_RIGHT, this.managed);//
	// // call init at control.
	//
	// SimpleValueDeliver<Boolean, Boolean> sd = new SimpleValueDeliver<Boolean,
	// Boolean>(
	// this.managed, ViewReferenceI.L_SELECTED, this.model,
	// ProfileModelI.L_VIEW_OPENED);
	// sd.mapDefaultDirect();
	// sd.start();
	// this.headerItem.setState(ItemModel.S_UP);// hiden login view.
	//
	// }

	protected FrwkModelI getFrwkModel() {
		FrwkModelI fc = this.getModel().getTopObject().getChild(FrwkModelI.class, true);

		return fc;
	}

	/*
	 * Nov 17, 2012
	 */
	@Override
	protected void processChildFormModelAdd(FormModel fm) {
		//
		super.processChildFormModelAdd(fm);
		// listen to the icon value inf field model
		if (this.listenIcon) {
			OffspringValueDeliver<String, String> ovd = new OffspringValueDeliver<String, String>(fm,
					FieldModel.class, ProfileModelI.L_ICON.getProperty(), FieldModel.L_DEFAULT, this.model,
					ProfileModelI.L_ICON);
			ovd.mapDefaultDirect();// NOTE
			ovd.start();
		}
	}

	@Override
	protected void processModelValue(Location loc, ValueWrapper vw) {
		super.processModelValue(loc, vw);
		if (ProfileModelI.L_ICON.equals(loc)) {
			this.processModelIconValue((String) vw.getValue());
		}
	}

	/**
	 * Nov 16, 2012
	 */
	protected void processModelIconValue(String value) {
		if (this.image != null) {
			this.image.setAttribute("src", value == null ? "null" : value);// TODO
																			// none
																			// image.
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ViewReferenceI.AwareI
	 * #setManaged
	 * (com.fs.uicommons.api.gwt.client.frwk.CenterModelI.ViewReferenceI)
	 */
	@Override
	public void setViewReference(ViewReferenceI mgd) {
		this.managed = mgd;
	}

}
