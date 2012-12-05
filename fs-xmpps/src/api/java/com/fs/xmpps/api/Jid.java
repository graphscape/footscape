/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 15, 2012
 */
package com.fs.xmpps.api;

import com.fs.commons.api.lang.ObjectUtil;

/**
 * @author wu
 * 
 */
public class Jid {

	private Bare bare;

	private String resource;

	public static class Bare {

		private String user;

		private String domain;

		public Bare(String bareS) {
			String[] ss = bareS.split("@");
			if (ss.length == 1) {
				domain = ss[0];
			} else {
				user = ss[0];
				domain = ss[1];
			}
		}

		public Bare(String user, String domain) {
			this.user = user;
			this.domain = domain;
		}

		/**
		 * @return the user
		 */
		public String getUser() {
			return user;
		}

		/**
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Jid.Bare)) {
				return false;
			}
			Jid.Bare j2 = ((Jid.Bare) obj);
			return ObjectUtil.nullSafeEquals(this.user, j2.user)
					&& ObjectUtil.nullSafeEquals(this.domain, j2.domain);
		}

		public Jid toJid() {
			return Jid.valueOf(this.toString());
		}

		@Override
		public String toString() {

			return (this.user == null ? "" : (this.user + "@")) + this.domain;
		}

		public static Bare valueOf(Jid jid) {
			if (jid == null) {
				return null;
			}
			return jid.getBare();
		}

		/**
		 * Oct 22, 2012
		 */
		public boolean isUser(String uname) {
			return this.user.equals(uname);
		}
	}

	public Jid.Bare getBare() {
		return this.bare;
	}

	public Jid domain() {
		return new Jid(this.bare.domain);
	}

	/**
	 * @return the resource
	 */
	public String getResource() {
		return resource;
	}

	private Jid(String vl) {

		String[] ss = vl.split("/");
		String bareS = ss[0];
		this.bare = new Bare(bareS);
		this.resource = ss.length > 1 ? ss[1] : null;

	}

	public static Jid valueOf(Jid.Bare bare, String res) {
		String ss = bare.toString() + (res == null ? "" : ("/" + res));
		return valueOf(ss);

	}

	public static Jid valueOf(String value) {
		if (value == null) {
			return null;
		}
		return new Jid(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Jid)) {
			return false;
		}
		Jid j2 = ((Jid) obj);
		return ObjectUtil.nullSafeEquals(this.bare, j2.bare)
				&& ObjectUtil.nullSafeEquals(this.resource, j2.resource);
	}

	@Override
	public String toString() {
		return this.bare.toString()
				+ (this.resource == null ? "" : ("/" + this.resource));
	}

	/**
	 * @return
	 */
	public Jid bare() {

		return Jid.valueOf(this.bare, null);
	}

	/**
	 * Oct 22, 2012
	 */
	public boolean isUser(String rname) {
		//
		return this.bare.isUser(rname);
	}

}