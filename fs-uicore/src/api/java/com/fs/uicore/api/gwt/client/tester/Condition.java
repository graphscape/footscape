/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jun 29, 2012
 */
package com.fs.uicore.api.gwt.client.tester;

import com.fs.uicore.api.gwt.client.core.Event;

/**
 * @author wuzhen
 * 
 */
public class Condition {

	private Class<Event> eventType;

	private String eventId;
	
	
	public Condition() {

	}

	public Condition eventType(Class<Event> ec) {
		this.eventType = ec;
		return this;
	}
}
