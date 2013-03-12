/**
 * All right is from Author of the file,to be explained in comming days.
 * Mar 12, 2013
 */
package com.fs.uiclient.impl.gwt.client.uexp;

import com.fs.uiclient.api.gwt.client.uexp.ExpConnect;
import com.fs.uicommons.api.gwt.client.mvc.support.ViewSupport;
import com.fs.uicommons.api.gwt.client.widget.basic.LabelI;
import com.fs.uicore.api.gwt.client.ContainerI;
import com.google.gwt.user.client.DOM;

/**
 * @author wu
 * 
 */
public class ConnectedExpView extends ViewSupport {

	/**
	 * @param c
	 * @param ele
	 */
	public ConnectedExpView(ContainerI c, ExpConnect ec) {
		super(c, DOM.createDiv());
		LabelI l1 = this.factory.create(LabelI.class);
		l1.setText(ec.getNick2(true) + ":");
		l1.parent(this);

		LabelI l2 = this.factory.create(LabelI.class);
		l2.setText(ec.getExpBody2(true));
		l2.parent(this);

	}

}
