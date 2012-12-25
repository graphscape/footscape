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
import com.fs.dataservice.api.expapp.wrapper.Session;

/**
 * @author wu
 * 
 */
public class AuthedUtil {

	public static Session beforeAuthedOperation(OperationI op, DataServiceI ds,
			ResultI rst) {
		String id = (String) op
				.getParameter(AuthedOperationI.PK_SESSION_ID, true);

		Session se = ds.getNewestById(Session.class, id, false);

		if (se == null) {
			rst.getErrorInfo().add(
					new ErrorInfo("no session record with id:" + id));
			return null;// not login
		}
		return se;
	}
}
