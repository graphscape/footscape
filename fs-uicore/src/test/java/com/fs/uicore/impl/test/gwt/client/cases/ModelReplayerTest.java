/**
 * Jul 19, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.efilter.ModelValueEventFilter;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class ModelReplayerTest extends TestBase {

	protected ModelValueEvent[] events = new ModelValueEvent[4];

	public void testUiWidgetAndModel() {
		ModelI root = this.container.get(ModelI.class, true);
		this.delayTestFinish(100 * 1000);

		root.setValue("string1", "test");
		root.setValue("list2", -1, "list2-0");

		String key = "001";

		root.addHandler(new ModelValueEventFilter(Location.valueOf("string1"),
				key), new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				ModelReplayerTest.this.onEventBeforeSetValue(e);
			}
		});

		root.createReplayer(key).replay();
		
		
		root.addHandler(new ModelValueEventFilter(Location.valueOf("list2"),
				key), new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				ModelReplayerTest.this.onEventBeforeSetValue(e);
			}
		});

		root.createReplayer(key).replay();//replay for the handlers added before.

		root.addHandler(new ModelValueEventFilter(Location.valueOf("list3")),
				new EventHandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ModelReplayerTest.this.onEventBeforeSetValue(e);
					}
				});

		root.setValue("list3", -1, "list3-0");
		root.setValue("list3", -1, "list3-1");
	}

	protected void onEventBeforeSetValue(ModelValueEvent e) {

		if (e.getLocation().isProperty("string1")) {
			assertEquals(e.getValueWrapper().getValue(), "test");
			assertTrue("should be event in replay:", e.isReplay());
			this.events[0] = e;
		} else if (e.getLocation().isProperty("list2")) {
			assertEquals(new Integer(0), e.getLocation().getIndex());
			assertTrue("should be event in replay:", e.isReplay());

			this.events[1] = e;
		} else if (e.getLocation().isProperty("list3")) {
			if (e.getLocation().getIndex() == 0) {
				assertFalse("should not be in replay:", e.isReplay());

				this.events[2] = e;
			} else if (e.getLocation().getIndex() == 1) {
				assertFalse("should not be in replay:", e.isReplay());
				this.events[3] = e;
			} else {
				assertTrue("index error:" + e.getLocation().getIndex()
						+ ",should be 0,1", false);
			}

		}
		this.tryFinish();
	}

	public void tryFinish() {
		for (int i = 0; i < this.events.length; i++) {
			if (this.events[i] == null) {
				return;
			}
		}
		this.finishTest();
	}

}
