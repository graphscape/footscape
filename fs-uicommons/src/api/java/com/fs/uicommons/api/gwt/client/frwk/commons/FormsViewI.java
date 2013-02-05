/**
 *  Feb 1, 2013
 */
package com.fs.uicommons.api.gwt.client.frwk.commons;

import com.fs.uicommons.api.gwt.client.mvc.ViewI;

/**
 * @author wuzhen
 * 
 */
public interface FormsViewI extends ViewI {

	public FormViewI addForm(String name);

	public FormViewI getForm(String name);

	public FormViewI getDefaultForm();
}
