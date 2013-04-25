/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import java.util.Date;

import com.fs.dataservice.api.core.NodeType;
import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.meta.FieldType;
import com.fs.dataservice.api.core.meta.NodeMeta;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;

/**
 * @author wu TODO rename to RegisteredUser
 */
public class Acknowledge extends NodeWrapper {
	public static final String CAT_EXPM = "exp-messages";

	public static final NodeType TYPE = NodeType.valueOf("acknowledge");//

	public static final String PK_ACCOUNT_ID = "accountId";

	public static final String PK_CATEGORY = "category";

	public static final String PK_OBJECT_ID = "objectId";

	public static final String PK_ACKNOWLEDGED = "acknowledged";

	/**
	 * @param pts
	 */
	public Acknowledge() {
		super(TYPE);
	}

	public static void config(DataSchema cfs) {
		NodeMeta nc = cfs.addConfig(TYPE, Acknowledge.class);
		nc.field(PK_ACCOUNT_ID).field(PK_CATEGORY).field(PK_OBJECT_ID).field(PK_ACKNOWLEDGED, FieldType.DATE);

	}

	public void setAccountId(String accid) {
		this.setProperty(PK_ACCOUNT_ID, accid);
	}

	public String getAccountId() {
		return this.getPropertyAsString(PK_ACCOUNT_ID);
	}

	public void setCategory(String ca) {
		this.setProperty(PK_CATEGORY, ca);
	}

	public String getCategory() {
		return (String) this.target.getProperty(PK_CATEGORY);
	}

	public void setObjectId(String ca) {
		this.setProperty(PK_OBJECT_ID, ca);
	}

	public String getObjectId() {
		return (String) this.target.getProperty(PK_OBJECT_ID);
	}

	public void setAcknowledged(Date ts) {
		this.target.setProperty(PK_ACKNOWLEDGED, ts);
	}

	public Date getAcknowledged() {
		return (Date) this.target.getProperty(PK_ACKNOWLEDGED);
	}
}
