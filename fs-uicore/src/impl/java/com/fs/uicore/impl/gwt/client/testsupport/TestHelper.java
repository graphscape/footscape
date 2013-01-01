/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package com.fs.uicore.impl.gwt.client.testsupport;

import com.fs.uicore.api.gwt.client.ContainerI;
import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.UiCoreGwtSPI;
import com.fs.uicore.api.gwt.client.UiException;
import com.fs.uicore.api.gwt.client.spi.GwtSPI;
import com.google.gwt.core.client.GWT;


/**
 * @author wu
 * This class simplify the test support.
 */
public class TestHelper {
	
	public static TestHelper ME;
	
	
	private static UiClientI startClient(Class[] spiClsA){
		
		if(ME != null){
			throw new UiException("already initialized,");//TODO support this case
		}
		ME = new TestHelper();
		
		GwtSPI.Factory factory = GwtSPI.Factory.get();
		ContainerI container = factory.getContainer();
		
		factory.active((UiCoreGwtSPI) GWT.create(UiCoreGwtSPI.class));
		
		UiClientI client = container.get(UiClientI.class, true);
		
		//client.setProperty(UiClientI.ROOT_URi, "/uicore/uidt/do");
		
		client.attach();//NOTE
		
		return GwtSPI.Factory.get().getContainer().get(UiClientI.class, true);		
	}
	
	
}
