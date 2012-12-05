/**
 * Jul 22, 2012
 */
package com.fs.commons.impl.validator;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.jexl.ExpressionI;
import com.fs.commons.api.jexl.JexlEngineI;
import com.fs.commons.api.validator.ConditionI;
import com.fs.commons.api.validator.ValidateItem;
import com.fs.commons.api.validator.ValidateResult;
import com.fs.commons.api.validator.ValidatorI;

/**
 * @author wu
 * 
 */
public class ValidatorImpl<T> implements ValidatorI<T> {

	private List<ConditionI<T>> conditionList;

	private JexlEngineI jexl;

	public ValidatorImpl(JexlEngineI jexl) {
		this.conditionList = new ArrayList<ConditionI<T>>();
		this.jexl = jexl;
	}

	/* */
	@Override
	public void addExpression(String exp) {
		ExpressionI ex = this.jexl.createExpression(exp);
		ExpressionCondition<T> ec = new ExpressionCondition<T>(ex);
		this.addCondition(ec);
	}

	/* */
	@Override
	public void addCondition(ConditionI<T> c) {
		this.conditionList.add(c);
	}

	/* */
	@Override
	public ValidateResult<T> validate(T target) {
		ValidateResult<T> rt = new ValidateResult<T>();
		for (ConditionI<T> c : this.conditionList) {
			ValidateItem<T> vi = new ValidateItem<T>(target, c);
			vi.evaluate(target);// TODO exception process?
			rt.add(vi);
		}
		return rt;

	}

}
