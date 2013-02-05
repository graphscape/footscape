/**
 * Jul 1, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.widget;

import com.fs.uicommons.api.gwt.client.editor.basic.BooleanEditorI;
import com.fs.uicommons.api.gwt.client.editor.basic.IntegerEditorI;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.editor.properties.PropertiesEditorI;
import com.fs.uicommons.api.gwt.client.widget.event.ChangeEvent;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class PropertiesEditorTest extends TestBase {

	public PropertiesEditorTest() {
		this.start = false;
	}

	public void testPropertiesEditor() {

		this.finishing.add("changed");

		ObjectPropertiesData dt = new ObjectPropertiesData();
		dt.setProperty("integer1", null);
		dt.setProperty("string1", null);
		dt.setProperty("string2", null);
		dt.setProperty("boolean1", null);//
		//

		PropertiesEditorI edt = wf.create(PropertiesEditorI.class);//
		edt.addFieldModel("integer1", IntegerEditorI.class);
		edt.addFieldModel("string1", StringEditorI.class);
		edt.addFieldModel("string2", StringEditorI.class);
		edt.addFieldModel("boolean1", BooleanEditorI.class);
		edt.parent(this.root);// attach

		assertTrue("should be attached.", edt.isAttached());

		final Integer expect = Integer.valueOf(1);
		edt.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

			@Override
			public void handle(ChangeEvent e) {
				PropertiesEditorTest.this.onChange(expect, e);

			}
		});

		this.delayTestFinish(this.timeoutMillis);
		IntegerEditorI ie = edt.find(IntegerEditorI.class, false);
		ie.input(expect);//
	}

	public void onChange(Integer expect, ChangeEvent e) {

		// integer property

		ObjectPropertiesData data = e.getData();
		Object actual = data.getProperty("integer1", false);
		assertNotNull(actual);
		assertEquals(expect, actual);
		this.tryFinish("changed");
	}
}
