/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.expector.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.HasIdConfigurableSupport;
import com.fs.datagrid.api.DataGridI;
import com.fs.datagrid.api.DgFactoryI;
import com.fs.datagrid.api.objects.DgMapI;
import com.fs.expector.api.GridMemberI;
import com.fs.expector.api.data.MemberRefGd;

/**
 * @author wu
 * 
 */
public class GridMemeberImpl extends HasIdConfigurableSupport implements GridMemberI {

	protected DataGridI dg;
	
	protected DgMapI<String,MemberRefGd> dgMap;
	

	/*
	 *Dec 15, 2012
	 */
	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
		
		//register to the dg.
		DgFactoryI df = this.container.find(DgFactoryI.class,true);
		dg = df.getInstance();
		this.dgMap = this.dg.getMap("gd-member-map", MemberRefGd.class);
		
		MemberRefGd mrgd = new MemberRefGd(this.id);
		
		this.dgMap.put(this.id, mrgd);
		
	}
	

}
