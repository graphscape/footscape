/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 7, 2012
 */
package com.fs.expector.gridservice.api;

import com.fs.commons.api.struct.Path;

/**
 * @author wu
 * 
 */
public interface Constants {

	public static final String HK_CLIENT_ID = "x-fs-client-id";

	public static Path P_ERROR = Path.valueOf("error");

	public static Path P_ERROR_INPUT = P_ERROR.getSubPath("input");
	
	public static Path P_ERROR_NOTALLOW = P_ERROR.getSubPath("notallow");
	
	public static Path P_ERROR_NOTFOUND = P_ERROR.getSubPath("notfound");

}
