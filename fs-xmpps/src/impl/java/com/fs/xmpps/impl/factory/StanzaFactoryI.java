/**
 * All right is from Author of the file,to be explained in comming days.
 * Aug 9, 2012
 */
package com.fs.xmpps.impl.factory;

import tigase.jaxmpp.core.client.xml.Element;

import com.fs.xmpps.api.StanzaI;
import com.fs.xmpps.impl.XmppImpl;

/**
 * @author wu
 * 
 */
public interface StanzaFactoryI<T extends StanzaI> {
	
	public T create(Element ele, XmppImpl xcf);
	
}
