/**
 * Jun 12, 2012
 */
package com.fs.uicommons.api.gwt.client.mvc;

import com.fs.uicore.api.gwt.client.commons.Path;
import com.fs.uicore.api.gwt.client.core.WidgetI;
import com.fs.uicore.api.gwt.client.data.ErrorInfosData;

/**
 * @author wuzhen
 * 
 */
public interface ViewI extends WidgetI {

	public void clickAction(Path a);
	
	public void addErrorInfo(ErrorInfosData eis);
	
	public void clearErrorInfo();

}
