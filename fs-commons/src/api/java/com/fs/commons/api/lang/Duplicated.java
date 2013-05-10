/**
 * Jun 11, 2012
 */
package com.fs.commons.api.lang;

/**
 * @author wuzhen
 * 
 */
public class Duplicated extends FsException {

	public Duplicated() {
		this("duplicated");
	}

	public Duplicated(String msg) {
		super(msg);
	}

	public static Duplicated todo(String msg) {
		return new Duplicated(msg);
	}
}
