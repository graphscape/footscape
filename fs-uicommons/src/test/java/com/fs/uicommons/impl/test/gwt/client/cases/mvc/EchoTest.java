/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.mvc;

import com.fs.uicommons.api.gwt.client.event.ActionEvent;
import com.fs.uicommons.api.gwt.client.frwk.BodyModelI;
import com.fs.uicommons.api.gwt.client.frwk.commons.FieldModel;
import com.fs.uicommons.api.gwt.client.widget.EditorI;
import com.fs.uicommons.impl.gwt.client.frwk.commons.form.FormView;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicommons.impl.test.gwt.client.echo.EchoModel;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;

/**
 * @author wu
 * 
 */
public class EchoTest extends TestBase {
	private String field1Data = ("field1-data");

	public void testFormView() {
		this.finishing.add("field1");
		this.finishing.add("form");
		this.finishing.add("action");
		this.finishing.add("response");

		BodyModelI cm = this.rootModel.find(BodyModelI.class, true);

		System.out.println(this.client.dump());
		System.out.println(this.root.dump());

		// model

		EchoModel fm = new EchoModel("");// is a form model
		// listen the form data
		fm.addDefaultValueHandler(new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				EchoTest.this.onDataValue(e);
			}
		});

		//EchoControl control = new EchoControl("echo");
		
		//control.parent(this.manager);// for attach
		// listen to the control event

		// view
		FormView view = new FormView(this.container);
		view.model(fm);

		cm.manage(fm, view);

		assertTrue("form view should attached.", view.isAttached());

		this.delayTestFinish(timeoutMillis);
		FieldModel f1m = fm.getDefaultForm().addField("field1", String.class);

		f1m.addDefaultValueHandler(new EventHandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				EchoTest.this.onField1Value(e);
			}
		});
		;

		EditorI fE = view.getEditor("field1");
		fE.input(this.field1Data);

		// view.clickAction("echo");// cause action

	}

	protected void onActionEvent(ActionEvent e) {

	}

	// form data
	protected void onDataValue(ModelValueEvent e) {
		System.out.println("field1:" + e.getValueWrapper().getValue());

		ObjectPropertiesData d = (ObjectPropertiesData) e.getValueWrapper().getValue();
		assertNotNull("form data is null", d);
		String sd = (String) d.getProperty("field1");
		assertNotNull("field1 is null", sd);
		assertEquals("", this.field1Data, sd);

		this.tryFinish("form");
	}

	// field value
	protected void onField1Value(ModelValueEvent e) {
		System.out.println("field1:" + e.getValueWrapper().getValue());

		assertEquals("field1 data error", this.field1Data, e.getValueWrapper().getValue());
		this.tryFinish("field1");
	}

}
