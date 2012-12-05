/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.dataservice.api.expapp;

import com.fs.dataservice.api.core.NodeType;

/**
 * @author wu
 * 
 */
public class NodeTypes {

	public static final NodeType SIGNUP_REQUEST = NodeType
			.valueOf("signup-request");

	public static final NodeType SIGNUP_CONFIRM = NodeType
			.valueOf("signup-confirm");

	public static final NodeType ACCOUNT = NodeType.valueOf("account");

	public static final NodeType LOGIN = NodeType.valueOf("login");

	public static final NodeType LOGIN_SUCCESS = NodeType
			.valueOf("login-success");

	public static final NodeType USER = NodeType.valueOf("user");

	public static final NodeType ACTIVITY = NodeType.valueOf("activity");
	public static final NodeType EXPECTATION = NodeType.valueOf("expectation");
	public static final NodeType COOPER_REQUEST = NodeType
			.valueOf("cooper-request");
	public static final NodeType COOPER_CONFIRM = NodeType
			.valueOf("cooper-confirm");

}
