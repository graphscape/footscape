/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.gwt.client;

import com.fs.uicommons.api.gwt.client.Actions;
import com.fs.uicommons.api.gwt.client.AdjusterI;
import com.fs.uicommons.api.gwt.client.HeaderItems;
import com.fs.uicommons.api.gwt.client.UiCommonsGPI;
import com.fs.uicommons.api.gwt.client.drag.DraggerI;
import com.fs.uicommons.api.gwt.client.editor.basic.BooleanEditorI;
import com.fs.uicommons.api.gwt.client.editor.basic.EnumEditorI;
import com.fs.uicommons.api.gwt.client.editor.basic.IntegerEditorI;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.editor.image.ImageCropEditorI;
import com.fs.uicommons.api.gwt.client.editor.image.ImageFileUrlDataEditorI;
import com.fs.uicommons.api.gwt.client.editor.properties.PropertiesEditorI;
import com.fs.uicommons.api.gwt.client.editor.properties.PropertiesEditorI.PropertyModel;
import com.fs.uicommons.api.gwt.client.event.AutoLoginRequireEvent;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;
import com.fs.uicommons.api.gwt.client.frwk.BodyModelI;
import com.fs.uicommons.api.gwt.client.frwk.BodyViewI;
import com.fs.uicommons.api.gwt.client.frwk.ConsoleModelI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkViewI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderViewI;
import com.fs.uicommons.api.gwt.client.frwk.WindowModelI;
import com.fs.uicommons.api.gwt.client.frwk.blank.BlankModelI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.LineModel;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI;
import com.fs.uicommons.api.gwt.client.frwk.login.LoginModelI;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupModel;
import com.fs.uicommons.api.gwt.client.gchat.ChatGroupViewI;
import com.fs.uicommons.api.gwt.client.gchat.GChatControlI;
import com.fs.uicommons.api.gwt.client.gchat.MessageModel;
import com.fs.uicommons.api.gwt.client.mvc.ActionModelI;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.api.gwt.client.widget.bar.BarWidgetI;
import com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.basic.DateWI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.error.ErrorInfosWidgetI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicommons.api.gwt.client.widget.menu.MenuItemWI;
import com.fs.uicommons.api.gwt.client.widget.menu.MenuWI;
import com.fs.uicommons.api.gwt.client.widget.panel.PanelWI;
import com.fs.uicommons.api.gwt.client.widget.popup.PopupWI;
import com.fs.uicommons.api.gwt.client.widget.stack.StackWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabWI;
import com.fs.uicommons.api.gwt.client.widget.tab.TabberWI;
import com.fs.uicommons.api.gwt.client.widget.table.TableI;
import com.fs.uicommons.api.gwt.client.widget.wpanel.WindowPanelWI;
import com.fs.uicommons.impl.gwt.client.drag.DraggerImpl;
import com.fs.uicommons.impl.gwt.client.editor.basic.BooleanEditorImpl;
import com.fs.uicommons.impl.gwt.client.editor.basic.EnumEditorImpl;
import com.fs.uicommons.impl.gwt.client.editor.basic.IntegerEditorImpl;
import com.fs.uicommons.impl.gwt.client.editor.basic.StringEditorImpl;
import com.fs.uicommons.impl.gwt.client.editor.file.ImageFileUrlDataEditorImpl;
import com.fs.uicommons.impl.gwt.client.editor.image.ImageCropEditorImpl;
import com.fs.uicommons.impl.gwt.client.editor.properties.PropertiesEditorImpl;
import com.fs.uicommons.impl.gwt.client.frwk.FrwkControlImpl;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormsView;
import com.fs.uicommons.impl.gwt.client.frwk.header.HeaderView;
import com.fs.uicommons.impl.gwt.client.frwk.login.LoginControlImpl;
import com.fs.uicommons.impl.gwt.client.frwk.login.LoginView;
import com.fs.uicommons.impl.gwt.client.gchat.GChatControlImpl;
import com.fs.uicommons.impl.gwt.client.handler.ClientStartedHandler;
import com.fs.uicommons.impl.gwt.client.handler.EndpointBondHandler;
import com.fs.uicommons.impl.gwt.client.handler.LoginHeaderItemHandler;
import com.fs.uicommons.impl.gwt.client.handler.LogoutHeaderItemHandler;
import com.fs.uicommons.impl.gwt.client.handler.UserLoginHandler;
import com.fs.uicommons.impl.gwt.client.handler.action.AutoLoginHandler;
import com.fs.uicommons.impl.gwt.client.handler.action.GChatJoinAP;
import com.fs.uicommons.impl.gwt.client.handler.action.GChatSendAP;
import com.fs.uicommons.impl.gwt.client.handler.action.LoginSubmitAH;
import com.fs.uicommons.impl.gwt.client.handler.action.LogoutAP;
import com.fs.uicommons.impl.gwt.client.handler.action.SignupAnonymousAH;
import com.fs.uicommons.impl.gwt.client.handler.message.SignupAnonymousMsgHandler;
import com.fs.uicommons.impl.gwt.client.mvc.ControlManagerImpl;
import com.fs.uicommons.impl.gwt.client.widget.bar.BarWidgetImpl;
import com.fs.uicommons.impl.gwt.client.widget.basic.AnchorWImpl;
import com.fs.uicommons.impl.gwt.client.widget.basic.ButtonImpl;
import com.fs.uicommons.impl.gwt.client.widget.basic.DateWImpl;
import com.fs.uicommons.impl.gwt.client.widget.basic.LabelImpl;
import com.fs.uicommons.impl.gwt.client.widget.error.ErrorInfosWidgetImpl;
import com.fs.uicommons.impl.gwt.client.widget.list.ListImpl;
import com.fs.uicommons.impl.gwt.client.widget.menu.MenuWImpl;
import com.fs.uicommons.impl.gwt.client.widget.panel.PanelWImpl;
import com.fs.uicommons.impl.gwt.client.widget.stack.StackWImpl;
import com.fs.uicommons.impl.gwt.client.widget.tab.TabberWImpl;
import com.fs.uicommons.impl.gwt.client.widget.table.TableImpl;
import com.fs.uicommons.impl.gwt.client.widget.wpanel.WindowPanelWImpl;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf.CheckerSupport;
import com.fs.uicore.api.gwt.client.support.WidgetCreaterSupport;

/**
 * @author wu
 * 
 */
public class UiCommonsGPIImpl implements UiCommonsGPI {

	/* */
	@Override
	public void active(ContainerI c) {
		final UiClientI client = c.get(UiClientI.class, true);
		ModelI rootM = client.getRootModel();

		this.activeInstaneOf(c);

		this.activeWidgetCreater(c);
		//
		this.activeActionHandlers(c, client);

		this.activeOtherHandlers(c, client);
		//
		new DraggerImpl().parent(client);

		this.activeMessageHandlers(c, client);
		//

		// controls
		ControlManagerI manager = new ControlManagerImpl();
		manager.parent(client);
		manager.child(new FrwkControlImpl(c, "frwk"));
		manager.child(new GChatControlImpl(c, "gchat"));
		manager.child(new LoginControlImpl(c, "login"));

		// start frwk view.
		FrwkControlI fc = manager.getControl(FrwkControlI.class, true);
		fc.open();
		fc.addHeaderItem(HeaderItems.USER_LOGIN);
		fc.addHeaderItem(HeaderItems.USER_LOGOUT);

	}

	public void activeActionHandlers(ContainerI c, UiClientI client) {
		EventBusI eb = client.getEventBus(true);

		eb.addHandler(Actions.A_LOGIN_SUBMIT, new LoginSubmitAH(c));
		eb.addHandler(Actions.A_LOGIN_LOGOUT, new LogoutAP(c));
		eb.addHandler(Actions.A_LOGIN_ANONYMOUS, new SignupAnonymousAH(c));

		//
		eb.addHandler(Actions.A_GCHAT_JOIN, new GChatJoinAP(c));
		eb.addHandler(Actions.A_GCHAT_SEND, new GChatSendAP(c));

	}

	public void activeMessageHandlers(ContainerI c, UiClientI client) {
		EndPointI ep = client.getEndpoint();
		ep.addHandler(Path.valueOf("/endpoint/message/signup/anonymous/success"),
				new SignupAnonymousMsgHandler());
	}

	public void activeOtherHandlers(ContainerI c, UiClientI client) {
		EventBusI eb = client.getEventBus(true);
		eb.addHandler(EndpointBondEvent.TYPE, new EndpointBondHandler(c));
		eb.addHandler(HeaderItemEvent.TYPE.getAsPath().concat(HeaderItems.USER_LOGIN),
				new LoginHeaderItemHandler(c));
		eb.addHandler(HeaderItemEvent.TYPE.getAsPath().concat(HeaderItems.USER_LOGOUT),
				new LogoutHeaderItemHandler(c));

		eb.addHandler(AfterClientStartEvent.TYPE, new ClientStartedHandler(c));
		eb.addHandler(UserLoginEvent.TYPE, new UserLoginHandler(c));
		eb.addHandler(AutoLoginRequireEvent.TYPE, new AutoLoginHandler(c));
	}

	public void activeWidgetCreater(ContainerI c) {
		WidgetFactoryI wf = c.get(WidgetFactoryI.class, true);
		//
		wf.addCreater(new WidgetCreaterSupport<AnchorWI>(AnchorWI.class) {
			@Override
			public AnchorWI create(String name) {

				return new AnchorWImpl(name);

			}
		});
		//
		wf.addCreater(new WidgetCreaterSupport<ListI>(ListI.class) {
			@Override
			public ListI create(String name) {

				return new ListImpl(name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<PanelWI>(PanelWI.class) {
			@Override
			public PanelWI create(String name) {

				return new PanelWImpl(name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<StackWI>(StackWI.class) {
			@Override
			public StackWI create(String name) {

				return new StackWImpl();

			}
		});

		wf.addCreater(new WidgetCreaterSupport<TabberWI>(TabberWI.class) {
			@Override
			public TabberWI create(String name) {

				return new TabberWImpl(name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<TableI>(TableI.class) {
			@Override
			public TableI create(String name) {

				return new TableImpl(name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<ButtonI>(ButtonI.class) {
			@Override
			public ButtonI create(String name) {

				return new ButtonImpl(name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<LabelI>(LabelI.class) {
			@Override
			public LabelI create(String name) {

				return new LabelImpl(name);

			}
		});
		//
		wf.addCreater(new WidgetCreaterSupport<MenuWI>(MenuWI.class) {
			@Override
			public MenuWI create(String name) {

				return new MenuWImpl(name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<TabberWI>(TabberWI.class) {
			@Override
			public TabberWI create(String name) {

				return new TabberWImpl(name);

			}
		});

		// Editors

		wf.addCreater(new WidgetCreaterSupport<StringEditorI>(StringEditorI.class) {
			@Override
			public StringEditorI create(String name) {

				return new StringEditorImpl(name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<BooleanEditorI>(BooleanEditorI.class) {
			@Override
			public BooleanEditorI create(String name) {

				return new BooleanEditorImpl(name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<IntegerEditorI>(IntegerEditorI.class) {
			@Override
			public IntegerEditorI create(String name) {

				return new IntegerEditorImpl(name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<PropertiesEditorI>(PropertiesEditorI.class) {
			@Override
			public PropertiesEditorI create(String name) {

				return new PropertiesEditorImpl(name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<WindowPanelWI>(WindowPanelWI.class) {
			@Override
			public WindowPanelWI create(String name) {

				return new WindowPanelWImpl(name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<BarWidgetI>(BarWidgetI.class) {
			@Override
			public BarWidgetI create(String name) {

				return new BarWidgetImpl(name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<EnumEditorI>(EnumEditorI.class) {
			@Override
			public EnumEditorI create(String name) {

				return new EnumEditorImpl(name);

			}
		});

		wf.addCreater(new WidgetCreaterSupport<ErrorInfosWidgetI>(ErrorInfosWidgetI.class) {
			@Override
			public ErrorInfosWidgetI create(String name) {

				return new ErrorInfosWidgetImpl(name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<ImageFileUrlDataEditorI>(ImageFileUrlDataEditorI.class) {
			@Override
			public ImageFileUrlDataEditorI create(String name) {

				return new ImageFileUrlDataEditorImpl(name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<ImageCropEditorI>(ImageCropEditorI.class) {
			@Override
			public ImageCropEditorI create(String name) {

				return new ImageCropEditorImpl(name);

			}
		});
		wf.addCreater(new WidgetCreaterSupport<DateWI>(DateWI.class) {
			@Override
			public DateWI create(String name) {

				return new DateWImpl(name);

			}
		});

	}

	public void activeInstaneOf(ContainerI c) {
		InstanceOf.addChecker(new CheckerSupport(AnchorWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof AnchorWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ActionModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ActionModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ButtonI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ButtonI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ControlI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ControlManagerI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ControlManagerI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(BodyModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BodyModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(HeaderModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof HeaderModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(TabberWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof TabberWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(TabWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof TabWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(StackWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof StackWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(PanelWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PanelWI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(ViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ViewI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(WidgetI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WidgetI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(EditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof EditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(PropertiesEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PropertiesEditorI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(BooleanEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BooleanEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(IntegerEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof IntegerEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(MenuWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MenuWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(MenuItemWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MenuItemWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(StringEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof StringEditorI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(WindowPanelWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WindowPanelWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ConsoleModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ConsoleModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(WindowModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof WindowModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(LineModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LineModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(PropertyModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PropertyModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FieldModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FieldModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(AdjusterI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof AdjusterI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FormModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FormModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FormView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FormView;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FormsView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FormsView;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(LoginModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LoginModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(LoginView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LoginView;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(BlankModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BlankModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(LoginControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LoginControlI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(HeaderView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof HeaderView;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(HeaderModelI.ItemModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof HeaderModelI.ItemModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(BarWidgetI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BarWidgetI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(StackWI.ItemModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof StackWI.ItemModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(EnumEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof EnumEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ErrorInfosWidgetI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ErrorInfosWidgetI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ImageFileUrlDataEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ImageFileUrlDataEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ImageCropEditorI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ImageCropEditorI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(DraggerI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof DraggerI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FrwkModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FrwkModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FrwkControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FrwkControlI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(PopupWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PopupWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(DateWI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof DateWI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(GChatControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof GChatControlI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(ChatGroupModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ChatGroupModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(MessageModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof MessageModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(FrwkViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof FrwkViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(BodyViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof BodyViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(HeaderViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof HeaderViewI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(ChatGroupViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ChatGroupViewI;
			}
		});

	}

}
