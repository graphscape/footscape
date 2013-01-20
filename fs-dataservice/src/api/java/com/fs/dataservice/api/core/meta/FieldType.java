/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 20, 2013
 */
package com.fs.dataservice.api.core.meta;

/**
 * @author wu
 * 
 */
public class FieldType extends com.fs.commons.api.Enum {

	public static final FieldType STRING = FieldType.valueOf("string");
	
	public static final FieldType LONG = FieldType.valueOf("long");
	
	public static final FieldType INTEGER = FieldType.valueOf("integer");
	
	public static final FieldType DATE = FieldType.valueOf("date");

	/**
	 * @param v
	 */
	protected FieldType(String v) {
		super(v);
	}

	public static final FieldType valueOf(String v) {
		return new FieldType(v);
	}

}
