/**
 * Jun 25, 2012
 */
package com.fs.uiclient.impl.gwt.client;

import com.fs.uiclient.api.gwt.client.Actions;
import com.fs.uiclient.api.gwt.client.UiClientConstants;
import com.fs.uiclient.api.gwt.client.UiClientGwtSPI;
import com.fs.uiclient.api.gwt.client.coper.ExpMessage;
import com.fs.uiclient.api.gwt.client.expe.ExpEditControlI;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.exps.MyExpViewI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.main.MainModelI;
import com.fs.uiclient.api.gwt.client.profile.ProfileModelI;
import com.fs.uiclient.api.gwt.client.signup.SignupModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.exps.ExpItemView;
import com.fs.uiclient.impl.gwt.client.exps.ExpSearchControl;
import com.fs.uiclient.impl.gwt.client.handler.action.CooperConfirmAP;
import com.fs.uiclient.impl.gwt.client.handler.action.CooperRequestAP;
import com.fs.uiclient.impl.gwt.client.handler.action.ExpEditSumbitAP;
import com.fs.uiclient.impl.gwt.client.handler.action.ExpSearchAP;
import com.fs.uiclient.impl.gwt.client.handler.action.OpenExpEditAP;
import com.fs.uiclient.impl.gwt.client.handler.action.OpenMyExpAP;
import com.fs.uiclient.impl.gwt.client.handler.action.ProfileInitAP;
import com.fs.uiclient.impl.gwt.client.handler.action.ProfileSubmitAP;
import com.fs.uiclient.impl.gwt.client.handler.action.SignupSubmitAP;
import com.fs.uiclient.impl.gwt.client.handler.action.UserExpSelectAP;
import com.fs.uiclient.impl.gwt.client.handler.message.CooperConfirmSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.CooperRequestSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpConnectCreatedNotifyMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpConnectSearchMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpDeletedNotifyMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpEditSubmitMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpGetMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpMessageCreatedNotifyMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpMessageMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ExpSearchMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ProfileInitSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ProfileSubmitFailureMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ProfileSubmitSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.ResourceGetSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.SignupSubmitFailureMH;
import com.fs.uiclient.impl.gwt.client.handler.message.SignupSubmitSuccessMH;
import com.fs.uiclient.impl.gwt.client.handler.message.SuccessOrFailureEventMH;
import com.fs.uiclient.impl.gwt.client.handler.message.UeListRefreshMH;
import com.fs.uiclient.impl.gwt.client.handler.other.ClientStartEventHandler;
import com.fs.uiclient.impl.gwt.client.handler.other.LoginEventHandler;
import com.fs.uiclient.impl.gwt.client.handler.other.MyExpHeaderItemHandler;
import com.fs.uiclient.impl.gwt.client.handler.other.OpenConsoleItemHandler;
import com.fs.uiclient.impl.gwt.client.handler.other.OpenResourceItemHandler;
import com.fs.uiclient.impl.gwt.client.handler.other.ProfileHeaderItemHandler;
import com.fs.uiclient.impl.gwt.client.handler.other.SearchHeaderItemHandler;
import com.fs.uiclient.impl.gwt.client.handler.other.SignupHeaderItemHandler;
import com.fs.uiclient.impl.gwt.client.main.MainControl;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpItemView;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListControl;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListView;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf.CheckerSupport;

/**
 * @author wuzhen
 * 
 */
public class UiClientGwtSPIImpl implements UiClientGwtSPI {

	@Override
	public void active(ContainerI c) {
		UiClientI client = c.get(UiClientI.class, true);//

		this.activeInstanceOfChecker(c);

		this.activeActionHandlers(c, client);
		this.activeMessageHandlers(c, client);
		this.activeOtherHandlers(c, client);

		this.activeControls(c, client);

	}

	/**
	 * Jan 13, 2013
	 */
	private void activeOtherHandlers(ContainerI c, UiClientI client) {
		EventBusI eb = client.getEventBus(true);

		eb.addHandler(UserLoginEvent.TYPE, new LoginEventHandler(c));

		// header item
		eb.addHandler(HeaderItemEvent.TYPE.getAsPath().concat(HeaderNames.H2_SIGNUP),
				new SignupHeaderItemHandler(c));

		eb.addHandler(HeaderItemEvent.TYPE.getAsPath().concat(HeaderNames.H2_PROFILE),
				new ProfileHeaderItemHandler(c));
		eb.addHandler(HeaderItemEvent.TYPE.getAsPath().concat(HeaderNames.H1_MYEXP),
				new MyExpHeaderItemHandler(c));
		eb.addHandler(HeaderItemEvent.TYPE.getAsPath().concat(HeaderNames.H1_SEARCH),
				new SearchHeaderItemHandler(c));

		// bottom item
		eb.addHandler(HeaderItemEvent.TYPE.getAsPath().concat(HeaderNames.H1_ABOUT),
				new OpenResourceItemHandler(c, UiClientConstants.P_HTML_ABOUT));
		eb.addHandler(HeaderItemEvent.TYPE.getAsPath().concat(HeaderNames.H1_CONTACT),
				new OpenResourceItemHandler(c, UiClientConstants.P_HTML_CONTACT));
		eb.addHandler(HeaderItemEvent.TYPE.getAsPath().concat(HeaderNames.H1_CONSOLE),
				new OpenConsoleItemHandler(c));

		// client start
		eb.addHandler(AfterClientStartEvent.TYPE, new ClientStartEventHandler(c));

	}

	/**
	 * Jan 10, 2013
	 */
	private void activeActionHandlers(ContainerI c, UiClientI client) {
		EventBusI eb = client.getEventBus(true);

		eb.addHandler(Actions.A_EXPE_SUBMIT, new ExpEditSumbitAP(c));//

		eb.addHandler(Actions.A_EXPS_COOPER, new CooperRequestAP(c));

		eb.addHandler(Actions.A_EXPS_SEARCH, new ExpSearchAP(c));

		eb.addHandler(Actions.A_PROFILE_INIT, new ProfileInitAP(c));
		eb.addHandler(Actions.A_PROFILE_SUBMIT, new ProfileSubmitAP(c));
		eb.addHandler(Actions.A_SIGNUP_SUBMIT, new SignupSubmitAP(c));

		eb.addHandler(Actions.A_UEL_CREATE, new OpenExpEditAP(c));

		eb.addHandler(Actions.A_UEXPI_SELECT, new UserExpSelectAP(c));
		eb.addHandler(Actions.A_UEXPI_OPEN, new OpenMyExpAP(c));

		eb.addHandler(Actions.A_UEXP_COOPER_CONFIRM, new CooperConfirmAP(c));
	}

	/**
	 * Jan 3, 2013
	 */
	private void activeMessageHandlers(ContainerI c, UiClientI client) {
		EndPointI dis = client.getEndpoint();
		dis.addHandler(Path.valueOf("/endpoint/message"), new SuccessOrFailureEventMH(c));
		// exp
		dis.addHandler(Path.valueOf("/endpoint/message/cooper/request/success"),
				new CooperRequestSuccessMH(c));// search
		dis.addHandler(Path.valueOf("/endpoint/message/cooper/confirm/success"),
				new CooperConfirmSuccessMH(c));// search
		dis.addHandler(Path.valueOf("/endpoint/message/expc/search/success"), new ExpConnectSearchMH(c));// search
		dis.addHandler(Path.valueOf("/endpoint/message/expe/submit/success"), new ExpEditSubmitMH(c));// create
		// exp
		// exp
		dis.addHandler(Path.valueOf("/endpoint/message/exps/search/success"), new ExpSearchMH(c));// search
		dis.addHandler(Path.valueOf("/endpoint/message/exps/get/success"), new ExpGetMH(c));// search

		dis.addHandler(Path.valueOf("/endpoint/message/expm/search/success"), new ExpMessageMH(c));// search
		//
		// exp
		dis.addHandler(Path.valueOf("/endpoint/message/notify/exp-message-created"),
				new ExpMessageCreatedNotifyMH(c));// search

		dis.addHandler(Path.valueOf("/endpoint/message/notify/exp-connect-created"),
				new ExpConnectCreatedNotifyMH(c));// search
		dis.addHandler(Path.valueOf("/endpoint/message/notify/exp-deleted"), new ExpDeletedNotifyMH(c));

		dis.addHandler(Path.valueOf("/endpoint/message/profile/init/success"), new ProfileInitSuccessMH(c));//

		dis.addHandler(Path.valueOf("/endpoint/message/profile/submit/failure"),
				new ProfileSubmitFailureMH(c));//
		dis.addHandler(Path.valueOf("/endpoint/message/profile/submit/success"),
				new ProfileSubmitSuccessMH(c));//
		// exp
		// exp
		dis.addHandler(Path.valueOf("/endpoint/message/uelist/refresh/success"), new UeListRefreshMH(c));// refresh
		dis.addHandler(Path.valueOf("/endpoint/message/signup/submit/success"), new SignupSubmitSuccessMH(c));//
		// succ
		dis.addHandler(Path.valueOf("/endpoint/message/signup/submit/failure"), new SignupSubmitFailureMH(c));//

		// open resource
		dis.addHandler(Path.valueOf("/endpoint/message/resource/get/success"), new ResourceGetSuccessMH(c));//

	}

	private void activeControls(ContainerI c, UiClientI client) {
		//
		//
		ControlManagerI manager = client.getChild(ControlManagerI.class, true);

		manager.addControl(new MainControl(c, "main"));
		manager.addControl(new UserExpListControl(c, "uelist"));
		manager.addControl(new ExpSearchControl(c, "exps"));

	}

	private void activeInstanceOfChecker(ContainerI c) {

		InstanceOf.addChecker(new CheckerSupport(ControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ControlI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(UiClientI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UiClientI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(ViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ButtonI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ButtonI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ListI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ListI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(EditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof EditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(LabelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LabelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(RootI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof RootI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(UserExpListControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UserExpListControlI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(ExpEditControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ExpEditControlI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(ExpSearchControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ExpSearchControlI;

			}

		});

		InstanceOf.addChecker(new CheckerSupport(ExpSearchModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ExpSearchModelI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(UserExpListModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UserExpListModelI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(UserExpModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UserExpModel;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(UserExpListView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UserExpListView;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(MainModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MainModelI;

			}

		});

		InstanceOf.addChecker(new CheckerSupport(MyExpViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MyExpViewI;

			}

		});

		InstanceOf.addChecker(new CheckerSupport(ExpItemView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ExpItemView;

			}

		});

		InstanceOf.addChecker(new CheckerSupport(UserExpItemView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UserExpItemView;

			}

		});

		InstanceOf.addChecker(new CheckerSupport(MainControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MainControlI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(ExpItemModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ExpItemModel;

			}

		});

		InstanceOf.addChecker(new CheckerSupport(ExpMessage.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ExpMessage;

			}

		});

		InstanceOf.addChecker(new CheckerSupport(SignupModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof SignupModelI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(ProfileModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ProfileModelI;

			}

		});

	}
}
