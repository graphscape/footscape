/**
 * Jun 12, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.core.UiObjectI;
import com.fs.uicore.api.gwt.client.endpoint.Address;
import com.fs.uicore.api.gwt.client.endpoint.EndPointI;

/**
 * @author wuzhen
 * 
 */
public interface UiClientI extends UiObjectI {

	public String getClientId();

	public EndPointI getEndpoint(boolean force);

	public RootI getRoot();

	public void setParameter(String key, String value);

	public String getParameter(String key, String def);

	public String getParameter(String key, boolean force);
	
	public int getParameterAsInt(String key, int def);
	
	public boolean getParameterAsBoolean(String key, boolean def);

	public CodecI.FactoryI getCodecFactory();

	public String getPreferedLocale();

	public String localized(String key);

	public void start();//

}
