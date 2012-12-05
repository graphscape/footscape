/**
 * Jul 22, 2012
 */
package com.fs.commons.api.validator;

/**
 * @author wu
 * 
 */
public interface ValidatorI<T> {
	public static interface FactoryI {
	
		public <T> ValidatorI<T> createValidator();
	
	}

	public void addExpression(String exp);

	public void addCondition(ConditionI<T> c);

	public ValidateResult<T> validate(T ctx);

}
