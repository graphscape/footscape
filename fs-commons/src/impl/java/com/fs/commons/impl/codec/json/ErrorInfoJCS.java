/**
 * Jul 17, 2012
 */
package com.fs.commons.impl.codec.json;

import java.util.List;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.support.MapProperties;
import com.fs.commons.api.value.ErrorInfo;
import com.fs.commons.api.value.PropertiesI;
import com.fs.commons.impl.codec.support.PropertiesJCSSupport;

/**
 * @author wu
 * 
 */
public class ErrorInfoJCS extends PropertiesJCSSupport<ErrorInfo> implements
		CodecI {
	public static final String SOURCE = "source";
	public static final String MESSAGE = "message";
	public static final String DETAIL = "detail";

	/** */
	public ErrorInfoJCS(FactoryI f) {
		super("E", ErrorInfo.class, f);

	}

	/* */
	@Override
	protected ErrorInfo convert(PropertiesI<Object> pts) {
		String source = (String) pts.getProperty(SOURCE);
		String msg = (String) pts.getProperty(MESSAGE);
		List<String> detail = (List<String>) pts.getProperty(DETAIL);
		ErrorInfo rt = new ErrorInfo(source, msg);
		rt.getDetail().addAll(detail);

		return rt;

	}

	/* */
	@Override
	protected PropertiesI<Object> convert(ErrorInfo t) {
		PropertiesI<Object> rt = new MapProperties<Object>();
		rt.setProperty(SOURCE, t.getSource());
		rt.setProperty(MESSAGE, t.getMessage());
		rt.setProperty(DETAIL, t.getDetail());
		return rt;

	}

}
