/**
 * Jun 23, 2012
 */
package com.fs.commons.api.codec;

/**
 * @author wu
 * 
 */
public interface CodecI {

	public static interface FactoryI {
		public CodecI getCodec(Class<?> dataCls);

		public CodecI getCodec(Class<?> dataCls, boolean force);

		public CodecI getCodec(String type);

	}

	public String getTypeCode();

	public Class<?> getDataClass();

	public Object decode(Object ser);

	public Object encode(Object ud);

}
