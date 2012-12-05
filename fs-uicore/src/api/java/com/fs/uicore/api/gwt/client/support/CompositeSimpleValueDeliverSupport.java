/**
 * 
 */
package com.fs.uicore.api.gwt.client.support;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.ModelI;
import com.fs.uicore.api.gwt.client.ModelI.Location;
import com.fs.uicore.api.gwt.client.SimpleValueDeliverI;

/**
 * @author wuzhen
 * 
 */
public abstract class CompositeSimpleValueDeliverSupport<S, T> extends
		SimpleValueDeliverSupport<S, T> {

	private List<SimpleValueDeliverI<S, T>> deliverList = new ArrayList<SimpleValueDeliverI<S, T>>();

	/**
	 * @param model2
	 * @param loc2
	 */
	public CompositeSimpleValueDeliverSupport(ModelI model2, Location loc2) {
		super(model2, loc2);
	}

	protected void addDeliver(SimpleValueDeliverI<S, T> sd) {
		sd.mapDefault(this.defaultConverter);
		sd.mapDefault(this.defaultTargetValue);
		sd.mapValue(this.valueMap);
		this.deliverList.add(sd);//
	}

}
