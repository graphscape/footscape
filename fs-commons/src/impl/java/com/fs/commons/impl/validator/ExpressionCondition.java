/**
 * Jul 22, 2012
 */
package com.fs.commons.impl.validator;

import com.fs.commons.api.jexl.ExpressionI;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.validator.ConditionI;

/**
 * @author wu
 * 
 */
public class ExpressionCondition<X> implements ConditionI<X> {

	private ExpressionI expression;

	public ExpressionCondition(ExpressionI exp) {
		this.expression = exp;
	}

	/* */
	@Override
	public boolean isMeet(X cxt) {

		Object v = this.expression.evaluate(cxt);
		boolean rt = v != null && (v instanceof Boolean) && ((Boolean) v);
		return rt;

	}

	/* */
	@Override
	public String toString() {
		return String.valueOf(this.expression);

	}

}
