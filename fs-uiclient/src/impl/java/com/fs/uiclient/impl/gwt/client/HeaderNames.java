/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 27, 2012
 */
package com.fs.uiclient.impl.gwt.client;

import com.fs.uicore.api.gwt.client.commons.Path;

/**
 * @author wu
 * 
 */
public class HeaderNames {

	public static final Path H1_LOGO = Path.valueOf("logo");

	public static final Path H1_USER = Path.valueOf("user");

	public static final Path H2_PROFILE = H1_USER.getSubPath("profile");

	public static final Path H2_SIGNUP = H1_USER.getSubPath("signup");

	public static final Path H1_MYEXP = Path.valueOf("myExp");

	public static final Path H1_SEARCH = Path.valueOf("search");

}
