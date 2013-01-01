/**
 * Jun 12, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wuzhen
 * 
 */
public interface UiClientI extends UiObjectI {

	public String getClientId();

	public EndPointI getEndpoint();

	public RootI getRoot();

	public ModelI getRootModel();

	public void setParameter(String key, String value);

	public String getParameter(String key, String def);

	public String getParameter(String key, boolean force);

	public CodecI.FactoryI getCodecFactory();

	public void start();//

}
