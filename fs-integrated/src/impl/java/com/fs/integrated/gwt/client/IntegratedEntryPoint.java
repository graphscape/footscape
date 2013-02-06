/**
 * Jul 14, 2012
 */
package com.fs.integrated.gwt.client;

import com.fs.uiclient.api.gwt.client.UiClientGwtSPI;
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
public class IntegratedEntryPoint implements EntryPoint {
	private ContainerI container;
	private UiClientI client;

	/* */
	@Override
	public void onModuleLoad() {
		//
		GwtSPI[] spis = new GwtSPI[] { (UiCoreGwtSPI) GWT.create(UiCoreGwtSPI.class),
				(UiCommonsGPI) GWT.create(UiCommonsGPI.class),
				(UiClientGwtSPI) GWT.create(UiClientGwtSPI.class) };
		GwtSPI.Factory sf = ClientLoader.getOrLoadClient(spis, new EventHandlerI<Event>() {

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
