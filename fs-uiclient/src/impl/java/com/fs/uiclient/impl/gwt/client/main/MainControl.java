/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package com.fs.uiclient.impl.gwt.client.main;

import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.coper.MyExp;
import com.fs.uiclient.api.gwt.client.exps.ExpEditViewI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchViewI;
import com.fs.uiclient.api.gwt.client.exps.MyExpViewI;
import com.fs.uiclient.api.gwt.client.exps.UserExpListViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.profile.ProfileModelI;
import com.fs.uiclient.api.gwt.client.profile.ProfileViewI;
import com.fs.uiclient.api.gwt.client.signup.SignupViewI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.user.UserInfoViewI;
import com.fs.uiclient.impl.gwt.client.expe.ExpEditView;
import com.fs.uiclient.impl.gwt.client.exps.ExpSearchView;
import com.fs.uiclient.impl.gwt.client.profile.ProfileModel;
import com.fs.uiclient.impl.gwt.client.profile.ProfileView;
import com.fs.uiclient.impl.gwt.client.signup.SignupView;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListView;
import com.fs.uiclient.impl.gwt.client.uexp.MyExpView;
import com.fs.uiclient.impl.gwt.client.user.UserInfoViewImpl;
import com.fs.uicommons.api.gwt.client.CreaterI;
import com.fs.uicommons.api.gwt.client.UiCommonsConstants;
import com.fs.uicommons.api.gwt.client.frwk.BodyViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicommons.api.gwt.client.widget.html.HtmlElementWidgetI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.MsgWrapper;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.commons.Holder;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Cause;
import com.fs.uicore.api.gwt.client.data.basic.DateData;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

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
	public UserExpListViewI openUeList(boolean show) {
		//
		final Holder<Boolean> holder = new Holder<Boolean>(Boolean.FALSE);
		UserExpListViewI uelv = this.getOrCreateViewInBody(Path.valueOf("/uelist"),
				new CreaterI<UserExpListView>() {

					@Override
					public UserExpListView create(ContainerI ct) {
						holder.setTarget(true);//
						return new UserExpListView(ct);
					}
				}, show);
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
		}, true);
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
		}, true);
		return rt;
	}

	@Override
	public ProfileViewI openProfile() {
		final ProfileModelI sm = this.getProfileModel();
		ProfileView rt = this.getOrCreateViewInBody(Path.valueOf("/profile"), new CreaterI<ProfileView>() {

			@Override
			public ProfileView create(ContainerI ct) {
				return new ProfileView(ct, (ProfileModel) sm);
			}
		}, true);
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.main.MainControlI#openMyExp(java.lang.
	 * String)
	 */
	@Override
	public MyExpViewI openMyExp(final Cause cause, final String expId, boolean show) {

		Path path = this.getExpViewPath(expId);
		final MyExpView esv = this.getOrCreateViewInBody(path, new CreaterI<MyExpView>() {

			@Override
			public MyExpView create(ContainerI ct) {
				return new MyExpView(ct, expId);
			}
		}, show);
		Boolean b = (Boolean) esv.getProperty("isNew", Boolean.TRUE);
		esv.setProperty("isNew", Boolean.FALSE);
		if (this.logger.isDebugEnabled()) {
			this.logger.debug("cause:" + cause + ",openMyExp,expId:" + expId + ",show:" + show + ",isNew:"
					+ b);
		}
		if (b) {

			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					MainControl.this.afterOpenNewMyExp(esv, expId);
				}
			});
		}
		return esv;
	}

	@Override
	public UserInfoViewI openUserInfo(boolean show) {
		Path path = Path.valueOf("/tab/uinfo");
		final UserInfoViewI esv = this.getOrCreateViewInBody(path, new CreaterI<UserInfoViewI>() {

			@Override
			public UserInfoViewI create(ContainerI ct) {
				return new UserInfoViewImpl(ct);
			}
		}, show);
		return esv;
	}

	protected void refreshUserInfo(String accId) {
		MsgWrapper req = this.newRequest(UiClientConstants.RP_USERINFO_GET);
		req.setPayload("accountId", accId);//
		this.sendMessage(req);
	}

	protected void afterOpenNewMyExp(MyExpView esv, String expId) {

		this.refreshExpContent(expId);
		this.refreshExpConnect(expId);
		this.refreshExpMessage(Cause.valueOf("afterOpenNewMyExp"), expId);//

	}

	public void refreshExpContent(String expId) {

		MsgWrapper req = this.newRequest(Path.valueOf("/exps/get"));
		req.setPayload("expId", expId);//
		this.sendMessage(req);
		// see ExpGetMH
		// see updateTitleOfExpTab

	}

	@Override
	public void refreshExpMessage(Cause cause, String expId) {
		// TODO filter expId

		MyExpViewI me = this.openMyExp(cause.getChild("refreshExpMessage"), expId, false);

		DateData timestamp1 = me.getLatestMessageTimestamp();
		String accId = this.getUserInfo().getAccountId();

		MsgWrapper req = this.newRequest(Path.valueOf("/expm/search"));
		req.setPayload("accountId2", accId);//
		req.setPayload("expId2", expId);
		if (timestamp1 == null) {// if no message,then limit the result,get the
									// newest list with limit
			int limit = this.getClient(true).getParameterAsInt(UiClientConstants.PK_MESSAGE_QUERY_LIMIT, 10);
			req.setPayload("limit", limit);
		} else {// no limit,there alreay message got,then refresh to the newest
				// list from the timestamp1.

			req.setPayload("timestamp1", timestamp1);// the
		}
		this.sendMessage(req);

	}

	@Override
	public void refreshExpConnect(String expId) {
		// TODO expId filter
		String accId = this.getUserInfo().getAccountId();
		MsgWrapper req = this.newRequest(Path.valueOf("/expc/search"));
		req.setPayload("accountId1", accId);//
		req.setPayload("expId1", expId);
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
	public void setExpDetail(MyExp me) {
		
		String expId = me.getId();
		// update title of tab that point to the exp view
		BodyViewI bv = this.getBodyView();
		Path path = this.getExpViewPath(me.getId());
		bv.setTitleOfItem(path, me.getTitle(), false);
		//
		MyExpViewI mv = this.openMyExp(Cause.valueOf("setExpDetail"), expId, false);

		mv.setMyExp(me);

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
		this.getBodyView().tryCloseItem(UiCommonsConstants.LOGIN_VIEW);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uiclient.api.gwt.client.main.MainControlI#openHtml(com.fs.uicore
	 * .api.gwt.client.commons.Path)
	 */
	@Override
	public HtmlElementWidgetI openHtmlResource(Path path, boolean refresh) {

		final HtmlElementWidgetI rt = this.getOrCreateViewInBody(path, new CreaterI<HtmlElementWidgetI>() {

			@Override
			public HtmlElementWidgetI create(ContainerI ct) {
				return ct.get(WidgetFactoryI.class, true).create(HtmlElementWidgetI.class);
			}
		}, true);
		// refresh
		if (refresh) {
			MsgWrapper req = this.newRequest(Path.valueOf("/resource/get"));

			req.setHeader("url", "classpath://" + path.toString());

			this.sendMessage(req);

		}
		return rt;

	}

}
