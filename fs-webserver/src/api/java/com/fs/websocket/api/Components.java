/**
 *  
 */
package com.fs.websocket.api;

import com.fs.commons.api.client.AClientI;
import com.fs.commons.api.lang.ClassUtil;

/**
 * @deprecated
 * @author wu <br>
 *         TODO component factory.
 */
public class Components {

	public static Class<? extends AClientI> CLS_MOCK_WSCLIENT = ClassUtil
			.forName("com.fs.websocket.impl.mock.MockWSClientImpl");
}
