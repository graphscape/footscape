/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 7, 2012
 */
package com.fs.uiclient.api.gwt.client.util;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.data.ListData;
import com.fs.uicore.api.gwt.client.data.basic.StringData;
import com.fs.uicore.api.gwt.client.data.list.ObjectListData;

/**
 * @author wu
 * 
 */
public class ListDataUtil {

	public static List<String> toStringList(ListData<StringData> sdL) {

		List<String> rt = new ArrayList<String>();

		for (int i = 0; i < sdL.size(); i++) {
			StringData sd = sdL.get(i);
			String v = sd.getValue();
			rt.add(v);
		}

		return rt;
	}

	public static ObjectListData toStringDataList(List<String> sl) {
		if (sl == null) {
			return null;
		}
		ObjectListData rt = new ObjectListData();
		for (String s : sl) {
			rt.add(StringData.valueOf(s));
		}

		return rt;
	}
}
