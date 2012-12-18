/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.chat.data;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.data.EntityGd;

/**
 * @author wuzhen
 * 
 */
public class PresenceGd extends EntityGd {

	public static final String PK_STATUS = "_status";

	public static final String PK_PARTICIPANTID = "_participantId";

	public PresenceGd() {

	}

	public PresenceGd(PropertiesI<Object> pts) {
		super(pts);
	}

	public String getStatus() {
		return (String) this.getProperty(PK_STATUS);
	}
}
