/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package com.fs.uicore.api.gwt.client.mock;

import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wu
 * 
 */
public class MockSignupEvent extends Event {
	public static Type<MockSignupEvent> TYPE = new Type<MockSignupEvent>("mock-signup");
	/**
	 * @param type
	 */
	public MockSignupEvent(MockUiClient uic) {
		super(TYPE,uic);
	}
	
	public MockUiClient getMockUiClient(){
		return (MockUiClient)this.source;
	}

}
