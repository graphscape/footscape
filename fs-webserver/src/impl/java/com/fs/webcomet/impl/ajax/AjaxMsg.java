/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package com.fs.webcomet.impl.ajax;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.fs.commons.api.struct.Path;
import com.fs.commons.api.support.MapProperties;

/**
 * @author wu
 *
 */
public class AjaxMsg extends MapProperties<String>{

	public static final Path CONNECT = Path.valueOf("ajax/connect");
	
	public static final Path CLOSE = Path.valueOf("ajax/close");
	
	public static final Path MESSAGE = Path.valueOf("ajax/message");
	
	public static final Path ERROR = Path.valueOf("ajax/error");
	
	public static final String PK_PATH = "_path";

	public static final String PK_SESSION_ID = "_session_id";
	
	public static final String PK_TEXTMESSAGE = "_text_message";
	
	public AjaxMsg(Path path){
		this(path,null);
	}
	
	public AjaxMsg(Path path, String sid){
		this.setProperty(PK_PATH, path.toString());
		if(sid != null){
			this.setProperty(PK_SESSION_ID, sid);
		}
	}
	
	public boolean isPath(Path path){
		return path.equals(this.getPath());
	}
	
	public String getSessionId(boolean force){
		return this.getProperty(PK_SESSION_ID,force);
	}
	
	public Path getPath(){
		String ps = this.getProperty(PK_PATH,true);
		return Path.valueOf(ps);
	}
	
	/**
	 *May 8, 2013
	 */
	public static AjaxMsg tryParse(Reader reader) {
		// 
		JSONObject jo = (JSONObject) JSONValue.parse(reader);
		String pathS = (String)jo.get(PK_PATH);
		if(pathS == null){
			return null;
		}
		Path path = Path.valueOf(pathS);
		AjaxMsg rt = new AjaxMsg(path);
		rt.setProperties(jo);
		return rt
				;
	}

	
}