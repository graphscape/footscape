/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.frwk;

import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.frwk.commons.FormModel;
import com.fs.uicommons.api.gwt.client.manage.BossControlI;
import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicommons.api.gwt.client.manage.ManagerModelI;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class FormTest extends TestBase {

	private StringData field1Data = StringData.valueOf("field1-data");

	public void testFormView() {
		this.finishing.add("field1");
		this.finishing.add("form");

		BossControlI fc = this.manager.getControl(BossControlI.class, true);
		
		ManagerModelI cm = fc.getManager(BossModelI.M_CENTER);

		System.out.println(this.client.dump());
		System.out.println(this.root.dump());

		FormModel fm = new FormModel("");
		fm.addDefaultValueHandler(new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				FormTest.this.onDataValue(e);
			}
		});

		FormView fv = new FormView(this.container);
		fv.model(fm);

		cm.manage(fm, fv);
		assertTrue("form view should attached.", fv.isAttached());

		this.delayTestFinish(timeoutMillis);
		FieldModel f1m = fm.addField("field1", StringData.class);

		f1m.addDefaultValueHandler(new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				FormTest.this.onField1Value(e);
			}
		});
		;

		EditorI fE = fv.getEditor("field1");
		fE.input(this.field1Data);

	}

	protected void onDataValue(ModelValueEvent e) {
		System.out.println("field1:" + e.getValueWrapper().getValue());

		ObjectPropertiesData d = (ObjectPropertiesData) e.getValueWrapper()
				.getValue();
		assertNotNull("form data is null", d);
		StringData sd = (StringData) d.getProperty("field1");
		assertNotNull("field1 is null", sd);
		assertEquals("", this.field1Data, sd);

		this.tryFinish("form");
	}

	protected void onField1Value(ModelValueEvent e) {
		System.out.println("field1:" + e.getValueWrapper().getValue());

		assertEquals("field1 data error", this.field1Data, e.getValueWrapper()
				.getValue());
		this.tryFinish("field1");
	}

}
