/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 24, 2012
 */
package com.fs.uicommons.api.gwt.client.manage;

import com.fs.uicommons.api.gwt.client.mvc.ControlI;
import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 *
 */
public interface BossControlI extends ControlI{

	public ManagerModelI getManager(String name);

	public ManagedModelI manage(ModelI model, WidgetI view);
}
