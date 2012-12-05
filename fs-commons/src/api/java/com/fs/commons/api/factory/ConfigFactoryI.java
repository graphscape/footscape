/**
 * Jun 19, 2012
 */
package com.fs.commons.api.factory;

import com.fs.commons.api.FinderI;

/**
 * @author wu
 * 
 */
public interface ConfigFactoryI {

	public FinderI<ObjectConfigI> finder();

	public PopulatorI newPopulator();

}
