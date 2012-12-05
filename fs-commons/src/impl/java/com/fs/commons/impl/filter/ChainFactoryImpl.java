/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 2, 2012
 */
package com.fs.commons.impl.filter;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.filter.ChainI;

/**
 * @author wuzhen
 * 
 */
public class ChainFactoryImpl implements ChainI.FactoryI {

	@Override
	public <REQ, RES> ChainI<REQ, RES> createChain(ActiveContext ac,
			Class<REQ> cls1, Class<RES> cls2) {
		//
		ChainImpl<REQ, RES> rt = new ChainImpl<REQ, RES>();
		rt.active(ac);
		return rt;
		//
	}

}
