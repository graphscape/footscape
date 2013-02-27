/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.frwk;

import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class FormTest extends TestBase {

	private String field1Data = ("field1-data");

	public void testFormView() {
		this.finishing.add("field1");
		this.finishing.add("form");


		System.out.println(this.client.dump());
		System.out.println(this.root.dump());

	
		FormView fv = new FormView(this.container, "testform");
		//TODO add to body
		assertTrue("form view should attached.", fv.isAttached());

		this.delayTestFinish(timeoutMillis);
		FieldModel f1m = fv.addField("field1", String.class);

		f1m.addValueHandler(new HandlerI<Object>() {

			@Override
			public void handle(Object v) {
				FormTest.this.onField1Value(v);
			}
		});
		;

		EditorI fE = fv.getEditor("field1");
		fE.input(this.field1Data);

	}

	protected void onDataValue(ModelValueEvent e) {
		System.out.println("field1:" + e.getValueWrapper().getValue());

		ObjectPropertiesData d = (ObjectPropertiesData) e.getValueWrapper().getValue();
		assertNotNull("form data is null", d);
		String sd = (String) d.getProperty("field1");
		assertNotNull("field1 is null", sd);
		assertEquals("", this.field1Data, sd);

		this.tryFinish("form");
	}

	protected void onField1Value(Object v) {
		System.out.println("field1:" + v);

		assertEquals("field1 data error", this.field1Data, v);
		this.tryFinish("field1");
	}

}
