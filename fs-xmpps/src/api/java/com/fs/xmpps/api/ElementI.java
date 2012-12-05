/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.xmpps.api;

/**
 * @author wu
 * 
 */
public interface ElementI {

	public void setXmlns(String xmlns);
	
	public String getXmlns();
	
	public String getName();

	public void setAttribute(String name, String value);

	public String getAttribute(String name);

	public void setText(String value);

	public ElementI addChild(String name);

	public ElementI getChild(String name, boolean force);

	public ElementI getOrAddChild(String name);

	public void setChildText(String name, String value);

}
