/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 28, 2012
 */
package com.fs.dataservice.core.impl;

import org.elasticsearch.common.UUID;

/**
 * @author wu
 * 
 */
public class UUIDUtil {

	/**
	 * Nov 28, 2012
	 */
	public static String randomStringUUID() {
		return UUID.randomBase64UUID();
	}
}
