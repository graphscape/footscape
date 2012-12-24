/**
 * Jun 12, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.core.UiCallbackI;
import com.fs.uicore.api.gwt.client.core.UiFilterI;
import com.fs.uicore.api.gwt.client.core.UiObjectI;

/**
 * @author wuzhen
 * 
 */
public interface UiClientI extends UiObjectI {

	public static final String ROOT_URi = "rootUri";

	// request context for path prefix

	public static final String CONTEXT_PATH = "contextPath";//

	public void sendRequest(UiRequest req, UiCallbackI<UiResponse, Object> res);

	public void addFilter(int idx, UiFilterI f);
	
	public void addFilter(UiFilterI f);

	public String getClientId();

	public RootI getRoot();

	public ModelI getRootModel();

	public void setParameter(String key, String value);
	
	public String getParameter(String key, String def);

	public String getParameter(String key, boolean force);

	public CodecI.FactoryI getCodecFactory();

	public void start();//

}
