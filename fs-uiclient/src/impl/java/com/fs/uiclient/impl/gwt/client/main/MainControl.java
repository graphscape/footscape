/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.main;

import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.profile.ProfileModelI;
import com.fs.uiclient.api.gwt.client.signup.SignupModelI;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.impl.gwt.client.cooper.CooperModel;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditModel;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditView;
import com.fs.uiclient.impl.gwt.client.exps.ExpSearchModel;
import com.fs.uiclient.impl.gwt.client.exps.ExpSearchView;
import com.fs.uiclient.impl.gwt.client.profile.ProfileModel;
import com.fs.uiclient.impl.gwt.client.profile.ProfileView;
import com.fs.uiclient.impl.gwt.client.signup.SignupModel;
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

	/**
	 * @param name
	 */
	public MainControl(ContainerI c, String name) {
		super(c, name);
	}

	@Override
	public SignupModelI getSignupModel() {
		//
		SignupModelI rt = this.getOrCreateModel(this.getRootModel(), SignupModelI.class,
				new CreaterI<SignupModelI>() {

					@Override
					public SignupModelI create(ContainerI ct) {
						// TODO Auto-generated method stub
						return new SignupModel("signup");
					}
				});
		return rt;
	}

	public ProfileModelI getProfileModel() {
		//
		ProfileModelI rt = this.getOrCreateModel(this.getRootModel(), ProfileModelI.class, name,
				new CreaterI<ProfileModelI>() {

					@Override
					public ProfileModelI create(ContainerI ct) {
						// TODO Auto-generated method stub
						return new ProfileModel("signup");
					}
				});
		return rt;
	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public ExpSearchModelI getExpSearchModel() {
		//
		ExpSearchModelI rt = this.getOrCreateModel(this.getRootModel(), ExpSearchModelI.class,
				new CreaterI<ExpSearchModelI>() {

					@Override
					public ExpSearchModelI create(ContainerI ct) {
						// TODO Auto-generated method stub
						return new ExpSearchModel("exp-search");
					}
				});
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.main.MainControlI#getUeListModel()
	 */
	@Override
	public UserExpListModelI getUeListModel() {
		//
		UserExpListModelI rt = this.getOrCreateModel(this.getRootModel(), UserExpListModelI.class,
				new CreaterI<UserExpListModelI>() {

					@Override
					public UserExpListModelI create(ContainerI ct) {
						// TODO Auto-generated method stub
						return new UserExpListModel("ue-list");
					}
				});
		return rt;
	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public ExpEditModelI getExpEditModel() {
		ExpEditModelI rt = this.getOrCreateModel(this.getRootModel(), ExpEditModelI.class,
				new CreaterI<ExpEditModelI>() {

					@Override
					public ExpEditModelI create(ContainerI ct) {
						// TODO Auto-generated method stub
						return new ExpEditModel("exp-edit");
					}
				});
		return rt;
	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public CooperModelI getCooperModel() {
		CooperModelI rt = this.getOrCreateModel(this.getRootModel(), CooperModelI.class,
				new CreaterI<CooperModelI>() {

					@Override
					public CooperModelI create(ContainerI ct) {
						// TODO Auto-generated method stub
						return new CooperModel();
					}
				});
		return rt;
	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public ExpSearchViewI openExpSearch() {
		//
		final ExpSearchModelI exps = this.getExpSearchModel();

		ExpSearchView esv = this.gorOrCreateViewInBody(Path.valueOf("/exp-search"),
				new CreaterI<ExpSearchView>() {

					@Override
					public ExpSearchView create(ContainerI ct) {
						return new ExpSearchView(ct, exps);
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
		//
		final ExpEditModelI uel = this.getExpEditModel();

		ExpEditViewI uelv = this.gorOrCreateViewInBody(Path.valueOf("/expe"), new CreaterI<ExpEditViewI>() {

			@Override
			public ExpEditViewI create(ContainerI ct) {
				return new ExpEditView(ct, (ExpEditModel) uel);
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
		final SignupModelI sm = this.getSignupModel();
		SignupView rt = this.gorOrCreateViewInBody(Path.valueOf("/signup"), new CreaterI<SignupView>() {

			@Override
			public SignupView create(ContainerI ct) {
				return new SignupView(ct, (SignupModel) sm);
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

}
