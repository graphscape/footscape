/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicore.impl.test.gwt.client.helper;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;

/**
 * @author wu
 * 
 */
public class WindowResizeHelper {
	private static int clientHeight;
	private static int clientWidth;
	private static int extraWidth;
	private static int extraHeight;
	private static boolean initialized;

	public static int getExtraHeight() {
		ensureInitialized();
		return extraHeight;
	}

	public static int getExtraWidth() {
		ensureInitialized();
		return extraWidth;
	}

	/**
	 * Wraps {@code Window#resizeBy(int, int)} to ensure initialized. This may
	 * be a no-op in Chrome.
	 * 
	 * @param width
	 * @param height
	 * @return Whether this operation is done
	 */
	public static boolean resizeBy(int width, int height) {
		if (ensureInitialized()) {
			Window.resizeBy(width, height);
		}
		return initialized;
	}

	/**
	 * Wraps {@code Window#resizeTo(int, int)} to ensure initialized. This may
	 * be a no-op in Chrome.
	 * 
	 * @param width
	 * @param height
	 * @return Whether this operation is done
	 */
	public static boolean resizeTo(int width, int height) {
		if (ensureInitialized()) {
			Window.resizeTo(width, height);
		}
		return initialized;
	}

	public static void restoreSize() {
		// Ignore if not initialized
		if (initialized) {
			Window.resizeTo(clientWidth + extraWidth, clientHeight
					+ extraHeight);
		}
	}

	private static synchronized boolean ensureInitialized() {
		if (!initialized) {
			init();
		}
		return initialized;
	}

	private static void init() {
		// resizeTo works in Chrome if the window is opened by Window.open(),
		// which is the case when testing with Selenium and the server is
		// started
		// with -multiWin. However, the size change is deferred. The test would
		// involve many nested DeferredCommand.
		if (Navigator.getUserAgent().toLowerCase().contains("chrome")) {
			return;
		}

		// FF4 on win can start in 'almost' fullscreen when the window title bar
		// is hidden but accounted incorrectly, so, move the window and resize
		// to
		// smaller size first, to take it out of 'full screen mode'.
		Window.moveTo(10, 10);
		Window.resizeTo(700, 500);

		// store the original size (to be used in restoreSize)
		clientHeight = Window.getClientHeight();
		clientWidth = Window.getClientWidth();
		// IE cannot resize window out of the screen, so we need to move the
		// window such that it can be resized to below size.
		// We do not have method to return the window coordinates (screenLeft,
		// screenTop), so this move is not undone.
		Window.moveTo(0, 0);

		// clientWidth is innerWidth, resizeTo specifies outerWidth
		// Let's find out the delta for extras such as border, menu, tool bar.
		// If the sizes are too small to show the extras, resizeTo may not set
		// the
		// sizes as requested.
		// If the sizes are too big, for example, height > screen.availHeight +
		// 40
		// on FF, resizeTo silently sets the height to screen.availHeight + 40.
		// Some test machines are configured at this time as 800x600, reduce the
		// size
		// to give some 'buffer'
		Window.resizeTo(750, 550);
		extraWidth = 750 - Window.getClientWidth();
		extraHeight = 550 - Window.getClientHeight();
		initialized = true;
		restoreSize();
	}

}
