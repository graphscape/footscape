/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package com.fs.uixmpp.groupchat.api.gwt.client;

/**
 * @author wu
 *
 */
public class Role {

	private String name;

	private Role(String name) {
		this.name = name;
	}

	public static Role valueOf(String name) {
		return new Role(name);
	}

	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Role)){
			return false;
		}
		return this.name.equals(((Role)obj).name);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	

}
