/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 8, 2012
 */
package com.fs.uixmpp.core.api.gwt.client;

import java.util.List;

/**
 * @author wu
 * 
 */
public interface ElementI {

	public String getName();

	public String getXmlns();

	public void setXmlns(String xmlns);

	public void setAttribute(String name, String value);

	public String getAttribute(String name);

	public int getAttributeAsInt(String name);
	
	public String getText();

	public void setText(String value);

	public ElementI addChild(String name);

	public ElementI getChild(String name, boolean force);
	
	public List<ElementI> getChildList(String name);

	public ElementI getOrAddChild(String name);

	public void setChildText(String name, String value);

}
