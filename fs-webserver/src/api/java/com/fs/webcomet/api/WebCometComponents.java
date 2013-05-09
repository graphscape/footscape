/**
 *  
 */
package com.fs.webcomet.api;

import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.client.BClientFactoryI;
import com.fs.commons.api.lang.ClassUtil;

/**
 * @author wu
 * 
 */
public class WebCometComponents {

	public static final BClientFactoryI.ProtocolI AJAX = new BClientFactoryI.ProtocolI() {

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "ajax";
		}

		@Override
		public Class<? extends AClientI> getClientClass() {
			return ClassUtil.forName("com.fs.webcomet.impl.mock.MockAjaxClientImpl");
		}
	};
}
