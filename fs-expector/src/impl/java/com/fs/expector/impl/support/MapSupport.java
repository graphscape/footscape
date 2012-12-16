/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.support;

import com.fs.datagrid.api.objects.DgMapI;
import com.fs.expector.api.GridedDataI;

/**
 * @author wuzhen
 * 
 */
public class MapSupport<E extends GridedDataI> extends CollectionSupport<E, DgMapI<String, E>> {

	public MapSupport(Class<E> wcls) {
		super(wcls);
	}

	@Override
	protected DgMapI<String, E> activeTarget() {
		DgMapI<String, E> rt = this.dg.getMap(this.collectionName, this.wrapperClass);
		return rt;
	}

}
