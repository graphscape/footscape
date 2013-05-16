/**
 *  
 */
package com.fs.commons.api.util;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.lang.ObjectUtil;

/**
 * @author wu
 * 
 */
public class ImageUrl {

	private String protocol;

	private String format;

	private String encode;

	private String data;

	/**
	 * @param format
	 * @param encode
	 * @param data
	 */
	public ImageUrl(String protocol, String format, String encode, String data) {
		this.format = format;
		this.protocol = protocol;
		this.encode = encode;
		this.data = data;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getFormat() {
		return format;
	}

	public String getEncode() {
		return encode;
	}

	public String getData() {
		return data;
	}

	@Override
	public String toString() {
		return this.protocol + ":" + this.format + ";" + this.encode + "," + this.data;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || (!(obj instanceof ImageUrl))) {
			return false;
		}
		ImageUrl o2 = (ImageUrl) obj;
		return ObjectUtil.nullSafeEquals(this.format, o2.format)
				&& ObjectUtil.nullSafeEquals(this.encode, o2.encode)
				&& ObjectUtil.nullSafeEquals(this.data, o2.data)
				&& ObjectUtil.nullSafeEquals(this.protocol, o2.protocol);

	}

	// data:image/x-icon;base64,
	public static ImageUrl parse(String str, boolean force) {

		int idxCom = str.indexOf(":");
		String protocol = str.substring(0, idxCom);

		int idxSemiC = str.indexOf(";");
		String format = str.substring(protocol.length() + 1, idxSemiC);
		int idxCo = str.indexOf(",");
		String encode = str.substring(protocol.length() + format.length() + 2, idxCo);

		String data = str.substring(protocol.length() + format.length() + encode.length() + 3);
		return new ImageUrl(protocol, format, encode, data);
	}
}
