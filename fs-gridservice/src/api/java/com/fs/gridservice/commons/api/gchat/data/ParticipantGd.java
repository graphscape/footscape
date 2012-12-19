/**
 *  Dec 18, 2012
 */
package com.fs.gridservice.commons.api.gchat.data;

import com.fs.commons.api.value.PropertiesI;
import com.fs.gridservice.commons.api.data.EntityGd;

/**
 * @author wuzhen
 * 
 */
public class ParticipantGd extends EntityGd {

	public static final String PK_GROUPID = "groupId";

	public static final String PK_ACCID = "accId";

	public static final String PK_ROLE = "role";

	public static final String PK_TERMINALID = "terminalId";

	public ParticipantGd() {

	}

	public ParticipantGd(String accId) {
		super(accId);
	}

	public ParticipantGd(PropertiesI<Object> pts) {
		super(pts);

	}

	/**
	 * @return the pkGroupid
	 */
	public String getGroupId() {
		return this.getString(PK_GROUPID);
	}

	public String getAccountId() {
		return this.getString(PK_ACCID);
	}

	public String getTerminalId() {
		return this.getString(PK_TERMINALID);
	}

	public String getRole() {
		return this.getString(PK_ROLE);
	}
}
