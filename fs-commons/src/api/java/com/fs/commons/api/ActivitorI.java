/**
 * Jul 8, 2012
 */
package com.fs.commons.api;

import com.fs.commons.api.config.Configuration;

/**
 * @author wu
 * @deprecated
 */
public interface ActivitorI {

	public ActivitorI spi(SPI spi);

	public ActivitorI context(ActiveContext ac);

	public ActivitorI cfgId(String cfgId);
	
	public ActivitorI configuration(Configuration cfg);

	public ActivitorI container(ContainerI c);

	public ActivitorI name(String name);

	public ActivitorI object(Object obj);

	public ActivitorI clazz(Class cls);

	public ActivitorI active();
	
	public <T> T getObject();

}
