/**
 * Jun 11, 2012
 */
package com.fs.commons.api.lang;

/**
 * @author wuzhen
 * 
 */
public class Todo extends FsException {

	public Todo() {
		this("todo");
	}

	public Todo(String msg) {
		super(msg);
	}

	public static Todo todo(String msg) {
		return new Todo(msg);
	}
}
