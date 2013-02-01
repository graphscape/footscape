/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.api.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wuzhen
 * 
 */
public interface BodyViewI extends ViewI {

	public <T extends WidgetI> T getItem(Path path, boolean force);

	public <T extends WidgetI> T addItem(Path path, T w);

	public <T extends WidgetI> T getOrCreateItem(Path path, com.fs.uicommons.api.gwt.client.CreaterI<T> crt);

}