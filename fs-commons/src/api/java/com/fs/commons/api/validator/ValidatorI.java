/**
 * Jul 22, 2012
 */
package com.fs.commons.api.validator;

import com.fs.commons.api.struct.Path;

/**
 * @author wu
 * 
 */
public interface ValidatorI<T> {
	public static interface FactoryI {

		public <T> ValidatorI<T> createValidator();

	}

	public void addExpression(Path errorCode, String exp);

	public void addCondition(Path errorCode, ConditionI<T> c);

	public ValidateResult<T> validate(T ctx);

}
