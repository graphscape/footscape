/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package com.fs.expector.dataservice.api;

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

	public static final NodeType SESSION = NodeType.valueOf("session");

	public static final NodeType EXPECTATION = NodeType.valueOf("expectation");
	public static final NodeType CONNECT_REQUEST = NodeType
			.valueOf("connect-request");
	public static final NodeType EXPMESSAGE = NodeType
			.valueOf("exp-message");
	
	public static final NodeType CONNECTION = NodeType
			.valueOf("connection");

}
