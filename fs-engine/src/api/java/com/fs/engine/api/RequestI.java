/**
 * Jun 11, 2012
 */
package com.fs.engine.api;

/**
 * @author wuzhen
 * 
 */
public interface RequestI extends RRContextI {

	public static final String PATH = "__path";

	public void setPath(String path);

	public String getPath();
}
