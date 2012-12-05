/**
 * Jun 20, 2012
 */
package com.fs.commons.api.describe;

import com.fs.commons.api.SPI;

/**
 * @author wu
 * 
 */
public interface DescribedI<T extends DescribedI<?>> {
	public static final String DK_OBJECT_CLASS = "_OBJECT_CLASS";

	public static final String DK_TYPE = "_TYPE";

	public static final String DK_NAME = "_NAME";

	public static final String DK_PRT_CFG_ID = "_PRT_CFG_ID";

	public static final String DK_CLASS = "_CLASS";

	public static final String DK_SPIID = "_SPIID";

	public static final String DK_SPI = "_SPI";

	public Describe getDescribe();

	public T describe(String key, Object value);

	public T type(String type);

	public T name(String name);

	public T objectClass(Class cls);

	public T prtCfgId(String pcid);

	public T spi(SPI spi);

	public T clazz(Class clazz);

	public SPI getSPI();

	public String getName();

	public Class getObjectClass();

	public Class getClazz();

	public String getPrtCfgId();

}
