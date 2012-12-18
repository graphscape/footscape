/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.gridservice.core.impl.hazelcast.codec;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONValue;

import com.fs.commons.api.codec.CodecI;
import com.fs.commons.api.value.PropertiesI;
import com.hazelcast.nio.DataSerializable;

/**
 * @author wu
 * 
 */
public class PropertiesData implements DataSerializable {

	public static CodecI CODEC;

	protected PropertiesI<Object> data;

	/**
	 * @param pts
	 */
	public PropertiesData() {

	}

	/*
	 * Dec 13, 2012
	 */
	@Override
	public void writeData(DataOutput out) throws IOException {
		//

		JSONArray json = (JSONArray) CODEC.encode(this.data);
		out.writeUTF(json.toJSONString());

	}

	/*
	 * Dec 13, 2012
	 */
	@Override
	public void readData(DataInput in) throws IOException {
		String jsonS = in.readUTF();
		JSONArray ser = (JSONArray) JSONValue.parse(jsonS);
		this.data = (PropertiesI<Object>) CODEC.decode(ser);
	}

	/**
	 * @return the data
	 */
	public PropertiesI<Object> getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(PropertiesI<Object> data) {
		this.data = data;
	}

}
