/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.support;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.datagrid.api.DataGridI;
import com.fs.datagrid.api.DgFactoryI;
import com.fs.expector.api.GridMemberI;

/**
 * @author wuzhen
 * 
 */
public abstract class DataGridAwareSupport extends ConfigurableSupport {

	protected DataGridI dg;

	protected String name;

	protected GridMemberI member;

	/**
	 * @param name
	 */
	public DataGridAwareSupport() {

	}

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);
		this.name = cfg.getName();

	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		DgFactoryI df = this.container.find(DgFactoryI.class);
		this.dg = df.getInstance();//
		this.member = this.container.find(GridMemberI.class, true);//
	
	}

}
