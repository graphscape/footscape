/**
 * Jun 12, 2012
 */
package com.fs.integrated.test.gwt.client;

import com.fs.uiclient.api.gwt.client.UiClientGwtSPI;
import com.fs.uicommons.api.gwt.client.UiCommonsGPI;
import com.fs.uicore.api.gwt.client.ClientLoader;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
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

	protected WidgetFactoryI wf;

	protected RootI root;

	/* */
	@Override
	public String getModuleName() {
		return "com.fs.integrated.test.gwt.IntegratedTest";
	}

	/*
	
	 */
	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		GwtSPI[] spis = new GwtSPI[] { GWT.create(UiCoreGwtSPI.class),
				GWT.create(UiCommonsGPI.class),
				GWT.create(UiClientGwtSPI.class) };

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

	}

	public void onEvent(Event e) {

	}

	/*
	
	 */
	@Override
	protected void gwtTearDown() throws Exception {
		super.gwtTearDown();

	}
}
