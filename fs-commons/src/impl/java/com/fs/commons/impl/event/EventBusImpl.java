/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 17, 2012
 */
package com.fs.commons.impl.event;

import java.util.HashMap;
import java.util.Map;

import com.fs.commons.api.Event;
import com.fs.commons.api.event.EventBusI;
import com.fs.commons.api.event.ListenerI;
import com.fs.commons.api.support.CollectionListener;

/**
 * @author wu
 * 
 */
public class EventBusImpl implements EventBusI {

	protected Map<Class,CollectionListener> listenerMap;
	
	public EventBusImpl(){
		this.listenerMap = new HashMap<Class,CollectionListener>();
	}
	
	/*
	 * Dec 17, 2012
	 */
	@Override
	public <T extends Event> void addListener(Class<T> eventType, ListenerI<T> l) {
		//
		CollectionListener cl = this.listenerMap.get(eventType);
		if(cl == null){
			cl = new CollectionListener<Event>();
			this.listenerMap.put(eventType, cl);
		}
		cl.addListener(l);
	}

	@Override
	public void put(Event e) {
		
		for(Class et:this.listenerMap.keySet()){
			if(!et.isInstance(e)){
				continue;
			}
			CollectionListener<Event> cl = this.listenerMap.get(et);
			cl.handle(e);
		}
	}

}
