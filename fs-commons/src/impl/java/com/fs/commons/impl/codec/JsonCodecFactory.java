/**
 * Jun 23, 2012
 */
package com.fs.commons.impl.codec;

import com.fs.commons.api.codec.support.CodecFactorySupport;
import com.fs.commons.impl.codec.json.BooleanJCS;
import com.fs.commons.impl.codec.json.DateJCS;
import com.fs.commons.impl.codec.json.ErrorInfoJCS;
import com.fs.commons.impl.codec.json.ErrorInfosJCS;
import com.fs.commons.impl.codec.json.IntegerJCS;
import com.fs.commons.impl.codec.json.LongJCS;
import com.fs.commons.impl.codec.json.MessageJCS;
import com.fs.commons.impl.codec.json.ObjectListJCS;
import com.fs.commons.impl.codec.json.ObjectPropertiesJCS;
import com.fs.commons.impl.codec.json.StringJCS;

/**
 * @author wu
 * 
 */
public class JsonCodecFactory extends CodecFactorySupport {

	public JsonCodecFactory() {
		this.add(new ErrorInfoJCS(this));
		this.add(new ErrorInfosJCS(this));

		this.add(new ObjectPropertiesJCS(this));
		this.add(new ObjectListJCS(this));
		this.add(new IntegerJCS(this));
		this.add(new StringJCS(this));
		this.add(new BooleanJCS(this));
		this.add(new DateJCS(this));
		this.add(new LongJCS(this));//
		this.add(new MessageJCS(this));

	}

}
