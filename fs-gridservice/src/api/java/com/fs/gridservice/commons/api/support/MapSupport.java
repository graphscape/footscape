/**
 *  Dec 14, 2012
 */
package com.fs.gridservice.commons.api.support;

import com.fs.gridservice.commons.api.GridedDataI;
import com.fs.gridservice.core.api.objects.DgMapI;

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
