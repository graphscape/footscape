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
import com.fs.uicore.api.gwt.client.commons.Holder;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;

/**
 * @author wu
 * 
 */
public class PropertiesEditorTest extends TestBase {

	public void testPropertiesEditor() {

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
		edt.parent(this.root);//attach
		
		assertTrue("should be attached.",edt.isAttached());
		final Holder<ObjectPropertiesData> holder = new Holder<ObjectPropertiesData>();

		edt.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent<?>>() {

			@Override
			public void handle(ChangeEvent<?> e) {
				holder.setTarget((ObjectPropertiesData) e.getData());

			}
		});
		System.out.println(edt.dump());

		// integer property
		Integer expected1 = Integer.valueOf(1);

		IntegerEditorI ie = edt.find(IntegerEditorI.class, false);
		assertNotNull("no editor found.", ie);
		ie.input(expected1);//
		ObjectPropertiesData data = holder.getTarget();
		Object actual = data.getProperty("integer1", false);
		assertNotNull(actual);
		assertEquals(expected1, actual);
		// boolean property
		Boolean expected2 = (true);
		BooleanEditorI e2 = edt.find(BooleanEditorI.class, true);
		e2.input(expected2);
		actual = data.getProperty("boolean1", true);
		assertEquals(expected2, actual);

	}
}
