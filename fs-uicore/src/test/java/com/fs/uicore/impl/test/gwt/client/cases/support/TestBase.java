/**
 * Jul 1, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases.support;

import java.util.HashSet;
import java.util.Set;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.fs.uicore.api.gwt.client.util.ClientLoader;
import com.fs.uicore.impl.test.gwt.client.UiCoreTestGPI;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

/**
 * @author wu
 * 
 */
public abstract class TestBase extends GWTTestCase {

	protected GwtSPI.Factory factory;

	protected UiClientI client;
	protected ContainerI container;

	protected Set<String> finishing;

	/* */
	@Override
	public String getModuleName() {

		return "com.fs.uicore.impl.test.gwt.UiCoreImplTest";

	}

	/* */
	@Override
	protected void gwtSetUp() throws Exception {

		super.gwtSetUp();
		this.finishing = new HashSet<String>();

		GwtSPI[] spis = new GwtSPI[] { GWT.create(UiCoreGwtSPI.class), new UiCoreTestGPI() };

		factory = ClientLoader.getOrLoadClient(spis, new EventHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				TestBase.this.onEvent(e);
			}
		});
		this.container = this.factory.getContainer();
		this.client = this.container.get(UiClientI.class, true);
		this.client.start();//
	}

	protected void onEvent(Event e) {
		System.out.println(this.getClass() + ":" + e);
		if (e instanceof AfterClientStartEvent) {
			this.onClientStart((AfterClientStartEvent) e);
		}
	}

	public void tryFinish(String finish) {
		this.finishing.remove(finish);
		System.out.println("finish:" + finish + ",waiting:" + this.finishing);
		if (this.finishing.isEmpty()) {
			this.finishTest();
		}
	}

	/**
	 * @param e
	 */
	protected void onClientStart(AfterClientStartEvent e) {

	}
}
