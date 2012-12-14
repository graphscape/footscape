/**
 *  Dec 14, 2012
 */
package com.fs.expector.impl.dg.support;

import com.fs.datagrid.api.wrapper.PropertiesDW;
import com.fs.expector.api.MemberI;

/**
 * @author wuzhen
 * 
 */
public class MemberPropertiesDWSupport extends PropertiesDW implements MemberI {

	public static final String ID = "_id";

	public MemberPropertiesDWSupport() {

	}

	public MemberPropertiesDWSupport(String id) {
		this.setProperty(ID, id);
	}

	@Override
	public String getId() {
		return (String) this.getProperty(ID, true);
	}

}
