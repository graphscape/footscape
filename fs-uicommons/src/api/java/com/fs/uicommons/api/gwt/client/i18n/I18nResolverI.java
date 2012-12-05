/**
 *  Nov 30, 2012
 */
package com.fs.uicommons.api.gwt.client.i18n;

import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wuzhen
 * 
 */
public interface I18nResolverI extends UiObjectI {

	public void resolve(String key, UiCallbackI<String, String> callback);

}
