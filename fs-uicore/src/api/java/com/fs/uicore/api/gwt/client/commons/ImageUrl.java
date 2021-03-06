/**
 *  
 */
package com.fs.uicore.api.gwt.client.commons;

import com.fs.uicore.api.gwt.client.UiClientI;
import com.fs.uicore.api.gwt.client.consts.UiConstants;
import com.fs.uicore.api.gwt.client.util.ObjectUtil;
import com.google.gwt.http.client.URL;

/**
 * @author wu
 * 
 */
public class ImageUrl {

	public static final String PRO_IID = "iid";// internal id.
	public static final String PRO_RES = "res";// resource.

	private String protocol;

	private String format;

	private String encode;

	private String data;

	public static ImageUrl NONE = new ImageUrl("none", null, null, null);

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

	public boolean isNone() {
		return this.equals(NONE);
	}

	@Override
	public String toString() {
		if (this.isNone()) {
			return NONE.protocol;
		}
		return this.protocol + ":" + this.format + ";" + this.encode + "," + this.data;
	}

	public String getAsSrc(UiClientI uic) {
		if (this.isNone()) {
			return "";
		}

		String rt = this.toString();
		if (this.protocol.equals("data")) {
			return rt;
		}
		// encode,and get from http.
		String uri = uic.getParameter(UiConstants.PK_IMAGES_URI, true);
		rt = URL.encodePathSegment(rt);
		rt = uri + "/" + rt;

		return rt;
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
		if (NONE.protocol.equals(str)) {
			return NONE;
		}
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
