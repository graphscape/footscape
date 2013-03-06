/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 29, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class ExpMessage extends NodeWrapper {

	public static final String EXP_ID1 = "expId";

	public static final String EXP_ID2 = "expId2";

	public static final String ACCOUNT_ID1 = "accountId1";

	public static final String ACCOUNT_ID2 = "accountId2";

	public static final String PATH = "path";

	public static final String HEADER = "header";

	public static final String BODY = "body";

	/**
	 * @param ntype
	 */
	public ExpMessage() {
		super(NodeTypes.EXPMESSAGE);
	}

	public void setExpId1(String expId) {
		this.setProperty(EXP_ID1, expId);

	}

	public String getExpId1() {
		return (String) this.getProperty(EXP_ID1);
	}

	public String getExpId2() {
		return (String) this.getProperty(EXP_ID2);
	}

	public void setExpId2(String expId) {
		this.setProperty(EXP_ID2, expId);

	}

	/**
	 * Nov 2, 2012
	 */
	public static void config(DataSchema cfs) {
		cfs.addConfig(NodeTypes.EXPMESSAGE, ExpMessage.class).field(EXP_ID1).field(EXP_ID2)
				.field(ACCOUNT_ID2).field(ACCOUNT_ID1).field(PATH).field(HEADER).field(BODY);

	}

	public void setAccountId1(String accId1) {
		this.setProperty(ACCOUNT_ID1, accId1);
	}

	public String getAccountId1() {
		return this.getPropertyAsString(ACCOUNT_ID1);
	}

	public void setAccountId2(String accId2) {
		this.setProperty(ACCOUNT_ID2, accId2);
	}

	public String getAccountId2() {
		return this.getPropertyAsString(ACCOUNT_ID2);
	}

	public void setHeader(String header) {
		this.setProperty(HEADER, header);
	}

	public String getHeader() {
		return this.getPropertyAsString(HEADER);
	}

	public void setBody(String body) {
		this.setProperty(BODY, body);
	}

	public String getBody() {
		return this.getPropertyAsString(BODY);
	}

	public void setPath(String path) {
		this.setProperty(PATH, path);
	}

	public String getPath() {
		return this.getPropertyAsString(PATH);
	}

}
