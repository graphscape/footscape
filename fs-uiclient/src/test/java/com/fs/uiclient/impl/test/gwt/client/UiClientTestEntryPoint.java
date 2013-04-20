/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 6, 2012
 */
package com.fs.uiclient.impl.test.gwt.client;

import com.fs.uiclient.api.gwt.client.UiClientGwtSPI;
import com.fs.uicommons.api.gwt.client.UiCommonsGPI;
import com.fs.uicommons.impl.gwt.client.frwk.login.AccountsLDW;
import com.fs.uicore.api.gwt.client.ClientLoader;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class UiClientTestEntryPoint implements EntryPoint {
	private ContainerI container;
	private UiClientI client;
	private int totalExp = 10;

	/* */
	@Override
	public void onModuleLoad() {
		//
		// UiLoggerFactory.configure((String) null, UiLoggerI.LEVEL_DEBUG);//
		//
		if ("true".equals(Window.Location.getParameter(UiClientTestConstants.PK_CLEAN_CLIENT_STORAGE))) {
			AccountsLDW accs = AccountsLDW.getInstance();
			accs.invalid();
		}

		//
		GwtSPI[] spis = new GwtSPI[] { (UiCoreGwtSPI) GWT.create(UiCoreGwtSPI.class),
				(UiCommonsGPI) GWT.create(UiCommonsGPI.class),
				(UiClientGwtSPI) GWT.create(UiClientGwtSPI.class),
				(UiClientTestGPI) GWT.create(UiClientTestGPI.class) };
		GwtSPI.Factory sf = ClientLoader.getInstance().getOrLoadClient(spis, new EventHandlerI<Event>() {

			@Override
			public void handle(Event e) {

			}
		});

		this.container = sf.getContainer();
		client = this.container.get(UiClientI.class, true);
		client.start();
		RootI root = client.getRoot();

	}
}
