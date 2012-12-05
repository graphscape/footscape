/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 27, 2012
 */
package com.fs.uixmpp.groupchat.impl.gwt.client;

import com.fs.uicommons.api.gwt.client.frwk.support.LazyMvcHeaderItemHandler;
import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicommons.api.gwt.client.mvc.LazyMvcI;
import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicommons.api.gwt.client.mvc.support.LazyMvcSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf.CheckerSupport;
import com.fs.uixmpp.core.api.gwt.client.XmppModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.UiGroupChatGPI;
import com.fs.uixmpp.groupchat.api.gwt.client.room.MessageModel;
import com.fs.uixmpp.groupchat.api.gwt.client.room.OccupantModel;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomControlI;
import com.fs.uixmpp.groupchat.api.gwt.client.room.RoomModelI;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsControlI;
import com.fs.uixmpp.groupchat.api.gwt.client.rooms.RoomsModelI;
import com.fs.uixmpp.groupchat.impl.gwt.client.room.RoomView;
import com.fs.uixmpp.groupchat.impl.gwt.client.rooms.RoomsControl;
import com.fs.uixmpp.groupchat.impl.gwt.client.rooms.RoomsModel;
import com.fs.uixmpp.groupchat.impl.gwt.client.rooms.RoomsView;

/**
 * @author wu
 * 
 */
public class UiGroupChatGPIImpl implements UiGroupChatGPI {

	@Override
	public void active(ContainerI c) {
		this.activeInstanceof();

		UiClientI client = c.get(UiClientI.class, true);
		ModelI rootM = c.get(ModelI.class, true);// TODO move to client

		final XmppModelI xmpp = client.getRootModel().find(XmppModelI.class,
				true); // Model
		// TODO does view is mandetory for using by chatrooms in uiclient.
		LazyMvcI mvc = new LazyMvcSupport(rootM, "rooms") {

			@Override
			protected ModelI createModel(String name) {
				//
				return new RoomsModel(name, xmpp);
			}

			@Override
			protected ViewI createView(String name, ContainerI c) {
				//
				return new RoomsView(name, c);
			}

			@Override
			protected ControlI createControl(String name) {
				//
				return new RoomsControl(name);
			}
		};

		rootM.addLazy(mvc.getName(), mvc);

		// new LazyMvcHeaderItemHandler(mvc).start(rootM);
	}

	public void activeInstanceof() {
		InstanceOf.addChecker(new CheckerSupport(RoomsControlI.class) {

			@Override
			public boolean isInstance(Object o) {
				// TODO Auto-generated method stub
				return o instanceof RoomsControlI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(RoomsView.class) {

			@Override
			public boolean isInstance(Object o) {
				// TODO Auto-generated method stub
				return o instanceof RoomsView;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(RoomControlI.class) {

			@Override
			public boolean isInstance(Object o) {
				// TODO Auto-generated method stub
				return o instanceof RoomControlI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(RoomView.class) {

			@Override
			public boolean isInstance(Object o) {
				// TODO Auto-generated method stub
				return o instanceof RoomView;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(RoomModelI.class) {

			@Override
			public boolean isInstance(Object o) {
				// TODO Auto-generated method stub
				return o instanceof RoomModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(RoomsModelI.class) {

			@Override
			public boolean isInstance(Object o) {
				// TODO Auto-generated method stub
				return o instanceof RoomsModelI;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(OccupantModel.class) {

			@Override
			public boolean isInstance(Object o) {
				// TODO Auto-generated method stub
				return o instanceof OccupantModel;
			}
		});
		InstanceOf.addChecker(new CheckerSupport(MessageModel.class) {

			@Override
			public boolean isInstance(Object o) {
				// TODO Auto-generated method stub
				return o instanceof MessageModel;
			}
		});
	}

}
