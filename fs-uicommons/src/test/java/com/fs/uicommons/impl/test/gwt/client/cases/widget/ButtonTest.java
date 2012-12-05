/**
 * Jun 13, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.widget;

import com.fs.uicommons.api.gwt.client.widget.basic.ButtonI;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event.HandlerI;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.fs.uicore.api.gwt.client.event.ModelValueEvent;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class ButtonTest extends TestBase {

	public void testButtonWithModel() {

		final ButtonI b = wf.create(ButtonI.class);
		// hope this handler is called after the button's own handler for
		// setting the element innerText.
		b.getModel().addHandler(ModelValueEvent.TYPE, new HandlerI<ModelValueEvent>() {

			@Override
			public void handle(ModelValueEvent e) {
				//
				if (e.getLocation().isDefaultProperty()) {
					String text = b.getElement().getInnerText();

					assertEquals("clickme", text);
					ButtonTest.this.finishTest();
				}

			}
		});
		this.delayTestFinish(timeoutMillis);

		b.getModel().setDefaultValue("clickme");

	}

	public void testButton() {
		//
		ButtonI b = wf.create(ButtonI.class);
		b.addHandler(ClickEvent.TYPE, new HandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				System.out.println("click event listened.");
				ButtonTest.this.finishTest();
			}
		});
		b.parent(this.root);// NOTE
		this.delayTestFinish(this.timeoutMillis);
		b.getElementWrapper().click();//

	}

	public void dump(int depth, Element ele) {
		String s = "";
		for (int i = 0; i < depth; i++) {
			s += " ";
		}
		log(s + "<" + ele.getTagName() + "/>");
		NodeList<Node> nl = ele.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Element eI = (Element) nl.getItem(i);
			dump(depth + 1, eI);
		}
	}

	public void log(String msg) {
		System.out.println(msg);
	}
}
