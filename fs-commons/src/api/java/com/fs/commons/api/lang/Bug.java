/**
 * Jun 11, 2012
 */
package com.fs.commons.api.lang;

/**
 * @author wuzhen
 * 
 */
public class Bug extends FsException {
	public Bug(String msg) {
		super(msg);
	}

	public static Bug bug(String msg) {
		return new Bug(msg);
	}
}
