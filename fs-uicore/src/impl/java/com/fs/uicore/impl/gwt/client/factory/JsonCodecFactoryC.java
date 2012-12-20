/**
 * Jun 23, 2012
 */
package com.fs.uicore.impl.gwt.client.factory;

import com.fs.uicore.api.gwt.client.support.CodecFactorySupport;
import com.fs.uicore.impl.gwt.client.json.BooleanJCC;
import com.fs.uicore.impl.gwt.client.json.DateJCC;
import com.fs.uicore.impl.gwt.client.json.ErrorInfoJCC;
import com.fs.uicore.impl.gwt.client.json.ErrorInfosJCC;
import com.fs.uicore.impl.gwt.client.json.IntegerJCC;
import com.fs.uicore.impl.gwt.client.json.LongJCC;
import com.fs.uicore.impl.gwt.client.json.MessageJCC;
import com.fs.uicore.impl.gwt.client.json.ObjectListJCC;
import com.fs.uicore.impl.gwt.client.json.ObjectPropertiesJCC;
import com.fs.uicore.impl.gwt.client.json.StringJCC;

/**
 * @author wu
 * 
 */
public class JsonCodecFactoryC extends CodecFactorySupport {

	public JsonCodecFactoryC() {
		this.add(new ObjectPropertiesJCC(this));
		this.add(new ErrorInfoJCC(this));
		this.add(new ErrorInfosJCC(this));
		this.add(new ObjectListJCC(this));
		this.add(new MessageJCC(this));
		this.add(new IntegerJCC(this));
		this.add(new StringJCC(this));
		this.add(new BooleanJCC(this));
		this.add(new DateJCC(this));
		this.add(new LongJCC(this));

	}

}
