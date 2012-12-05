/**
 * Jul 8, 2012
 */
package com.fs.commons.api;

/**
 * @author wu
 * 
 */
public interface ActivitorI {

	public ActivitorI spi(SPI spi);

	public ActivitorI context(ActiveContext ac);

	public ActivitorI cfgId(String cfgId);

	public ActivitorI container(ContainerI c);

	public ActivitorI name(String name);

	public ActivitorI object(Object obj);

	public ActivitorI clazz(Class cls);

	public ActivitorI active();

}
