/**
 * Jul 22, 2012
 */
package com.fs.commons.api.validator;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.value.ValueI;

/**
 * @author wu
 * 
 */
public class ValidateResult<T> implements ValueI {

	private List<ValidateItem<T>> itemList = new ArrayList<ValidateItem<T>>();

	public ValidateResult() {

	}

	public void add(ValidateItem<T> vi) {
		this.itemList.add(vi);
	}

	public List<ValidateItem<T>> getItemList() {
		return this.getItemList(null, null);
	}

	public List<ValidateItem<T>> getItemList(Boolean evaluated, Boolean valid) {
		List<ValidateItem<T>> rt = new ArrayList<ValidateItem<T>>();
		for (ValidateItem<T> i : this.itemList) {
			if ((evaluated == null || evaluated.equals(i.isEvaluated()))
					&& (valid == null || valid.equals(i.isValid()))) {
				rt.add(i);
			}
		}
		return rt;
	}

}
