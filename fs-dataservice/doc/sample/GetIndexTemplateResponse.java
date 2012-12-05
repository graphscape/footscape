/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 1, 2012
 */
package com.fs.dataservice.core.impl.elastic.ext;

import java.io.IOException;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.io.stream.Streamable;

/**
 * @author wu
 * 
 */
public class GetIndexTemplateResponse implements ActionResponse, Streamable {

	
	/*
	 * Nov 1, 2012
	 */
	@Override
	public void readFrom(StreamInput si) throws IOException {
		si.readString();
	}

	/*
	 * Nov 1, 2012
	 */
	@Override
	public void writeTo(StreamOutput streamoutput) throws IOException {
		//

	}

}
