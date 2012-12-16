/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.support;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.lang.ClassUtil;
import com.fs.datagrid.api.DataGridI;
import com.fs.datagrid.api.DgCollectionI;
import com.fs.datagrid.api.DgFactoryI;
import com.fs.expector.api.GridedDataI;

/**
 * @author wuzhen
 * 
 */
public abstract class CollectionSupport<E extends GridedDataI, D extends DgCollectionI> extends
		ConfigurableSupport {

	protected DataGridI dg;

	protected Class<E> wrapperClass;

	protected String name;

	protected String collectionName;

	protected D target;

	/**
	 * @param name
	 */
	public CollectionSupport(Class<E> wcls) {
		this.wrapperClass = wcls;

	}

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);
		this.name = cfg.getName();
		this.collectionName = "dgc-" + this.name;

	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		DgFactoryI df = this.container.find(DgFactoryI.class);
		this.dg = df.getInstance();//
		this.target = this.activeTarget();
	}

	protected abstract D activeTarget();

	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public E newElement() {

		E rt = ClassUtil.newInstance(this.wrapperClass);

		return rt;
	}

}
