/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 26, 2012
 */
package com.fs.dataservice.api.core;

/**
 * @author wu
 * 
 */
public interface NodeI extends RowI {

	// NOTE the property cannot start with '_' for elastic reserved some
	// properties such as '_type' '_uid' etc.

	public static final String PK_UNIQUE_ID = "uniqueId_";// system global
															// unique.
	public static final String PK_TYPE = "type_";
	public static final String PK_ID = "id_";// business id set from outer
	public static final String PK_TIMESTAMP = "timestamp_";

	public String getUniqueId();// unique in the same type.

	public String getId();// another id that may assigned by user.

	public String getTimestamp();

	public NodeType getType();

}
