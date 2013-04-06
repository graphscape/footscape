/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.gridservice.commons.api.data;

import java.util.UUID;

import com.fs.commons.api.message.MessageI;
import com.fs.commons.api.struct.Path;
import com.fs.gridservice.commons.api.EventType;
import com.fs.gridservice.commons.api.GridedDataI;
import com.fs.gridservice.core.api.gdata.MessageGd;

/**
 * @author wu
 * 
 */
public class EventGd extends MessageGd implements GridedDataI {

	public static final String HK_TYPE = "_type";

	public static final String HK_ID = MessageI.HK_ID;

	public static final String HK_SOURCE_PATH = "_source_path";

	public EventGd() {

	}

	public EventGd(MessageI msg) {
		super(msg);
	}

	public EventGd(EventType type, Path path, Path spath) {
		this(type, path, UUID.randomUUID().toString(), spath);//
	}

	public EventGd(EventType type, Path path, String id, Path spath) {
		this.setHeader(HK_TYPE, type.name());
		this.setHeader(HK_PATH, path.toString());
		this.setHeader(HK_ID, id);
		this.setHeader(HK_SOURCE_PATH, spath == null ? null : spath.toString());
	}

	public EventType getType() {
		String ts = this.getHeader(HK_TYPE, true);
		return EventType.valueOf(ts);
	}

	public Path getSourcePath() {
		String pS = this.getHeader(HK_SOURCE_PATH, true);
		return Path.valueOf(pS);
	}

	public String getId() {
		return this.getHeader(HK_ID, true);
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
