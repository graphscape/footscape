/**
 * Jul 22, 2012
 */
package com.fs.commons.api.validator;

import com.fs.commons.api.value.ValueI;

/**
 * @author wu
 * 
 */
public class ValidateItem<T> implements ValueI {

	protected T target;

	protected boolean evaluated;

	protected boolean valid;

	protected ConditionI<T> condition;

	public ValidateItem(T target, ConditionI<T> c) {
		this.target = target;
		this.condition = c;
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
	public Object getCondition() {
		return condition;
	}

	/**
	 * @return the evaluated
	 */
	public boolean isEvaluated() {
		return evaluated;
	}

}
