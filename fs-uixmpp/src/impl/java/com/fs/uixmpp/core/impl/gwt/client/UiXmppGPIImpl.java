/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 15, 2012
 */
package com.fs.uixmpp.core.impl.gwt.client;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf;
import com.fs.uicore.api.gwt.client.reflect.InstanceOf.CheckerSupport;
import com.fs.uixmpp.core.api.gwt.client.UiXmppCoreGPI;
import com.fs.uixmpp.core.api.gwt.client.XmppControlI;
import com.fs.uixmpp.core.api.gwt.client.XmppModelI;

/**
 * @author wu
 * 
 */
public class UiXmppGPIImpl implements UiXmppCoreGPI {

	@Override
	public void active(ContainerI c) {
		this.activeInstanceOf(c);
		//
		UiClientI uc = c.get(UiClientI.class, true);
		XmppModel xm = new XmppModel("xmpp");
		uc.getRootModel().child(xm);//

		XmppControlI xc = new XmppControl("xmpp");
		xc.model(xm);//
		xc.parent(uc);//

	}

	protected void activeInstanceOf(ContainerI c) {
		//
		InstanceOf.addChecker(new CheckerSupport(XmppControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof XmppControlI;

			}
		});
		InstanceOf.addChecker(new CheckerSupport(XmppModelI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof XmppModelI;

			}
		});
		

	}

}
