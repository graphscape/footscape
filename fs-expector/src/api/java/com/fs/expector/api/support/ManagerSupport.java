/**
 *  Dec 14, 2012
 */
package com.fs.expector.api.support;

import com.fs.commons.api.lang.FsException;
import com.fs.datagrid.api.objects.DgMapI;
import com.fs.expector.api.ManagerI;
import com.fs.expector.api.MemberI;
import com.fs.expector.impl.dg.support.DgCollectionSupport;

/**
 * @author wuzhen
 * 
 */
public abstract class ManagerSupport<I extends MemberI, C extends I> extends
		DgCollectionSupport<I, C, DgMapI<String, I>> implements ManagerI<I> {

	public ManagerSupport(Class<I> itf, Class<C> wcls) {
		super(itf, wcls);
	}

	@Override
	public I getMember(String id, boolean force) {
		I rt = this.getMember(id);
		if (force && rt == null) {
			throw new FsException("no this member:" + id + ",manager:"
					+ this.getName());
		}
		return rt;
	}

}
