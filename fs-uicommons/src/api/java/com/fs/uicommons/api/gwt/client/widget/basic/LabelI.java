/**
 * Jun 25, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.basic;

import com.fs.uicommons.api.gwt.client.widget.BasicI;

/**
 * @author wuzhen
 * 
 */
public interface LabelI extends BasicI {

	/**
	 * @param key
	 */
	public void setText(String key);

	public void setText(String key, boolean loc);

	public void setTitle(String title);

	public void setTextAndTitle(String key, boolean loc, String title);

}
