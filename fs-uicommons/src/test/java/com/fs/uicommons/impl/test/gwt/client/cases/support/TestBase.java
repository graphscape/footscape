/**
 * Jun 13, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.support;

import java.util.HashSet;
import java.util.Set;

import com.fs.uicommons.api.gwt.client.UiCommonsGPI;
import com.fs.uicommons.api.gwt.client.mvc.ControlManagerI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.SyncHandlerI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.event.AttachedEvent;
import com.fs.uicore.api.gwt.client.event.BeforeClientStartEvent;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
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

	protected ControlManagerI manager;

	protected WidgetFactoryI wf;

	protected RootI root;

	protected int timeoutMillis = 10 * 1000;

	protected boolean disableWebSocket;

	protected Set<String> finishing = new HashSet<String>();

	protected boolean start = true;

	@Override
	public String getModuleName() {

		return "com.fs.uicommons.impl.test.gwt.UiCommonsImplTest";
	}

	/* */
	@Override
	protected void gwtSetUp() throws Exception {

		super.gwtSetUp();

		GwtSPI[] spis = new GwtSPI[] { GWT.create(UiCoreGwtSPI.class), GWT.create(UiCommonsGPI.class) };

		factory = com.fs.uicore.api.gwt.client.ClientLoader.getInstance().getOrLoadClient(spis, new SyncHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				TestBase.this.onEvent(e);
			}
		});

		this.container = this.factory.getContainer();
		this.client = this.container.get(UiClientI.class, true);

		this.wf = this.container.get(WidgetFactoryI.class, true);
		this.root = this.container.get(UiClientI.class, true).getRoot();
		this.manager = this.client.getChild(ControlManagerI.class, true); // see
		// xxxSPI.active
		// this.client.attach();//
		if (this.start) {
			this.client.start();//

		}
	}

	protected void onEvent(Event e) {
		System.out.println(this.getClass().getName() + ":" + e);
		if (e instanceof AfterClientStartEvent) {
			this.onClientStart((AfterClientStartEvent) e);
		} else if (e instanceof BeforeClientStartEvent) {
			this.beforeClientStart((BeforeClientStartEvent) e);
		} else if (e instanceof AttachedEvent) {
			this.onAttachedEvent((AttachedEvent) e);
		}
	}

	/**
	 * Jan 2, 2013
	 */
	protected void onAttachedEvent(AttachedEvent e) {

	}

	protected void beforeClientStart(BeforeClientStartEvent e) {

	}

	protected void onClientStart(AfterClientStartEvent e) {

	}

	/* */
	@Override
	protected void gwtTearDown() throws Exception {

		super.gwtTearDown();

	}

	public void dump() {
		System.out.println(this.root.dump());
		System.out.println(this.client.dump());

	}

	public void tryFinish(String item) {
		this.finishing.remove(item);
		System.out.println(item + " done,remains:" + this.finishing);
		if (this.finishing.isEmpty()) {
			this.finishTest();
		}

	}

}
