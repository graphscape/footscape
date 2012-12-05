/**
 * Jun 25, 2012
 */
package com.fs.uiclient.impl.gwt.client;

import com.fs.uiclient.api.gwt.client.UiClientGwtSPI;
import com.fs.uiclient.api.gwt.client.activities.ActivitiesModelI;
import com.fs.uiclient.api.gwt.client.activity.ActivityModelI;
import com.fs.uiclient.api.gwt.client.activity.ParticipantModel;
import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomControlI;
import com.fs.uiclient.api.gwt.client.chatroom.ChatRoomModelI;
import com.fs.uiclient.api.gwt.client.chatroom.PeerModel;
import com.fs.uiclient.api.gwt.client.chatrooms.ChatRoomsControlI;
import com.fs.uiclient.api.gwt.client.chatrooms.ChatRoomsModelI;
import com.fs.uiclient.api.gwt.client.coper.CooperModelI;
import com.fs.uiclient.api.gwt.client.expe.ExpEditControlI;
import com.fs.uiclient.api.gwt.client.expe.ExpEditModelI;
import com.fs.uiclient.api.gwt.client.exps.ExpItemModel;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchControlI;
import com.fs.uiclient.api.gwt.client.exps.ExpSearchModelI;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.main.MainModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListControlI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpListModelI;
import com.fs.uiclient.api.gwt.client.uexp.UserExpModel;
import com.fs.uiclient.impl.gwt.client.exps.item.ExpItemView;
import com.fs.uiclient.impl.gwt.client.filter.LocalActionFilter;
import com.fs.uiclient.impl.gwt.client.filter.SessionFilter;
import com.fs.uiclient.impl.gwt.client.main.MainControl;
import com.fs.uiclient.impl.gwt.client.main.MainModel;
import com.fs.uiclient.impl.gwt.client.signup.SignupControl;
import com.fs.uiclient.impl.gwt.client.tasks.TaskManager;
import com.fs.uiclient.impl.gwt.client.uelist.UserExpListView;
import com.fs.uiclient.impl.gwt.client.uexp.UserExpView;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.Mvc;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
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
		this.activeFilter(c);
		this.activeMainControl(c, client);
		// this.activeTaskManager(c);
	}

	private void activeTaskManager(ContainerI c) {
		UiClientI client = c.get(UiClientI.class, true);

		TaskManager tm = new TaskManager();
		client.child(tm);//

	}

	private void activeFilter(ContainerI c) {
		UiClientI client = c.get(UiClientI.class, true);
		client.addFilter(new LocalActionFilter());
		client.addFilter(new SessionFilter(client));//
	}

	private void activeMainControl(ContainerI c, UiClientI client) {
		//
		//
		ModelI rootModel = client.getRootModel();

		Mvc mvc = new Mvc(new MainModel("main"), null, new MainControl("main"));
		mvc.start(rootModel);//
		// main has no view.

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

		InstanceOf.addChecker(new CheckerSupport(SignupControl.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof SignupControl;

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
		InstanceOf.addChecker(new CheckerSupport(ExpEditModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ExpEditModelI;

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
		InstanceOf.addChecker(new CheckerSupport(CooperModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof CooperModelI;

			}

		});

		InstanceOf.addChecker(new CheckerSupport(ActivityModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ActivityModelI;

			}

		});

		InstanceOf.addChecker(new CheckerSupport(ExpItemView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ExpItemView;

			}

		});

		InstanceOf.addChecker(new CheckerSupport(ActivitiesModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ActivitiesModelI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(
				ActivitiesModelI.ItemModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ActivitiesModelI.ItemModel;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(UserExpView.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof UserExpView;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(ChatRoomModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ChatRoomModelI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(ChatRoomsModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ChatRoomsModelI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(ParticipantModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ParticipantModel;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(PeerModel.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof PeerModel;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(ChatRoomControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ChatRoomControlI;

			}

		});
		InstanceOf.addChecker(new CheckerSupport(ChatRoomsControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof ChatRoomsControlI;

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

	}
}
