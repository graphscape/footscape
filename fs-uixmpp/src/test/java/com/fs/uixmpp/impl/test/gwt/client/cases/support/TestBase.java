/**
 * Jun 13, 2012
 */
package com.fs.uixmpp.impl.test.gwt.client.cases.support;

import java.util.HashSet;
import java.util.Set;

import com.fs.uicommons.api.gwt.client.UiCommonsGPI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.fs.uicore.api.gwt.client.util.ClientLoader;
import com.fs.uixmpp.core.api.gwt.client.UiXmppCoreGPI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;
import com.fs.uixmpp.groupchat.api.gwt.client.UiGroupChatGPI;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author wuzhen
 * 
 */
public class TestBase extends GWTTestCase {

	protected GwtSPI.Factory factory;

	protected UiClientI client;
	protected ContainerI container;
	protected XmppControlI xclient;

	protected RootI root;

	protected ControlManagerI manager;

	protected ModelI rootModel;

	protected Set<String> finishing = new HashSet<String>();

	/*
	
	 */
	@Override
	public String getModuleName() {

		return "com.fs.uixmpp.impl.test.gwt.UiXmppImplTest";
	}

	/* */
	@Override
	protected void gwtSetUp() throws Exception {

		super.gwtSetUp();

		GwtSPI[] spis = new GwtSPI[] { //
		GWT.create(UiCoreGwtSPI.class), //
				GWT.create(UiCommonsGPI.class), //
				GWT.create(UiXmppCoreGPI.class),//
				GWT.create(UiGroupChatGPI.class),//

		};
		factory = ClientLoader.getOrLoadClient(spis, new HandlerI<Event>() {

			@Override
			public void handle(Event e) {
				TestBase.this.onEvent(e);
			}
		});
		this.container = this.factory.getContainer();
		this.client = this.container.get(UiClientI.class, true);
		this.xclient = this.client.getChild(XmppControlI.class, true);
		this.root = this.container.get(UiClientI.class, true).getRoot();
		this.rootModel = this.client.getRootModel();//
	}

	public void dump() {
		System.out.println(this.rootModel.dump());
		System.out.println(this.root.dump());
		System.out.println(this.client.dump());

	}

	protected void onEvent(Event e) {
		System.out.println("event received in TestBase.onEvent:" + e);
	}

	protected void tryFinish(String item) {
		this.finishing.remove(item);
		System.out.println("finished:" + item + ",waiting:" + this.finishing);
		if (this.finishing.isEmpty()) {
			this.finishTest();
		}
	}
}
