/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 6, 2012
 */
package com.fs.uiclient.impl.test.gwt.client;

import com.fs.uiclient.api.gwt.client.UiClientGwtSPI;
import com.fs.uicommons.api.gwt.client.UiCommonsGPI;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.commons.Holder;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.fs.uicore.api.gwt.client.util.ClientLoader;
import com.fs.uixmpp.core.api.gwt.client.UiXmppCoreGPI;
import com.fs.uixmpp.groupchat.api.gwt.client.UiGroupChatGPI;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * @author wu
 * 
 */
public class UiClientTestEntryPoint implements EntryPoint {
	private ContainerI container;
	private UiClientI client;

	/* */
	@Override
	public void onModuleLoad() {
		//
		// UiLoggerFactory.configure((String) null, UiLoggerI.LEVEL_DEBUG);//

		GwtSPI[] spis = new GwtSPI[] {
				(UiCoreGwtSPI) GWT.create(UiCoreGwtSPI.class),
				(UiCommonsGPI) GWT.create(UiCommonsGPI.class),
				(UiXmppCoreGPI) GWT.create(UiXmppCoreGPI.class),
				(UiGroupChatGPI) GWT.create(UiGroupChatGPI.class),
				(UiClientGwtSPI) GWT.create(UiClientGwtSPI.class) };
		GwtSPI.Factory sf = ClientLoader.getOrLoadClient(spis,
				new HandlerI<Event>() {

					@Override
					public void handle(Event e) {
						// TODO
					}
				});

		this.container = sf.getContainer();
		client = this.container.get(UiClientI.class, true);

		RootI root = client.getRoot();

	}
}
