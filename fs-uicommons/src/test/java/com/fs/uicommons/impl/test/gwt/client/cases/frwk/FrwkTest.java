/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.frwk;

import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI;
import com.fs.uicommons.api.gwt.client.frwk.HeaderModelI.ItemModel;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.widget.basic.AnchorWI;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class FrwkTest extends TestBase {

	public void testHeaderAddMenuAction() {

		HeaderModelI hc = this.framework.getHeader();

		ItemModel i1 = hc.addItem("menu1", HeaderModelI.ItemModel.P_LEFT);
		final ItemModel i11 = i1.addItem("item1");
		i11.addHandler(ModelValueEvent.TYPE, new HandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				FrwkTest.this.onItemClick(i11, e);

			}
		});
		System.out.println(this.client.dump());
		System.out.println(this.root.dump());
		AnchorWI w11 = i11.getWidget(AnchorWI.class, true);
		assertTrue("widget should be attached before use it", w11.isAttached());
		this.delayTestFinish(timeoutMillis);
		w11.click();//
	}

	protected void onItemClick(ItemModel im, ModelValueEvent e) {
		System.out.println("clicked:" + im);
		this.finishTest();
	}
}
