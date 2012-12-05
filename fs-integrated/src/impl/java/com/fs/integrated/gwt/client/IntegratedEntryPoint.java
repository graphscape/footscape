/**
 * Jul 14, 2012
 */
package com.fs.integrated.gwt.client;

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
public class IntegratedEntryPoint implements EntryPoint {
	private ContainerI container;
	private UiClientI client;

	/* */
	@Override
	public void onModuleLoad() {
		//
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
		final ButtonI bt = this.create();
		final Holder<Integer> ih = new Holder<Integer>();
		ih.setTarget(0);

		bt.addHandler(ClickEvent.TYPE, new HandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				ih.setTarget(ih.getTarget() + 1);
				bt.getElement().setInnerText("clicked" + ih.getTarget());
			}
		});
		bt.parent(root);// TODO ,?test

	}

	private ButtonI create() {

		ButtonI rt = this.container.get(WidgetFactoryI.class, true).create(
				ButtonI.class);

		rt.getModel().setDefaultValue("ButtonX");
		return rt;
	}
}
