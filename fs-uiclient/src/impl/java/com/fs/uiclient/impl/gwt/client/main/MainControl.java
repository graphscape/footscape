/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.main;

import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.exps.MyExpViewI;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.profile.ProfileModelI;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditView;
import com.fs.uiclient.impl.gwt.client.exps.ExpSearchView;
import com.fs.uiclient.impl.gwt.client.profile.ProfileModel;
import com.fs.uiclient.impl.gwt.client.profile.ProfileView;
import com.fs.uiclient.impl.gwt.client.signup.SignupView;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListView;
import com.fs.uiclient.impl.gwt.client.uexp.MyExpView;
import com.fs.uicommons.api.gwt.client.Constants;
import com.fs.uicommons.api.gwt.client.CreaterI;
import com.fs.uicommons.api.gwt.client.frwk.BodyViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.commons.Holder;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;

/**
 * @author wu
 * 
 */
public class MainControl extends ControlSupport implements MainControlI {

	ProfileModelI profile;

	/**
	 * @param name
	 */
	public MainControl(ContainerI c, String name) {
		super(c, name);
		this.profile = new ProfileModel("profile");

	}

	public ProfileModelI getProfileModel() {
		return this.profile;
	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public ExpSearchViewI openExpSearch(boolean showView) {
		Path path = Path.valueOf("/exp-search");

		ExpSearchView esv = this.getOrCreateViewInBody(path, new CreaterI<ExpSearchView>() {

			@Override
			public ExpSearchView create(ContainerI ct) {
				return new ExpSearchView(ct);
			}
		}, showView);

		return esv;

	}

	/*
	 * Jan 31, 2013
	 */
	@Override
	public UserExpListViewI openUeList() {
		//
		final Holder<Boolean> holder = new Holder<Boolean>(Boolean.FALSE);
		UserExpListViewI uelv = this.getOrCreateViewInBody(Path.valueOf("/uelist"),
				new CreaterI<UserExpListView>() {

					@Override
					public UserExpListView create(ContainerI ct) {
						holder.setTarget(true);//
						return new UserExpListView(ct);
					}
				});
		// created called,first created.
		if (holder.getTarget()) {
			this.refreshUeList();//
		}
		return uelv;

	}

	@Override
	public ExpEditViewI openExpEditView() {

		ExpEditViewI uelv = this.getOrCreateViewInBody(Path.valueOf("/expe"), new CreaterI<ExpEditViewI>() {

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

		SignupView rt = this.getOrCreateViewInBody(UiClientConstants.P_SIGNUP, new CreaterI<SignupView>() {

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
		ProfileView rt = this.getOrCreateViewInBody(Path.valueOf("/profile"), new CreaterI<ProfileView>() {

			@Override
			public ProfileView create(ContainerI ct) {
				return new ProfileView(ct, (ProfileModel) sm);
			}
		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.main.MainControlI#openMyExp(java.lang.
	 * String)
	 */
	@Override
	public MyExpViewI openMyExp(final String expId) {
		Path path = this.getExpViewPath(expId);
		MyExpView esv = this.getOrCreateViewInBody(path, new CreaterI<MyExpView>() {

			@Override
			public MyExpView create(ContainerI ct) {
				return new MyExpView(ct, expId);
			}
		});

		Boolean b = (Boolean) esv.getProperty("isNew", Boolean.TRUE);
		if (b) {
			this.refreshExpContent(expId);
			this.refreshExpConnect(expId);
			this.refreshExpMessage(expId);//
			esv.setProperty("isNew", Boolean.FALSE);
		}
		return esv;
	}

	public void refreshExpContent(String expId) {

		MsgWrapper req = this.newRequest(Path.valueOf("/exps/get"));
		req.setPayload("expId", expId);//
		this.sendMessage(req);
		// see ExpGetMH
		// see updateTitleOfExpTab

	}

	@Override
	public void refreshExpMessage(String expId) {
		// TODO filter expId
		String accId = this.getUserInfo().getAccountId();

		MsgWrapper req = this.newRequest(Path.valueOf("/expm/search"));
		req.setPayload("accountId2", accId);//

		this.sendMessage(req);

	}

	@Override
	public void refreshExpConnect(String expId) {
		// TODO expId filter
		String accId = this.getUserInfo().getAccountId();
		MsgWrapper req = this.newRequest(Path.valueOf("/expc/search"));
		req.setPayload("accountId1", accId);//
		this.sendMessage(req);
	}

	public UserInfo getUserInfo() {
		return this.getClient(true).getEndpoint().getUserInfo();
	}

	private Path getExpViewPath(String expId) {
		return Path.valueOf("/myexp/" + expId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.main.MainControlI#updateTitleOfExpTab(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void setExpDetail(String expId, String body) {
		// update title of tab that point to the exp view
		BodyViewI bv = this.getBodyView();
		Path path = this.getExpViewPath(expId);
		bv.setTitleOfItem(path, body, false);
		//
		MyExpViewI mv = this.openMyExp(expId);
		mv.setMyExp(body);

	}

	/*
	 * Mar 16, 2013
	 */
	@Override
	public void closeMyExp(String expId) {
		//
		Path path = this.getExpViewPath(expId);
		this.getBodyView().tryCloseItem(path);
	}

	protected UserExpListControlI getUeListControl() {
		return this.getManager().getControl(UserExpListControlI.class, true);
	}

	@Override
	public void refreshUeList() {
		this.getUeListControl().refresh(null);//
	}

	@Override
	public void closeLoginView() {
		this.getBodyView().tryCloseItem(Constants.LOGIN_VIEW);
	}

	/*
	 * Mar 21, 2013
	 */
	@Override
	public void closeAll() {
		this.getBodyView().closeAllItems();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uiclient.api.gwt.client.main.MainControlI#closeSignup()
	 */
	@Override
	public void closeSignup() {
		this.getBodyView().tryCloseItem(UiClientConstants.P_SIGNUP);
	}

}
