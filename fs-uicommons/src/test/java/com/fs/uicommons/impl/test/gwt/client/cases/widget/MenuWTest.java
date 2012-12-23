/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 3, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.cases.widget;

import com.fs.uicommons.api.gwt.client.widget.menu.MenuItemWI;
import com.fs.uicommons.api.gwt.client.widget.menu.MenuWI;
import com.fs.uicommons.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.api.gwt.client.core.Event;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.efilter.SimpleEventFilter;
import com.fs.uicore.api.gwt.client.event.ClickEvent;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * @author wu
 * 
 */
public class MenuWTest extends TestBase {

	private MenuWI menu0;

	private MenuWI menu1;

	private MenuItemWI menu1_item1;

	private MenuItemWI menu0_item1;

	public void testMenu() {

		menu1 = wf.create(MenuWI.class);

		menu1_item1 = menu1.addItem("menu1-Item1");//

		menu0 = wf.create(MenuWI.class);
		menu0_item1 = menu0.addItem("menu0-Item1");//
		MenuItemWI mi02 = menu0.addItem("menu0-Item2", menu1);// menu1 as the
																// sub menu
		// of menu0

		menu0.parent(this.root);// Attach to root

		// handler to listen the menu item click event.

		EventHandlerI<ClickEvent> eh = new EventHandlerI<ClickEvent>() {

			@Override
			public void handle(ClickEvent e) {
				MenuWTest.this.onClick(e);
			}
		};
		//
		Event.FilterI ef = SimpleEventFilter.valueOf(ClickEvent.TYPE,
				MenuItemWI.class);
		menu0.getEventBus(true).addHandler(ef, eh);// register the handler and
													// filter.
		//
		// assertFalse("menu should not visible at init", menu0.isVisible());
		// assertFalse("menu should not visible at init", menu1.isVisible());

		//
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				MenuWTest.this.start();
			}
		});
		this.delayTestFinish(1000 * 10);

	}

	protected void start() {
		// menu0.setVisible(true);
		// assertFalse("menu 1 should no visible as init",
		// this.menu1.isVisible());
		menu0_item1._select();
		menu0_item1._click();

	}

	protected void onClick(ClickEvent ce) {
		System.out.println(this.getClass() + ":" + ce);// TODO add logger.
		if (menu1_item1 == ce.getSource()) {

		}
		if (menu0_item1 == ce.getSource()) {
			//

			this.finishTest();//
		}
	}

}
