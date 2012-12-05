/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 23, 2012
 */
package com.fs.uicommons.api.gwt.client.manage;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 *
 */
public interface ManagedModelI extends ModelI {

	public static final Location L_SELECTED = ModelI.L_DEFAULT;

	public WidgetI getManagedWidget();

	public void select(boolean sel);

	public boolean isSelect();

}