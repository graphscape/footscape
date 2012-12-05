/**
 * Jun 23, 2012
 */
package com.fs.commons.api.converter.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fs.commons.api.converter.ConverterI;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.util.CollectionUtil;

/**
 * @author wu
 * 
 */
public class ConverterFactorySupport implements ConverterI.FactoryI {
	private List<ConverterI> converterList;

	public ConverterFactorySupport() {
		this.converterList = new ArrayList<ConverterI>();
	}

	/* */
	@Override
	public <F, T> ConverterI<F, T> getConverter(Class<F> scls, Class<T> tcls) {
		return this.getConverter(scls, tcls, false);
	}

	@Override
	public <F, T> ConverterI<F, T> getConverter(Class<F> scls, Class<T> tcls,
			boolean force) {
		return this.getConverter(scls, true, tcls, force);
	}

	@Override
	public <F, T> ConverterI<F, T> getConverter(Class<F> scls,
			boolean strictTarget, Class<T> tcls) {
		return this.getConverter(scls, strictTarget, tcls, false);
	}

	@Override
	public <F, T> ConverterI<F, T> getConverter(Class<F> scls,
			boolean strictTarget, Class<T> tcls, boolean force) {

		List<ConverterI> tL = this.getByTargetClass(tcls, strictTarget);

		ConverterI rt = this.getByTheNearestSuperClass(scls, tL, force);

		return rt;

	}

	protected List<ConverterI> getByTargetClass(Class tcls, boolean strictTarget) {
		List<ConverterI> rt = new ArrayList<ConverterI>();

		for (ConverterI c : this.converterList) {
			Class ctcls = c.getTagetClass();
			if (strictTarget && ctcls.equals(tcls)// must be equals;
					// or can be the sub class of required target
					|| !strictTarget && tcls.isAssignableFrom(ctcls)) {
				rt.add(c);
			}
		}
		return rt;
	}

	protected ConverterI getByTheNearestSuperClass(Class scls,
			List<ConverterI> cL, boolean force) {

		ConverterI rt = null;
		for (ConverterI c : cL) {
			Class cscls = c.getSourceClass();
			if (cscls.isAssignableFrom(scls)// find one any way
					&& (rt == null || rt.getSourceClass().isAssignableFrom(
							cscls))) {
				rt = c;
			}

		}

		if (force && rt == null) {
			throw new FsException("no source class match object:" + scls);
		}
		return rt;
	}

	/* */
	@Override
	public <S, T> void addConverter(ConverterI<S, T> dc) {
		this.converterList.add(dc);
	}

}
