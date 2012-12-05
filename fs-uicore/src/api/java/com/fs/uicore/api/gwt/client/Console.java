/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 11, 2012
 */
package com.fs.uicore.api.gwt.client;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.core.UiCallbackI;

/**
 * @author wu
 * 
 */
public class Console {

	private static Console ME = new Console();

	private List<UiCallbackI<Object, Boolean>> messageCallbackList = new ArrayList<UiCallbackI<Object, Boolean>>();

	public static Console getInstance() {
		return ME;
	}

	public void addMessageCallback(UiCallbackI<Object, Boolean> cb) {
		this.messageCallbackList.add(cb);
	}

	public void removeMessageCallback(UiCallbackI<Object, Boolean> cb) {
		this.messageCallbackList.remove(cb);
	}

	public void println(Object obj) {
		System.out.println(obj);
		for (UiCallbackI<Object, Boolean> cb : this.messageCallbackList) {
			try {
				cb.execute(obj);
			} catch (Throwable t) {
				System.out
						.println(Console.class.getName()
								+ ",error when dispatch println in console to callback:"
								+ cb + ",throwable:" + t);
				t.printStackTrace();
			}
		}

	}

}
