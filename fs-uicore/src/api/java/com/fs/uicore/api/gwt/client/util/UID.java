package com.fs.uicore.api.gwt.client.util;

public class UID {

	// TODO uuid generate.
	private static int index;

	public static String create() {
		return create("");
	}

	public static String create(String pre) {
		return pre + (index++);
	}
}
