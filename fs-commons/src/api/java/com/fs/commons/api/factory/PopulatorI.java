/**
 * Jun 20, 2012
 */
package com.fs.commons.api.factory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.ContainerI;
import com.fs.commons.api.SPI;

/**
 * @author wuzhen
 * @deprecated
 */
public interface PopulatorI {

	public static interface FactoryI {
		public PopulatorI populator(String type);
	}

	public PopulatorI spi(SPI spi);

	public PopulatorI cfgId(String cfgId);

	public PopulatorI type(String type);

	public PopulatorI force(boolean force);

	public PopulatorI active(ActiveContext ac);

	public PopulatorI container(ContainerI c);

	public PopulatorI populate();

}
