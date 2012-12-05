package com.fs.uicore.api.gwt.client.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fs.uicore.api.gwt.client.util.ObjectUtil;

public class Path {

	private List<String> nameList;

	public List<String> getNameList() {
		return nameList;
	}

	private Path(String... names) {
		this(Arrays.asList(names));
	}

	private Path(List<String> nl) {
		this.nameList = new ArrayList<String>(nl);
	}

	public Path getSubPath(String name) {
		List<String> names = new ArrayList<String>();

		return Path.valueOf(names);
	}

	public static Path valueOf(Path par, String name) {
		return par.getSubPath(name);
	}

	public static Path valueOf(List<String> names) {
		return new Path(names);
	}

	public static Path valueOf(String... names) {
		return new Path(names);
	}

	public int length() {
		return this.nameList.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Path)) {
			return false;
		}
		Path p2 = (Path) obj;

		for (int i = 0; i < this.nameList.size(); i++) {
			String o1 = this.nameList.get(i);
			String o2 = p2.nameList.get(i);

			if (!ObjectUtil.nullSafeEquals(o1, o2)) {
				return false;
			}

		}
		return true;
	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public String toString() {
		//
		return "class:" + this.getClass() + ",names:" + this.nameList;
	}

}
