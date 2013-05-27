/**
 * Jun 19, 2012
 */
package com.fs.commons.api.factory;

import com.fs.commons.api.describe.Describe;
import com.fs.commons.api.describe.DescribedI;

/**
 * @author wuzhen
 * @deprecated
 */
public interface ObjectConfigI extends DescribedI {

	public <T> T newInstance();
	
	public String getCfgId();

	public Describe getDescribe();

}
