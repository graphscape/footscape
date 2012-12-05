/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.uixmpp.core.api.gwt.client;

import com.fs.uicore.api.gwt.client.data.ErrorInfosData;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;

/**
 * @author wu
 *         <p>
 *         chat, error, get,
 * 
 *         groupchat, headline, normal, probe,
 * 
 *         result, set, subscribe, subscribed, unavailable, unsubscribe,
 *         unsubscribed,
 */
public interface StanzaI extends ElementI {

	public static class Type {
		private String name;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		private Type(String type) {
			this.name = type;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Type)) {
				return false;
			}
			return ObjectUtil.nullSafeEquals(this.name, ((Type) obj).name);
		}

		public static Type valueOf(String name) {
			return new Type(name);// TODO cache?
		}
	}

	public static final String N_MESSAGE = "message";

	public static final String N_IQ = "iq";

	public static final String N_PRESENCE = "presence";

	public static Type T_SET = new Type("set");
	public static Type T_GET = new Type("get");
	public static Type T_ERROR = new Type("error");
	public static Type T_GROUPCHAT = new Type("groupchat");
	public static Type T_NORMAL = new Type("normal");
	public static Type T_UNAVAILABLE = new Type("unavailable");
	

	public Type getType();

	public void setType(Type type);

	public void setTo(Jid to);

	public Jid getFrom();

	public Jid.Bare getFromBare();

	public Jid getTo();

	public Jid.Bare getToBare();

	public void send();

	public String getId();

	public void assertNoError();

	public ErrorInfosData getErrorInfos();

}
