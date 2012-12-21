/**
 * Jul 1, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases.support;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ClientStartEvent;
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

	/* */
	@Override
	public String getModuleName() {

		return "com.fs.uicore.impl.test.gwt.UiCoreImplTest";

	}

	/* */
	@Override
	protected void gwtSetUp() throws Exception {

		super.gwtSetUp();

		GwtSPI[] spis = new GwtSPI[] { GWT.create(UiCoreGwtSPI.class),
				new UiCoreTestGPI() };

		factory = ClientLoader.getOrLoadClient(spis, new HandlerI<Event>() {

			@Override
			public void handle(Event e) {
				TestBase.this.onEvent(e);
			}
		});
		this.container = this.factory.getContainer();
		this.client = this.container.get(UiClientI.class, true);
	}

	protected void onEvent(Event e) {
		System.out.println(this.getClass() + ":" + e);
		if (e instanceof ClientStartEvent) {
			this.onClientStart((ClientStartEvent) e);
		}
	}

	/**
	 * @param e
	 */
	protected void onClientStart(ClientStartEvent e) {

	}
}
