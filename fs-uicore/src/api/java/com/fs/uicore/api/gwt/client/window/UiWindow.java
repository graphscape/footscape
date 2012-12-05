/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 18, 2012
 */
package com.fs.uicore.api.gwt.client.window;

import com.fs.uicore.api.gwt.client.commons.Point;
import com.fs.uicore.api.gwt.client.commons.Rectangle;
import com.fs.uicore.api.gwt.client.commons.Size;
import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class UiWindow {

	public static String getParameter(String name, String def) {
		String rt = com.google.gwt.user.client.Window.Location
				.getParameter(name);
		return rt == null ? def : rt;
	}

	public static Rectangle getRectangle() {
		int x = Window.getScrollLeft();
		int y = Window.getScrollTop();
		int w = Window.getClientWidth();
		int h = Window.getClientHeight();
		
		return new Rectangle(Point.valueOf(x, y), Size.valueOf(w, h));
	}
}
