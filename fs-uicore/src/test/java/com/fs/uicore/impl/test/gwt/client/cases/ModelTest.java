/**
 * Jul 19, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.RootI;
import com.fs.uicore.api.gwt.client.WidgetFactoryI;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.efilter.ModelValueEventFilter;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.fs.uicore.api.gwt.client.support.SimpleModel;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.impl.test.gwt.client.widget.TestWI;

/**
 * @author wu
 * 
 */
public class ModelTest extends TestBase {

	protected ModelValueEvent[] events = new ModelValueEvent[2];

	public void testUiWidgetAndModel() {
		ModelI root = this.container.get(ModelI.class, true);

		root.addHandler(new ModelValueEventFilter(Location.valueOf("string1")),
				new HandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ModelTest.this.onEventBeforeSetValue(e);
					}
				});
		root.addHandler(new ModelValueEventFilter(Location.valueOf("list2")),
				new HandlerI<ModelValueEvent>() {

					@Override
					public void handle(ModelValueEvent e) {
						ModelTest.this.onEventBeforeSetValue(e);
					}
				});

		this.delayTestFinish(100000);

		root.setValue("string1", "test");
		root.setValue("list2", -1, "append");

	}

	protected void onEventBeforeSetValue(ModelValueEvent e) {

		if (e.getLocation().isProperty("string1")) {
			assertEquals(e.getValueWrapper().getValue(), "test");
			this.events[0] = e;
		} else if (e.getLocation().isProperty("list2")) {
			assertEquals(new Integer(0), e.getLocation().getIndex() != null);
			assertTrue("shoud be last", e.getLocation().isLast());
			this.events[1] = e;
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

	protected void other() {
		RootI root = this.container.get(RootI.class, true);

		SimpleModel testModel = SimpleModel.valueOf("test", "yes");

		TestWI tw = this.container.get(WidgetFactoryI.class, true).create(
				TestWI.class, testModel);
		tw.parent(root);
	}
}
