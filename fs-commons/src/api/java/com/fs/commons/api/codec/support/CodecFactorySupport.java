/**
 * Jun 23, 2012
 */
package com.fs.commons.api.codec.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.ClassWrapper;

/**
 * @author wu
 * 
 */
public class CodecFactorySupport implements CodecI.FactoryI {

	private Map<String, CodecI> jcMap = new HashMap<String, CodecI>();

	private Map<Class, String> classMap = new HashMap<Class, String>();

	public CodecFactorySupport() {

	}

	protected void add(CodecI cd) {
		String tc = cd.getTypeCode();
		Class cls = cd.getDataClass();
		String oldx = this.classMap.put(cls, tc);
		if (oldx != null) {
			throw new FsException("duplicated:" + cls + ",oldx:" + oldx);
		}
		CodecI old = this.jcMap.put(tc, cd);
		if (old != null) {
			throw new FsException("duplicated:" + tc);
		}

	}

	/* */
	@Override
	public CodecI getCodec(Class<?> dataCls) {
		return this.getCodec(dataCls, false);
	}

	@Override
	public CodecI getCodec(Class<?> dataCls, boolean force) {
		String tc = this.getTypeCode(dataCls, true);
		CodecI rt = this.getCodec(tc);
		if (rt == null && force) {
			throw new FsException("no codec for type:" + dataCls);
		}
		return rt;

	}

	protected String getTypeCode(Class<?> dataCls, boolean force) {

		String tc = this.classMap.get(dataCls);
		if (tc != null) {
			return tc;
		}

		List<Class> superL = new ClassWrapper(dataCls).getSuperTypeList();//
		List<String> codeL = new ArrayList<String>();
		for (Class sc : superL) {
			String stc = this.classMap.get(sc);
			if (stc != null) {
				codeL.add(stc);
			}
		}

		if (codeL.isEmpty()) {
			if (force) {
				throw new FsException("no type code for:" + dataCls);
			}
			return null;
		} else if (codeL.size() == 1) {
			return codeL.get(0);
		} else {
			throw new FsException("too much type code:" + codeL + ", for class:" + dataCls);
		}

	}

	/* */
	@Override
	public CodecI getCodec(String type) {

		CodecI rt = this.jcMap.get(type);
		if (rt == null) {
			throw new FsException("no codec found for type code:" + type + ",all:" + this.jcMap.keySet());
		}
		return rt;
	}

}
