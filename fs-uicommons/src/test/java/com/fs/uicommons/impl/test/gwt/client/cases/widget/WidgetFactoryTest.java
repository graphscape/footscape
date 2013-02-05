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
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
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
		final Holder<String> sdH = new Holder<String>();
		se.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

			@Override
			public void handle(ChangeEvent e) {
				sdH.setTarget((String) e.getData());
			}
		});
		String sd = ("this is string value");
		se.input(sd);
		assertEquals(sd, sdH.getTarget());

	}

	public void testIntegerEditor() {
		IntegerEditorI se = wf.create(IntegerEditorI.class);
		final Holder<Integer> sdH = new Holder<Integer>();
		se.addHandler(ChangeEvent.TYPE, new EventHandlerI<ChangeEvent>() {

			@Override
			public void handle(ChangeEvent e) {
				sdH.setTarget((Integer) e.getData());
			}
		});
		Integer sd = Integer.valueOf(10);
		se.input(sd);
		assertEquals(sd, sdH.getTarget());

	}

	public void testButton() {
		//
		ButtonI b = wf.create(ButtonI.class);
		final Holder<Boolean> clicked = new Holder<Boolean>();
		clicked.setTarget(false);
		b.addHandler(ClickEvent.TYPE, new EventHandlerI<ClickEvent>() {

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
