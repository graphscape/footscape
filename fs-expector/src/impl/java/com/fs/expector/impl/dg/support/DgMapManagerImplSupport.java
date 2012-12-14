/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.dg.support;

import java.util.UUID;

import com.fs.commons.api.lang.ClassUtil;
import com.fs.datagrid.api.objects.DgMapI;
import com.fs.expector.api.MemberI;
import com.fs.expector.api.support.ManagerSupport;

/**
 * @author wuzhen
 * 
 */
public class DgMapManagerImplSupport<T extends MemberI, C extends T> extends
		ManagerSupport<T, C> {

	/**
	 * @param name
	 */
	public DgMapManagerImplSupport(Class<T> itf, Class<C> wcls) {
		super(itf, wcls);
	}

	@Override
	protected DgMapI<String, T> activeTarget() {
		DgMapI<String, T> rt = this.dg.getMap(this.collectionName,
				this.wrapperItf, this.wrapperClass);
		return rt;
	}

	@Override
	public T getMember(String id) {

		return this.target.getValue(id);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.expector.api.ManagerI#addMember(com.fs.expector.api.MemberI)
	 */
	@Override
	public String addMember(T t) {
		String rt = t.getId();
		this.target.put(t.getId(), t);
		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.expector.api.ManagerI#newMember(java.lang.String)
	 */
	@Override
	public T newMember(String id) {
		T rt = ClassUtil.newInstance(this.wrapperClass,
				new Class[] { String.class }, new Object[] { id });

		return rt;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.expector.api.ManagerI#newMember()
	 */
	@Override
	public T newMember() {
		String id = UUID.randomUUID().toString();
		return this.newMember(id);
	}

}
