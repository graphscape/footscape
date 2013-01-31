/**
 * Jun 25, 2012
 */
package com.fs.uiclient.impl.test.gwt.client;

import com.fs.uiclient.impl.gwt.client.testsupport.ActivityTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.CollectionTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.ExpTestWorker;
import com.fs.uiclient.impl.gwt.client.testsupport.LoginTestWorker;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
import com.fs.uicommons.api.gwt.client.frwk.FrwkControlI;
import com.fs.uicommons.api.gwt.client.frwk.FrwkModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.EventBusI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;

/**
 * @author wuzhen
 * 
 */
public class UiClientTestGPIImpl implements UiClientTestGPI {

	private static final Path HI_EXP = Path.valueOf(new String[] { "testers", "user2:signup-login-exp" });

	private static final Path HI_ACTIVITY = Path.valueOf(new String[] { "testers",
			"user1:signup-login-exp-activities" });

	@Override
	public void active(ContainerI c) {
		final UiClientI client = c.get(UiClientI.class, true);//
		ModelI rootModel = client.getRootModel();
		FrwkControlI fc = client.find(FrwkControlI.class, true);

		fc.addHeaderItem(HI_EXP);
		fc.addHeaderItem(HI_ACTIVITY);

		EventBusI eb = c.getEventBus();

		eb.addHandler(HeaderItemEvent.TYPE, new EventHandlerI<HeaderItemEvent>() {

			@Override
			public void handle(HeaderItemEvent t) {
				UiClientTestGPIImpl.this.onHeaderItemEvent(client, (HeaderItemEvent) t);
			}
		});
	}

	/**
	 * Jan 13, 2013
	 */
	protected void onHeaderItemEvent(UiClientI client, HeaderItemEvent t) {
		Path path = t.getPath();
		if (HI_ACTIVITY.equals(path)) {
			CollectionTestWorker worker = new CollectionTestWorker()//
					.addTestWorker(new LoginTestWorker("user1", "user1@some.com", "user1"))//
					.addTestWorker(new ExpTestWorker(6))//
					.addTestWorker(new ActivityTestWorker())//
			;
			worker.start(client);
		} else if (HI_EXP.equals(path)) {
			CollectionTestWorker worker = new CollectionTestWorker()//
					.addTestWorker(new LoginTestWorker("user2", "user2@other.com", "user2"))//
					.addTestWorker(new ExpTestWorker(6))//

			;
			worker.start(client);
		}
	}
}
