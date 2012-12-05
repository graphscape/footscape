/**
 * Jun 11, 2012
 */
package com.fs.commons.api.lang;

/**
 * @author wuzhen
 * 
 */
public class FsException extends RuntimeException {

	private Object source;

	public FsException() {
		this(null, null, null);
	}

	public FsException(Throwable t) {
		this(null, null, t);
	}

	public FsException(Object source, Throwable t) {
		this(source, null, t);
	}

	public FsException(Object source, String msg) {
		this(source, msg, null);
	}

	public FsException(String msg, Throwable t) {
		this(null, msg, t);
	}

	public FsException(Object source, String msg, Throwable t) {
		super(msg, t);
	}

	public FsException(String msg) {
		this(null, msg, null);
	}

	public static FsException valueOf(Exception e) {
		return new FsException(e);
	}

	public static RuntimeException toRtE(Throwable t) {
		if (t instanceof Error) {
			throw (Error) t;
		} else if (t instanceof RuntimeException) {
			throw (RuntimeException) t;
		} else {
			throw FsException.valueOf((Exception) t);
		}
	}

	/**
	 * @param e
	 * @return
	 */
	public static RuntimeException toRuntimeException(Exception e) {

		return toRtE(e);

	}

	/**
	 * @return the source
	 */
	public Object getSource() {
		return source;
	}
}
