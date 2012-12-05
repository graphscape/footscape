/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 15, 2012
 */
package com.fs.uixmpp.muc.impl.gwt.client;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf.CheckerSupport;
import com.fs.uixmpp.core.api.gwt.client.XmppI;
import com.fs.uixmpp.muc.api.gwt.client.RoomI;
import com.fs.uixmpp.muc.api.gwt.client.RoomManagerI;
import com.fs.uixmpp.muc.api.gwt.client.UiXmppMucGPI;

/**
 * @author wu
 * 
 */
public class UiXmppMucGPIImpl implements UiXmppMucGPI {

	@Override
	public void active(ContainerI c) {
		this.activeInstanceOf(c);

		this.activeRoomManager(c);
	}

	protected void activeRoomManager(ContainerI c) {
		UiClientI uic = c.get(UiClientI.class, true);
		XmppI xc = uic.getChild(XmppI.class, true);//
		RoomManagerI rm = new RoomManagerImpl();
		rm.parent(xc);//

	}

	protected void activeInstanceOf(ContainerI c) {

		InstanceOf.addChecker(new CheckerSupport(RoomManagerI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof RoomManagerI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(RoomI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof RoomI;

			}
		});
	}

}
