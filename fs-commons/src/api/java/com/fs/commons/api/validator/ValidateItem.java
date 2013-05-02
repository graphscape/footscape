/**
 * Jul 22, 2012
 */
package com.fs.commons.api.validator;

import com.fs.commons.api.struct.Path;
import com.fs.commons.api.value.ValueI;

/**
 * @author wu
 * 
 */
public class ValidateItem<T> implements ValueI {

	protected boolean evaluated;

	protected boolean valid;

	protected ConditionI<T> condition;
	
	protected Path errorCode;
	
	public ValidateItem(Path errorCode,ConditionI<T> c) {
		this.condition = c;
		this.errorCode = errorCode;
	}

	public boolean evaluate(T t) {

		this.valid = this.condition.isMeet(t);
		this.evaluated = true;
		return this.valid;
	}

	/**
	 * @return the validate
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @return the condition
	 */
	public ConditionI<T> getCondition() {
		return condition;
	}

	/**
	 * @return the evaluated
	 */
	public boolean isEvaluated() {
		return evaluated;
	}

	/**
	 * @return the errorCode
	 */
	public Path getErrorCode() {
		return errorCode;
	}

}
