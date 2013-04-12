/**
 *  
 */
package com.fs.commons.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;

import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class FileUtil {

	public static String readAsString(InputStream is, String encode) {
		try {
			Reader reader = new InputStreamReader(is, encode);
			return readAsString(reader);
		} catch (UnsupportedEncodingException e) {
			throw new FsException(e);
		}

	}

	/**
	 * @param reader
	 * @deprecated should use writer
	 */
	public static String readAsString(Reader reader) {
		StringBuffer sb = new StringBuffer();
		char[] cbuf = new char[1024];
		while (true) {
			try {
				int l = reader.read(cbuf);
				if (l == -1) {
					break;
				}
				sb.append(cbuf, 0, l);
			} catch (IOException e) {
				throw new FsException(e);

			}
		}
		return sb.toString();
	}
}
