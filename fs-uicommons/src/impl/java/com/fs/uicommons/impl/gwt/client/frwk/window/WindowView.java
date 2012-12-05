package com.fs.uicommons.impl.gwt.client.frwk.window;

import com.fs.uicommons.api.gwt.client.mvc.support.ProxyViewSupport;
import com.fs.uicommons.api.gwt.client.widget.wpanel.WindowPanelWI;
import com.fs.uicore.api.gwt.client.ContainerI;

public class WindowView extends ProxyViewSupport<WindowPanelWI> {

	public WindowView(ContainerI ctn) {
		super(ctn, WindowPanelWI.class);
	}

}
