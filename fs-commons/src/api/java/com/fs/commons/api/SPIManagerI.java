/**
 * Jun 19, 2012
 */
package com.fs.commons.api;

import com.fs.commons.api.lang.ClassUtil;
import com.fs.commons.api.support.StringProperties;

/**
 * @author wu
 * 
 */
public interface SPIManagerI {
	public class Factory {

		private static Class<SPIManagerI> CLS;
		static {

			StringProperties pw = StringProperties.load(
					"/boot/bootup.properties", true);
			CLS = pw.getPropertyAsClass(SPIManagerI.class.getName(), true);
		}

		public SPIManagerI get() {

			SPIManagerI rt = ClassUtil.newInstance(CLS);
			return rt;
		}

	}

	public static Factory FACTORY = new Factory();

	public void load(String res);//

	public SPI getSpi(String id, boolean force);
	
	public void add(String id);

	public void add(String id, Class<? extends SPI> cls);

	public ContainerI getContainer();

	public void shutdown();//

}
