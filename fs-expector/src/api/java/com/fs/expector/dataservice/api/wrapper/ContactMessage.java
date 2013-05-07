/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package com.fs.expector.dataservice.api.wrapper;

import com.fs.dataservice.api.core.meta.DataSchema;
import com.fs.dataservice.api.core.wrapper.NodeWrapper;
import com.fs.expector.dataservice.api.NodeTypes;

/**
 * @author wu
 * 
 */
public class ContactMessage extends NodeWrapper {

	public static final String STATUS = "status";
	
	public static final String NAME = "name";

	public static final String EMAIL = "email";

	public static final String SUBJECT = "subject";

	public static final String BODY = "body";

	/**
	 * @param ntype
	 * @param pts
	 */
	public ContactMessage() {
		super(NodeTypes.EXPECTATION);
	}

	public String getBody() {
		return (String) this.getProperty(BODY);
	}

	public void setBody(String value) {
		this.setProperty(BODY, value);
	}

	public String getSubject() {
		return (String) this.getProperty(SUBJECT);
	}

	public void setSubject(String value) {
		this.setProperty(SUBJECT, value);
	}
	
	public void setStatus(String st){
		this.setProperty(STATUS, st);
	}

	public void setName(String value) {
		this.setProperty(NAME, value);
	}

	public void setEmail(String value) {
		this.setProperty(EMAIL, value);
	}

	/**
	 * Nov 2, 2012
	 */
	public static void config(DataSchema cfs) {
		cfs.addConfig(NodeTypes.CONTACT_MESSAGE, ContactMessage.class)//
				.field(SUBJECT)//
				.field(NAME)//
				.field(EMAIL)//
				.field(BODY)//
				.field(STATUS)//
		;
	}

}
