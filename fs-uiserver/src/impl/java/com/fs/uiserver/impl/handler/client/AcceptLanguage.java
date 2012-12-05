/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 30, 2012
 */
package com.fs.uiserver.impl.handler.client;

/**
 * @author wu
 * 
 */
public class AcceptLanguage {
	protected String line;

	protected String[] languages;

	public AcceptLanguage(String line) {
		this.line = line;
		this.languages = line.split(",");
	}

	public String getLocale() {
		return languages[0];// TODO analysis
	}
}
