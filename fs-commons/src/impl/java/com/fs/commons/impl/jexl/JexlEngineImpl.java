/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 5, 2012
 */
package com.fs.commons.impl.jexl;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlEngine;

import com.fs.commons.api.jexl.ExpressionI;
import com.fs.commons.api.jexl.JexlEngineI;

/**
 * @author wuzhen
 * 
 */
public class JexlEngineImpl implements JexlEngineI {

	private JexlEngine engine;

	public JexlEngineImpl() {
		this.engine = new JexlEngine();
	}

	@Override
	public ExpressionI createExpression(String exp) {
		//
		Expression e = engine.createExpression(exp);
		return new ExpressionImpl(this.engine, e);

		//
	}

}
