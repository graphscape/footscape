/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 19, 2012
 */
package com.fs.uicommons.api.gwt.client.manage;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface ManagerModelI extends ModelI {

	public ManagedModelI manage(ModelI model, WidgetI w);

	public ManagedModelI getManaged(String key);

	public void showManaged(String managed);
}
