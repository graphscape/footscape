/**
 * Jun 13, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.widget;

import com.fs.uicommons.api.gwt.client.editor.basic.IntegerEditorI;
import com.fs.uicommons.api.gwt.client.editor.basic.StringEditorI;
import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicommons.api.gwt.client.widget.event.ChangeEvent;
import com.fs.uicommons.api.gwt.client.widget.list.ListI;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.commons.Holder;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.data.basic.IntegerData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.event.ClickEvent;

/**
 * @author wuzhen
 * 
 */
public class WidgetFactoryTest extends TestBase {

	public void testList() {

		ListI ls = wf.create(ListI.class);
		ls.parent(this.root);//

		ButtonI b = wf.create(ButtonI.class);

		ls.addChild(b);

		LabelI lb = wf.create(LabelI.class);
		ls.addChild(lb);

	}

	public void testStringEditor() {
		StringEditorI se = wf.create(StringEditorI.class);
		final Holder<StringData> sdH = new Holder<StringData>();
		se.addHandler(ChangeEvent.TYPE, new HandlerI<ChangeEvent<?>>() {

			@Override
			public void handle(ChangeEvent<?> e) {
				sdH.setTarget((StringData) e.getData());
			}
		});
		StringData sd = StringData.valueOf("this is string value");
		se.input(sd);
		assertEquals(sd, sdH.getTarget());

	}

	public void testIntegerEditor() {
		IntegerEditorI se = wf.create(IntegerEditorI.class);
		final Holder<IntegerData> sdH = new Holder<IntegerData>();
		se.addHandler(ChangeEvent.TYPE, new HandlerI<ChangeEvent<?>>() {

			@Override
			public void handle(ChangeEvent<?> e) {
				sdH.setTarget((IntegerData) e.getData());
			}
		});
		IntegerData sd = IntegerData.valueOf(10);
		se.input(sd);
		assertEquals(sd, sdH.getTarget());

	}

	public void testButton() {
		//
		ButtonI b = wf.create(ButtonI.class);
		final Holder<Boolean> clicked = new Holder<Boolean>();
		clicked.setTarget(false);
		b.addHandler(ClickEvent.TYPE, new HandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				clicked.setTarget(true);
			}
		});
		b.parent(this.root);// NOTE
		b.getElementWrapper().click();
		assertTrue("button click not notified.", clicked.getTarget());

	}
}
