/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 16, 2012
 */
package com.fs.uiclient.impl.gwt.client.profile;

import com.fs.uiclient.api.gwt.client.support.MHSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.data.message.MessageData;
import com.fs.uicore.api.gwt.client.data.property.ObjectPropertiesData;
import com.fs.uicore.api.gwt.client.event.EndpointMessageEvent;

/**
 * @author wu
 * 
 */
public class FormInitAP extends MHSupport {

	private String form;

	private String payload;// key in payload

	public FormInitAP(ContainerI c, String form, String payload) {
		super(c);
		this.form = form;
		this.payload = payload;
	}

	@Override
	public void handle(EndpointMessageEvent t) {

		MessageData res = t.getMessage();
		ObjectPropertiesData opd = (ObjectPropertiesData) res.getPayloads().getProperty(this.payload, false);
		// TODO
	}
}
