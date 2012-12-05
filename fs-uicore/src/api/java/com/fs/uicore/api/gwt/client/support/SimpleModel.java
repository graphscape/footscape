/**
 * Jun 29, 2012
 */
package com.fs.uicore.api.gwt.client.support;

import com.fs.uicore.api.gwt.client.ModelI;

/**
 * @author wu
 * 
 */
public class SimpleModel extends ModelSupport implements ModelI {

	public SimpleModel(String name) {
		super(name);
	}

	public static SimpleModel valueOfDefaultProperty(Object def){
		return SimpleModel.valueOf(null, def);
	}
	
	public static SimpleModel valueOf(String name, Object defProperty) {
		SimpleModel rt = new SimpleModel(name);
		rt.defaultValue(defProperty);
		return rt;
	}

	public ModelI convert(String name, String... keyPairs) {
		SimpleModel rt = new SimpleModel(name);

		for (int i = 0; i < keyPairs.length / 2; i++) {
			String key = keyPairs[i * 2];
			String key2 = keyPairs[i * 2 + 1];
			Object value = this.getProperty(key);
			rt.setProperty(key2, value);
		}

		return rt;
	}
}
