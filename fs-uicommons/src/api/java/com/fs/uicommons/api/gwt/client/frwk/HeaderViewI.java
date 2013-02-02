/**
 *  Jan 31, 2013
 */
package com.fs.uicommons.api.gwt.client.frwk;

import com.fs.uicommons.api.gwt.client.mvc.ViewI;
import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wuzhen
 * 
 */
public interface HeaderViewI extends ViewI {

	public void addItem(Path path);

	public void setItemDisplayText(Path path, String text);

}
