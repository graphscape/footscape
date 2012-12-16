/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.expector.api.session;

import com.fs.commons.api.value.PropertiesI;
import com.fs.expector.api.data.SessionGd;

/**
 * @author wu
 * 
 */
public interface SessionManagerI {

	public SessionGd createSession(PropertiesI<Object> pts);

}
