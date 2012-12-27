/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 16, 2012
 */
package com.fs.gridservice.commons.api.support;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.gridservice.commons.api.GridFacadeI;

/**
 * @author wu
 * 
 */
public class FacadeAwareConfigurableSupport extends ConfigurableSupport {

	protected GridFacadeI facade;

	@Override
	public void active(ActiveContext ac) {
		//
		super.active(ac);
		//

		this.facade = this.container.find(GridFacadeI.class, true);
	}

}
