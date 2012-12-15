/**
 * Jun 11, 2012
 */
package com.fs.engine.api;

import com.fs.commons.api.message.MessageI;

/**
 * @author wuzhen
 * 
 */
public interface RequestI extends MessageI {

	public static final String PATH = "__path";

	public void setPath(String path);

	public String getPath();
}
