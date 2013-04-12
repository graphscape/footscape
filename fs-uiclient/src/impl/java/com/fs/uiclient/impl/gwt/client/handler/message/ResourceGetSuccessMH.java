/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 3, 2013
 */
package com.fs.uiclient.impl.gwt.client.handler.message;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicommons.api.gwt.client.widget.html.HtmlElementWidgetI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class ResourceGetSuccessMH extends MHSupport {

	/**
	 * @param c
	 */
	public ResourceGetSuccessMH(ContainerI c) {
		super(c);
		// TODO Auto-generated constructor stub
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(EndpointMessageEvent t) {
		MessageData res = t.getMessage();
		MessageData req = res.getSource();
		String urlS = req.getHeader("url");
		String pathS = urlS.substring("classpath://".length());
		Path resoP = Path.valueOf(pathS);

		String reso = res.getString("resource", true);
		MainControlI mc = this.getControl(MainControlI.class, true);

		HtmlElementWidgetI w = mc.openHtmlResource(resoP, false);
		w.setInnerHtml(reso);

	}

}
