/**
 * Jun 12, 2012
 */
package com.fs.uicore.api.gwt.client;

/**
 * @author wuzhen
 * 
 */
public class UiRequest extends UiTransfer {

	public static final String PATH = "X_FS_PATH";

	public static final String SESSION_ID = "X_FS" + "_session_id";// NOTE

	public static final String INIT = "X_FS_INIT";//

	/**
	 * @param fc
	 */
	public UiRequest() {
		this(null);
	}

	public UiRequest(String name) {
		super(name);
	}

	public UiRequest requestPath(String path) {
		this.setRequestPath(path);
		return this;
	}

	public void setRequestPath(String path) {
		this.setHeader(PATH, path);
	}

	public String getRequestPath() {
		return this.getHeader(PATH);
	}

	public void setInit(boolean init) {
		this.setHeader(INIT, init + "");
	}

	public boolean isInit() {
		return Boolean.valueOf(this.getHeader(INIT));
	}
}
