/**
 * Jun 25, 2012
 */
package com.fs.uiclient.impl.test.gwt.client;

import com.fs.uiclient.impl.gwt.client.testsupport.CollectionTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.ExpTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.LoginTestWorker;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.AfterClientStartEvent;

/**
 * @author wuzhen
 * 
 */
public class UiClientTestGPIImpl implements UiClientTestGPI {

	private static final Path HI_EXP = Path.valueOf(new String[] { "testers", "user1@some.com" });

	@Override
	public void active(final ContainerI c) {
		final UiClientI client = c.get(UiClientI.class, true);//

		//
		EventBusI eb = c.getEventBus();

		eb.addHandler(HeaderItemEvent.TYPE.getAsPath().concat(HI_EXP), new EventHandlerI<HeaderItemEvent>() {

			@Override
			public void handle(HeaderItemEvent t) {
				CollectionTestWorker worker = new CollectionTestWorker()//
						.addTestWorker(new LoginTestWorker("user1", "user1@some.com", "user1"))//
						.addTestWorker(new ExpTestWorker(6))//

				;
				worker.start(client);
			}
		});

		eb.addHandler(AfterClientStartEvent.TYPE, new EventHandlerI<AfterClientStartEvent>() {

			@Override
			public void handle(AfterClientStartEvent t) {
				UiClientTestGPIImpl.this.onClientStart(c);
			}
		});
	}

	public void onClientStart(ContainerI c) {
		final UiClientI client = c.get(UiClientI.class, true);//
		FrwkControlI fc = client.find(FrwkControlI.class, true);

		fc.addHeaderItem(HI_EXP);

	}

}
