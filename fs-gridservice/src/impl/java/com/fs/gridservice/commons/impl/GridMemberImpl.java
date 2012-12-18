/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.gridservice.commons.impl;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.HasIdConfigurableSupport;
import com.fs.gridservice.commons.api.GridMemberI;
import com.fs.gridservice.commons.api.data.MemberRefGd;
import com.fs.gridservice.core.api.DataGridI;
import com.fs.gridservice.core.api.DgFactoryI;
import com.fs.gridservice.core.api.objects.DgMapI;

/**
 * @author wu
 * 
 */
public class GridMemberImpl extends HasIdConfigurableSupport implements GridMemberI {

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
