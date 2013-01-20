/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 20, 2013
 */
package com.fs.dataservice.api.core.meta;

/**
 * @author wu
 * 
 */
public class AnalyzerType extends com.fs.commons.api.Enum {

	public static final AnalyzerType TEXT = AnalyzerType.valueOf("text");

	/**
	 * @param v
	 */
	protected AnalyzerType(String v) {
		super(v);
	}

	public static final AnalyzerType valueOf(String v) {
		return new AnalyzerType(v);
	}

}
