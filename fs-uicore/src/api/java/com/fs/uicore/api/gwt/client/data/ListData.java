/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 12, 2012
 */
package com.fs.uicore.api.gwt.client.data;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.core.UiData;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;

/**
 * @author wuzhen
 * 
 */
public class ListData<T> extends UiData {
	private List<T> list = new ArrayList<T>();

	public int size() {
		return this.list.size();
	}

	public void add(T t) {
		this.list.add(t);
	}

	public T get(int idx) {
		return this.list.get(idx);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof ListData)) {
			return false;
		}
		return this.isEquals((ListData) o);

	}

	protected boolean isEquals(ListData<T> list) {
		if (this.size() != list.size()) {
			return false;
		}

		for (int i = 0; i < this.size(); i++) {

			T d1 = this.get(i);
			T d2 = list.get(i);

			if (!ObjectUtil.nullSafeEquals(d1, d2)) {
				return false;
			}
		}
		return true;
	}

}
