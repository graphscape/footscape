/**
 * Jun 23, 2012
 */
package com.fs.uicore.api.gwt.client;

import com.fs.uicore.api.gwt.client.core.UiData;

/**
 * @author wu
 * 
 */
public interface CodecI {

	public static interface FactoryI {
		public CodecI getCodec(Class<? extends UiData> dataCls);

		public CodecI getCodec(String type);

	}
	
	public String getTypeCode();
	
	public Class<? extends UiData> getDataClass();

	public UiData decode(Object ser);

	public Object encode(UiData ud);

}
