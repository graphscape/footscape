/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.main;

import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.profile.ProfileModelI;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.impl.gwt.client.activities.ActivitiesModel;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditView;
import com.fs.uiclient.impl.gwt.client.exps.ExpSearchModel;
import com.fs.uiclient.impl.gwt.client.exps.ExpSearchView;
import com.fs.uiclient.impl.gwt.client.profile.ProfileModel;
import com.fs.uiclient.impl.gwt.client.profile.ProfileView;
import com.fs.uiclient.impl.gwt.client.signup.SignupView;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListModel;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListView;
import com.fs.uicommons.api.gwt.client.CreaterI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class MainControl extends ControlSupport implements MainControlI {

	ProfileModelI profile;
	UserExpListModelI uelist;
	ActivitiesModelI activities;

	/**
	 * @param name
	 */
	public MainControl(ContainerI c, String name) {
		super(c, name);
		this.profile = new ProfileModel("profile");
		this.uelist = new UserExpListModel("ue-list");
		this.activities = new ActivitiesModel("activities");
	}

	public ProfileModelI getProfileModel() {
		return this.profile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.main.MainControlI#getUeListModel()
	 */
	@Override
	public UserExpListModelI getUeListModel() {
		return this.uelist;
	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public ExpSearchViewI openExpSearch() {
	
		ExpSearchView esv = this.gorOrCreateViewInBody(Path.valueOf("/exp-search"),
				new CreaterI<ExpSearchView>() {

					@Override
					public ExpSearchView create(ContainerI ct) {
						return new ExpSearchView(ct);
					}
				});
		return esv;

	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public UserExpListViewI openUeList() {
		//
		final UserExpListModelI uel = this.getUeListModel();

		UserExpListViewI uelv = this.gorOrCreateViewInBody(Path.valueOf("/uelist"),
				new CreaterI<UserExpListView>() {

					@Override
					public UserExpListView create(ContainerI ct) {
						return new UserExpListView(ct, uel);
					}
				});
		return uelv;

	}

	@Override
	public ExpEditViewI openExpEditView() {

		ExpEditViewI uelv = this.gorOrCreateViewInBody(Path.valueOf("/expe"), new CreaterI<ExpEditViewI>() {

			@Override
			public ExpEditViewI create(ContainerI ct) {
				return new ExpEditView(ct);
			}
		});
		return uelv;

	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public SignupViewI openSignup() {
		//

		SignupView rt = this.gorOrCreateViewInBody(Path.valueOf("/signup"), new CreaterI<SignupView>() {

			@Override
			public SignupView create(ContainerI ct) {
				return new SignupView(ct);
			}
		});
		return rt;
	}

	@Override
	public void openProfile() {
		final ProfileModelI sm = this.getProfileModel();
		ProfileView rt = this.gorOrCreateViewInBody(Path.valueOf("/profile"), new CreaterI<ProfileView>() {

			@Override
			public ProfileView create(ContainerI ct) {
				return new ProfileView(ct, (ProfileModel) sm);
			}
		});

	}

	/*
	 * Feb 2, 2013
	 */
	@Override
	public ActivitiesModelI getActivitiesModel() {
		return this.activities;
	}

}
