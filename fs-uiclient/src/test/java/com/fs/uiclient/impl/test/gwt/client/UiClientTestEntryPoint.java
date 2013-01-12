/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 6, 2012
 */
package com.fs.uiclient.impl.test.gwt.client;

import com.fs.uiclient.api.gwt.client.UiClientGwtSPI;
import com.fs.uiclient.impl.gwt.client.testsupport.ActivityTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.TestWorker;
import com.fs.uicommons.api.gwt.client.UiCommonsGPI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.fs.uicore.api.gwt.client.util.ClientLoader;
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

		TestWorker.beforeTesting();
		final ActivityTestWorker worker = new ActivityTestWorker("user1", "user1@some.com", "user1", 3);

		GwtSPI[] spis = new GwtSPI[] { (UiCoreGwtSPI) GWT.create(UiCoreGwtSPI.class),
				(UiCommonsGPI) GWT.create(UiCommonsGPI.class),
				(UiClientGwtSPI) GWT.create(UiClientGwtSPI.class) };
		GwtSPI.Factory sf = ClientLoader.getOrLoadClient(spis, new EventHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				worker.onEvent(e);
			}
		});

		this.container = sf.getContainer();
		client = this.container.get(UiClientI.class, true);
		client.start();
		RootI root = client.getRoot();

	}
}
