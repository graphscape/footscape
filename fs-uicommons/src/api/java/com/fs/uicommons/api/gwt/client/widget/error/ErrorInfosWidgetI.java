/**
 * 
 */
package com.fs.uicommons.api.gwt.client.widget.error;

import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;

/**
 * @author wuzhen
 * 
 */
public interface ErrorInfosWidgetI extends WidgetI {

	public void addErrorInfos(ErrorInfosData errorInfos);

	public void clear();

}
