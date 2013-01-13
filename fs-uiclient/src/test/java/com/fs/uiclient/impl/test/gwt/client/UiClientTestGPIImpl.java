/**
 * Jun 25, 2012
 */
package com.fs.uiclient.impl.test.gwt.client;

import com.fs.uiclient.api.gwt.client.UiClientGwtSPI;
import com.fs.uiclient.impl.gwt.client.testsupport.ActivityTestWorker;
import com.fs.uicommons.api.gwt.client.event.HeaderItemEvent;
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
public class UiClientTestGPIImpl implements UiClientGwtSPI {

	private static final Path HI_ACTIVITY = Path.valueOf(new String[] { "testers", "activities-worker" });

	@Override
	public void active(ContainerI c) {
		final UiClientI client = c.get(UiClientI.class, true);//
		ModelI rootModel = client.getRootModel();
		FrwkModelI fc = rootModel.getChild(FrwkModelI.class, true);
		HeaderModelI hm = fc.getHeader();
		HeaderModelI.ItemModel him = hm.addItem(HI_ACTIVITY.getNameList().toArray(new String[] {}),
				HeaderModelI.ItemModel.P_RIGHT);
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
			new ActivityTestWorker("user1", "user1@some.com", "user1", 6).start(client);
		}
	}

}
