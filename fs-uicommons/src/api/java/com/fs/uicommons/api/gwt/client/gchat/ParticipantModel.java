/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat;

import com.fs.uicore.api.gwt.client.support.ModelSupport;

/**
 * @author wu
 * 
 */
public class ParticipantModel extends ModelSupport {

	protected String accountId;

	protected String nick;

	protected String role;
	
	protected String groupId;

	/**
	 * @param name
	 */
	public ParticipantModel(String id,String gid) {
		super(id,id);
		this.groupId = gid;
	}

	public String getId() {
		return this.getName();
	}

	/**
	 * @return the accountId
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId
	 *            the accountId to set
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick
	 *            the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

}
