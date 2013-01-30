/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 23, 2012
 */
package com.fs.uicommons.api.gwt.client.frwk;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public interface FrwkModelI extends ModelI {

	public static String M_TOP = "top";

	public static String M_CENTER = "center";

	public static String M_LEFT = "left";

	public static String M_RIGHT = "right";

	public static String M_BOTTOM = "bottom";
	
	public static String M_POPUP = "popup";
	
	public HeaderModelI getHeader();

	public BodyModelI getBody();

}
