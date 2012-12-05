/**
 * Jul 17, 2012
 */
package com.fs.commons.impl.codec.json;

import java.util.List;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.value.ErrorInfos;
import com.fs.commons.impl.codec.support.ListJCSSupport;

/**
 * @author wu
 * 
 */
public class ErrorInfosJCS extends ListJCSSupport<ErrorInfos> implements
		CodecI {
	/** */
	public ErrorInfosJCS(FactoryI f) {
		super("ES", ErrorInfos.class, f);

	}

	/* */
	@Override
	protected ErrorInfos convert(List pts) {
		ErrorInfos rt = new ErrorInfos();
		rt.getErrorInfoList().addAll(pts);
		return rt;

	}

	/* */
	@Override
	protected List convert(ErrorInfos t) {
		List rt = t.getErrorInfoList();
		return rt;

	}

}
