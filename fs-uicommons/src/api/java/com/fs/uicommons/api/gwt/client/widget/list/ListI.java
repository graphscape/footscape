/**
 * Jun 13, 2012
 */
package com.fs.uicommons.api.gwt.client.widget.list;

import com.fs.uicore.api.gwt.client.core.WidgetI;

/**
 * @author wu
 * 
 */
public interface ListI extends WidgetI {
	
	public static final String PK_IS_VERTICAL = "isVertical";
	
	public static final String PK_COMPARATOR = "comparator";
	
	public static final String PK_LIST_ITEM_CLASSNAME = "itemClassName"; 

	public int getSize();
}
