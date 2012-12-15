/**
 * Jul 17, 2012
 */
package com.fs.commons.impl.codec.json;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.message.support.MessageSupport;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.impl.codec.support.PropertiesJCSSupport;

/**
 * @author wu
 * 
 */
public class MessageJCS extends PropertiesJCSSupport<MessageI> implements CodecI {
	public static final String HEADERS = "_headers";
	public static final String PAYLOADS = "_payloads";

	/** */
	public MessageJCS(FactoryI f) {
		super("M", MessageI.class, f);
	}

	/* */
	@Override
	protected MessageI convert(PropertiesI<Object> pts) {
		PropertiesI<String> hds = (PropertiesI) pts.getProperty(HEADERS);
		PropertiesI<Object> payloads = (PropertiesI) pts.getProperty(PAYLOADS);
		MessageI rt = MessageSupport.newMessage(hds, payloads);

		return rt;

	}

	/* */
	@Override
	protected PropertiesI<Object> convert(MessageI t) {
		PropertiesI<Object> rt = new MapProperties<Object>();
		rt.setProperty(HEADERS, t.getHeaders());
		rt.setProperty(PAYLOADS, t.getPayloads());

		return rt;

	}

}
