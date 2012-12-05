/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 13, 2012
 */
package com.fs.uicommons.impl.test.gwt.client.echo;

import com.fs.uicommons.api.gwt.client.frwk.commons.FormsModel;
import com.fs.uicommons.api.gwt.client.mvc.support.ControlUtil;

/**
 * @author wu
 * 
 */
public class EchoModel extends FormsModel {

	/**
	 * @param name
	 */
	public EchoModel(String name) {
		super(name);
		ControlUtil.addAction(this, "echo");// or called from control
	}

}
