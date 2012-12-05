/**
 * Jun 20, 2012
 */
package com.fs.commons.api.support;

import com.fs.commons.api.SPI;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.describe.Describe;
import com.fs.commons.api.describe.DescribedI;

/**
 * @author wu
 * 
 */
public class DescribedSupport<T extends DescribedI<?>> extends ConfigurableSupport implements DescribedI<T> {

	protected Describe describe = new Describe();

	/* */
	@Override
	public T describe(String key, Object value) {

		this.describe.attribute(key, value);
		return (T) this;

	}

	public T type(String type) {
		this.describe.attribute(DescribedI.DK_TYPE, type);
		return (T) this;
	}

	public T name(String name) {
		this.describe.attribute(DescribedI.DK_NAME, name);
		return (T) this;
	}

	public T clazz(Class cls) {
		this.describe.attribute(DescribedI.DK_CLASS, cls);
		return (T) this;
	}

	public T objectClass(Class cls) {
		this.describe.attribute(DescribedI.DK_OBJECT_CLASS, cls);
		return (T) this;
	}

	public T prtCfgId(String pcid) {
		this.describe.attribute(DescribedI.DK_PRT_CFG_ID, pcid);
		return (T) this;
	}

	public T spi(SPI spi) {
		this.describe.attribute(DescribedI.DK_SPIID, spi.getId());
		this.describe.attribute(DescribedI.DK_SPI, spi);
		return (T) this;
	}

	public String getPrtCfgId() {
		return (String) this.describe.getAttribute(DescribedI.DK_PRT_CFG_ID);
	}

	public String getType() {
		return (String) this.describe.getAttribute(DescribedI.DK_TYPE);
	}

	public String getName() {
		return (String) this.describe.getAttribute(DescribedI.DK_NAME);
	}

	public Class getObjectClass() {
		return (Class) this.describe.getAttribute(DescribedI.DK_OBJECT_CLASS);
	}

	public Class getClazz() {
		return (Class) this.describe.getAttribute(DescribedI.DK_CLASS);
	}

	public SPI getSPI() {
		return (SPI) this.describe.getAttribute(DescribedI.DK_SPI);
	}

	@Override
	public String toString() {
		return this.describe.toString();
	}

	/* */
	@Override
	public Describe getDescribe() {

		return this.describe;

	}

}
