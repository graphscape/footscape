/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 2, 2012
 */
package com.fs.uicore.api.gwt.client.reflect;

import java.util.HashMap;
import java.util.Map;

import com.fs.uicore.api.gwt.client.UiException;

/**
 * @author wu
 *
 */
public class GwtWrapper {

	public static interface CreaterI<T>{
		public T create();
	}

	private static Map<String,CreaterI> createrMap = new HashMap<String,CreaterI>();
	
	public static <T> T create(Class<T> cls){
		CreaterI crt = createrMap.get(cls.getName());
		if(crt == null){
			throw new UiException("no creater for:"+cls);
		}
		return (T)crt.create();
	}
}
