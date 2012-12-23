/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package com.fs.uicommons.api.gwt.client.gchat.wrapper;

import com.fs.uicore.api.gwt.client.data.message.MessageData;

/**
 * @author wu
 * 
 */
public class TextMW extends GChatMW {

	/**
	 * @param md
	 */
	public TextMW(MessageData md) {
		super(md);
	}


	public String getText() {
		return this.target.getString("text", true);
	}

}
