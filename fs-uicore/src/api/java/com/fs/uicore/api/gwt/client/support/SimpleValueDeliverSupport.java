/**
 * 
 */
package com.fs.uicore.api.gwt.client.support;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.SimpleValueDeliverI;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;

/**
 * @author wuzhen
 * 
 */
public abstract class SimpleValueDeliverSupport<S, T> implements
		SimpleValueDeliverI<S, T> {
	public static class ValuePoint<X> implements SimpleValueDeliverI.ValueSourceI<X>{

		/**
		 * @param model1
		 * @param loc1
		 * @param def1
		 */
		public ValuePoint(ModelI model1, Location loc1) {
			this.model = model1;
			this.location = loc1;
		}

		public ModelI model;

		public Location location;

		public boolean isModel(ModelI md) {
			return this.model == md;
		}
		
		@Override
		public X getValue() {
			X rt = (X) this.model.getValue(location);

			return rt;
		}

		/*
		 *Dec 2, 2012
		 */
		@Override
		public void setValue(X x) {
			this.model.setValue(this.location, x);
		}

	}

	protected Map<S, T> valueMap = new HashMap<S, T>();

	protected T defaultTargetValue;

	protected ValueConverterI<S, T> defaultConverter;

	protected ValueSourceI<T> targetValueSource;

	/**
	 * @param model2
	 * @param loc2
	 */
	public SimpleValueDeliverSupport(ModelI model2, Location loc2) {
		this(new ValuePoint<T>(model2, loc2));
	}

	public SimpleValueDeliverSupport(ValueSourceI<T> target) {
		this.targetValueSource = target;
	}

	@Override
	public SimpleValueDeliverI<S, T> mapValue(Map<S, T> map) {
		this.valueMap.putAll(map);
		return this;
	}

	@Override
	public SimpleValueDeliverI<S, T> mapValue(S s, T t) {
		this.valueMap.put(s, t);//
		return this;
	}

	@Override
	public SimpleValueDeliverI<S, T> mapDefault(T t) {
		this.defaultTargetValue = t;
		return this;
	}

	@Override
	public SimpleValueDeliverI<S, T> mapDefault(ValueConverterI<S, T> vc) {
		this.defaultConverter = vc;
		return this;
	}

	@Override
	public SimpleValueDeliverI<S, T> mapDefaultDirect() {
		return this.mapDefault(new ValueConverterI<S, T>() {

			@Override
			public T convert(S s) {
				//
				return (T) s;
			}
		});
	}

	protected void deliver(S value) {

		T rt = this.convert((S) value);

		if (rt == null) {
			if (this.defaultTargetValue == null) {
				// no default target,not touch target.
				return;
			}
			rt = this.defaultTargetValue;//
		}

		// if the converted value is null,not touch to the target.

		// if no need to set the target value for same value.

		Object old = this.targetValueSource.getValue();
		if (ObjectUtil.nullSafeEquals(old, rt)) {
			return;
		}
		this.targetValueSource.setValue(rt);//
	}

	protected T convert(S s) {

		T rt = this.valueMap.get(s);
		if (rt == null && this.defaultConverter != null) {
			rt = this.defaultConverter.convert(s);
		}
		return rt;
	}

}
