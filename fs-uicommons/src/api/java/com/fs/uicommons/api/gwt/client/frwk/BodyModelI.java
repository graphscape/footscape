/**
 *  Jan 30, 2013
 */
package com.fs.uicommons.api.gwt.client.frwk;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wuzhen
 * @deprecated
 */
public interface BodyModelI extends ModelI {

	public ViewReferenceI manage(Path p, WidgetI w);

	public ViewReferenceI getManaged(Path p);

	public void showManaged(Path p);

}
