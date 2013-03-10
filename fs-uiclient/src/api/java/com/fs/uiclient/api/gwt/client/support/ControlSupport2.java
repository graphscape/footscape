/**
 * All right is from Author of the file,to be explained in comming days.
 * Feb 1, 2013
 */
package com.fs.uiclient.api.gwt.client.support;

import com.fs.uiclient.api.gwt.client.main.MainControlI;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlSupport;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.endpoint.UserInfo;

/**
 * @author wu
 * 
 */
public class ControlSupport2 extends ControlSupport {

	/**
	 * @param c
	 * @param name
	 */
	public ControlSupport2(ContainerI c, String name) {
		super(c, name);
	}

	protected MainControlI getMainControl() {
		return this.getManager().getControl(MainControlI.class, true);
	}

	protected UserInfo getUserInfo() {
		UserInfo rt = this.getClient(true).getEndpoint().getUserInfo();
		return rt;
	}

	protected boolean isUser(String accId) {
		UserInfo ui = this.getUserInfo();

		return ui.getAccountId().equals(accId);
	}

}
