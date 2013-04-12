/**
 *  Jan 30, 2013
 */
package com.fs.uicommons.api.gwt.client.frwk;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wuzhen
 * @deprecated remove this class
 */
public interface ViewReferenceI {

	public static interface AwareI {
		public void setViewReference(ViewReferenceI vr);
	}

	public Path getPath();
	
	public WidgetI getManagedWidget();

	public void select(boolean sel);

	public boolean isSelect();
	

}
