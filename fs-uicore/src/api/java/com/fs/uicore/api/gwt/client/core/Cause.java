/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 14, 2013
 */
package com.fs.uicore.api.gwt.client.core;

/**
 * @author wu
 *
 */
public class Cause {
	private Cause parent;
	
	private Object value;
	
	private Cause(Object value){
		this(null,value);
	}	
	
	private Cause(Cause parent,Object value){
		this.parent = parent;
		this.value = value;
	}
	
	public static Cause valueOf(Object value){
		return new Cause(value);
	}
	
	public Cause getChild(Object value){
		return new Cause(this,value);
	}
	
	

	/**
	 * @return the parent
	 */
	public Cause getParent() {
		return parent;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}



	/*
	 *Apr 14, 2013
	 */
	@Override
	public String toString() {
		return (this.parent == null?"":this.parent.toString())+"/"+this.value;
	}
	
}
