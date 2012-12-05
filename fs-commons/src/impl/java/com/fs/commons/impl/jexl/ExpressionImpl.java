/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 5, 2012
 */
package com.fs.commons.impl.jexl;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.ObjectContext;

import com.fs.commons.api.jexl.ExpressionI;

/**
 * @author wuzhen
 * 
 */
public class ExpressionImpl implements ExpressionI {

	private Expression exp;
	private JexlEngine eng;

	public ExpressionImpl(JexlEngine eng, Expression exp) {
		this.exp = exp;
		this.eng = eng;
	}

	@Override
	public Object evaluate(Object ctx) {
		//

		JexlContext jc = new ObjectContext<Object>(this.eng, ctx);
		Object rt = exp.evaluate(jc);
		return rt;
		//
	}

	@Override
	public String toString() {
		return String.valueOf(this.exp);
	}

}
