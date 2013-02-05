/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package com.fs.uicore.impl.gwt.client;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.WindowI;
import com.fs.uicore.api.gwt.client.commons.Size;
import com.fs.uicore.api.gwt.client.event.SizeChangeEvent;
import com.fs.uicore.api.gwt.client.event.WindowClosingEvent;
import com.fs.uicore.api.gwt.client.support.StatefulUiObjectSupport;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;

/**
 * @author wu
 * 
 */
public class WindowImpl extends StatefulUiObjectSupport implements WindowI {

	public WindowImpl(ContainerI c) {
		super(c);
	}

	/**
	 * @param arg0
	 */
	protected void onGwtClosing(ClosingEvent arg0) {

		new WindowClosingEvent(this).dispatch();

	}

	/**
	 * @param resizeevent
	 */
	protected void onGwtResize(ResizeEvent resizeevent) {
		Size size = Size.valueOf(resizeevent.getWidth(), resizeevent.getHeight());
		new SizeChangeEvent(size, this).dispatch();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.WindowI#getSize()
	 */
	@Override
	public Size getSize() {
		int w = Window.getClientWidth();
		int h = Window.getClientHeight();
		Size rt = Size.valueOf(w, h);
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicore.api.gwt.client.WindowI#resizeTo(com.fs.uicore.api.gwt.client
	 * .commons.Size)
	 */
	@Override
	public void resizeTo(Size size) {
		Window.resizeTo(size.getWidth(), size.getHeight());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#doAttach()
	 */
	@Override
	protected void doAttach() {
		// TODO Auto-generated method stub
		super.doAttach();
		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent resizeevent) {
				WindowImpl.this.onGwtResize(resizeevent);

			}
		});
		Window.addWindowClosingHandler(new ClosingHandler() {

			@Override
			public void onWindowClosing(ClosingEvent arg0) {
				WindowImpl.this.onGwtClosing(arg0);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.uicore.api.gwt.client.support.UiObjectSupport#doDetach()
	 */
	@Override
	protected void doDetach() {
		super.doDetach();

	}

}
