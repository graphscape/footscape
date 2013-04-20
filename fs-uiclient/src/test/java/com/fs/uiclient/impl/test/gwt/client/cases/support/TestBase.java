/**
 * Jun 12, 2012
 */
package com.fs.uiclient.impl.test.gwt.client.cases.support;

import java.util.HashSet;
import java.util.Set;

import com.fs.uiclient.api.gwt.client.UiClientGwtSPI;
import com.fs.uiclient.api.gwt.client.event.FailureMessageEvent;
import com.fs.uiclient.api.gwt.client.event.SuccessMessageEvent;
import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.UiCommonsGPI;
import com.fs.uicommons.api.gwt.client.event.UserLoginEvent;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicore.api.gwt.client.ClientLoader;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.fs.uicore.api.gwt.client.event.EndpointBondEvent;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author wuzhen
 * 
 */
public class TestBase extends GWTTestCase {
	public static final int timeoutMillis = 1000000;

	protected GwtSPI.Factory factory;

	protected UiClientI client;

	protected ContainerI container;

	protected WidgetFactoryI wf;

	protected RootI root;

	protected ControlManagerI manager;

	protected MainControlI mcontrol;

	protected Set<String> finishing = new HashSet<String>();

	/* */
	@Override
	public String getModuleName() {
		return "com.fs.uiclient.impl.test.gwt.UiClientImplTest";
	}

	public void dump() {
		System.out.println(this.root.dump());
		System.out.println(this.client.dump());

	}

	/*
	
	 */
	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		AccountsLDW accs = AccountsLDW.getInstance();
		accs.invalid();

		GwtSPI[] spis = new GwtSPI[] { GWT.create(UiCoreGwtSPI.class), GWT.create(UiCommonsGPI.class),
				GWT.create(UiClientGwtSPI.class), };

		factory = ClientLoader.getInstance().getOrLoadClient(spis, new EventHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				TestBase.this.onEvent(e);
			}
		});
		this.container = this.factory.getContainer();
		this.client = this.container.get(UiClientI.class, true);

		this.wf = this.container.get(WidgetFactoryI.class, true);
		this.root = this.container.get(UiClientI.class, true).getRoot();
		this.manager = this.client.getChild(ControlManagerI.class, true);
		this.dump();
		this.client.start();//
		this.mcontrol = this.manager.getControl(MainControlI.class, true);
	}

	public void onEvent(Event e) {
		System.out.println("TestBase.onEvent:" + e);

		if (e instanceof AttachedEvent) {
			AttachedEvent ae = (AttachedEvent) e;
			this.onAttachedEvent(ae);
		} else if (e instanceof EndpointBondEvent) {
			this.onBondEvent((EndpointBondEvent) e);
		} else if (e instanceof SuccessMessageEvent) {
			this.onSuccessMessageEvent((SuccessMessageEvent) e);
		} else if (e instanceof FailureMessageEvent) {
			this.onFailureMessageEvent((FailureMessageEvent) e);
		} else if (e instanceof UserLoginEvent) {
			UserLoginEvent le = (UserLoginEvent) e;
			if (le.getUserInfo().isAnonymous()) {
				this.onAnonymousUserLogin(le);
			}
		}

	}

	/**
	 * Jan 13, 2013
	 * 
	 */
	protected void onAnonymousUserLogin(UserLoginEvent le) {
		//

	}

	/**
	 * Jan 3, 2013
	 */
	protected void onFailureMessageEvent(FailureMessageEvent e) {
		//
		System.err.println("failure message " + e.getMessage());
	}

	/**
	 * Jan 3, 2013
	 */
	protected void onSuccessMessageEvent(SuccessMessageEvent e) {
		//

	}

	/**
	 * Jan 3, 2013
	 */
	protected void onBondEvent(EndpointBondEvent e) {

	}

	/**
	 * @param ae
	 */
	protected void onAttachedEvent(AttachedEvent ae) {

	}

	/*
	
	 */
	@Override
	protected void gwtTearDown() throws Exception {
		super.gwtTearDown();

	}

	protected void tryFinish(String item) {
		this.finishing.remove(item);
		System.out.println("finished:" + item + ",waiting:" + this.finishing);
		if (this.finishing.isEmpty()) {
			this.finishTest();
		}
	}
}
