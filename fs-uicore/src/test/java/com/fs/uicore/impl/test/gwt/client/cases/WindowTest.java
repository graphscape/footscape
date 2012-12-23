/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicore.impl.test.gwt.client.cases;

import com.fs.uicore.api.gwt.client.WindowI;
import com.fs.uicore.api.gwt.client.commons.Size;
import com.fs.uicore.api.gwt.client.core.Event.EventHandlerI;
import com.fs.uicore.api.gwt.client.event.SizeChangeEvent;
import com.fs.uicore.impl.test.gwt.client.cases.support.TestBase;
import com.fs.uicore.impl.test.gwt.client.helper.WindowResizeHelper;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * @author wu see <code>
 *         http://code.google.com/p/google-web-toolkit/source/browse/trunk/user/test/com/google/gwt/user/client/WindowTest.java?r=10807
 *         </code>
 */
public class WindowTest extends TestBase {
	/**
	 * TODO investigate
	 * 
	 * The resize not work,may be work in real env.
	 */

	public void _testResize() {

		final WindowI w = this.container.get(WindowI.class, true);
		w.addHandler(SizeChangeEvent.TYPE, new EventHandlerI<SizeChangeEvent>() {

			@Override
			public void handle(SizeChangeEvent e) {
				WindowTest.this.onResizeEvent(e);

			}
		});

		this.delayTestFinish(10 * 1000);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {

			@Override
			public void execute() {
				Size size = Size.valueOf(1600, 1700);
				// w.resizeTo(size);
				WindowResizeHelper.resizeTo(size.getWidth(), size.getHeight());
				Size size2 = w.getSize();
				assertEquals("sizeTo failed.", size, size2);
				WindowTest.this.finishTest();

			}
		});

	}

	protected void onResizeEvent(SizeChangeEvent e) {
		this.finishTest();
	}
}
