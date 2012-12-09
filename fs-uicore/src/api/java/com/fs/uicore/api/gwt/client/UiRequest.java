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

	public static final String IS_LOCAL = "_is_local";

	/**
	 * @param fc
	 */
	public UiRequest() {
		this(null);
	}

	public UiRequest(boolean local) {
		this(null, local);
	}

	public UiRequest(String name) {
		this(name, false);
	}

	public UiRequest(String name, boolean local) {
		super(name);
		this.setIsLocal(local);
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

	public void setIsLocal(boolean isL) {
		this.setHeader(IS_LOCAL, Boolean.toString(isL));// filtered
	}
	
	public boolean isLocal(){
		return Boolean.valueOf(this.getHeader(IS_LOCAL));
	}
	
	public void setInit(boolean init) {
		this.setHeader(INIT, init + "");
	}

	public boolean isInit() {
		return Boolean.valueOf(this.getHeader(INIT));
	}
}
