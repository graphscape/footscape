/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package com.fs.uicommons.impl.gwt.client.manage;

import com.fs.uicommons.api.gwt.client.manage.BossModelI;
import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class BossModelImpl extends ModelSupport implements BossModelI {

	/**
	 * @param c
	 */
	public BossModelImpl(String name) {
		super(name);
		new ManagerModel(BossModelI.M_TOP).parent(this);

		new ManagerModel(BossModelI.M_LEFT).parent(this);

		new ManagerModel(BossModelI.M_CENTER).parent(this);

		new ManagerModel(BossModelI.M_RIGHT).parent(this);
		
		new ManagerModel(BossModelI.M_POPUP).parent(this);
		
		new ManagerModel(BossModelI.M_BOTTOM).parent(this);
		

	}

}
