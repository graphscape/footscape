/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 28, 2012
 */
package com.fs.dataservice.expapp.impl.elastic.util;

import com.fs.commons.api.value.ErrorInfo;
import com.fs.dataservice.api.core.DataServiceI;
import com.fs.dataservice.api.core.OperationI;
import com.fs.dataservice.api.core.ResultI;
import com.fs.dataservice.api.expapp.operations.AuthedOperationI;
import com.fs.dataservice.api.expapp.wrapper.Login;

/**
 * @author wu
 * 
 */
public class AuthedUtil {

	public static Login beforeAuthedOperation(OperationI op, DataServiceI ds,
			ResultI rst) {
		String id = (String) op
				.getParameter(AuthedOperationI.PK_LOGIN_ID, true);

		Login login = ds.getNewestById(Login.class, id, false);

		if (login == null) {
			rst.getErrorInfo().add(
					new ErrorInfo("no login record with login id:" + id));
			return null;// not login
		}
		return login;
	}
}
