/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright (c) 2012 Author of the file, All rights reserved.
 *
 * Jul 5, 2012
 */
package com.fs.commons.api.trace;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author wuzhen
 * 
 */
public class Trace {

	public abstract static class Entry {

		private int depth;

		/**
		 * @param dep
		 * @param b
		 * @param source2
		 */
		public Entry(int dep) {
			//
			this.depth = dep;
			//
		}

		public int getDepth() {
			return depth;
		}

		public abstract Object getSource();

	}

	public static class Before extends Entry {

		private Object source;

		private Trace trace;

		public Before(Trace t, int dep, Object source2) {
			super(dep);
			this.trace = t;
			this.source = source2;
		}

		public After after() {
			Before before = this.trace.stack.pop();

			int dep = this.trace.stack.size();

			After e = new After(before);

			this.trace.entryList.add(e);
			return e;

		}

		public Object getSource() {
			return this.source;
		}

	}

	public static class After extends Entry {
		private Before before;

		public After(Before bef) {
			super(bef.getDepth());
			this.before = bef;
		}

		public Object getSource() {
			return this.before.getSource();
		}
	}

	private Stack<Before> stack = new Stack<Before>();

	private List<Entry> entryList = new ArrayList<Entry>();

	public Before before(Object source) {
		int dep = this.stack.size();
		Before rt = new Before(this, dep, source);
		this.stack.push(rt);
		this.entryList.add(rt);
		return rt;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n");
		for (Entry e : this.entryList) {

			for (int i = 0; i < e.depth; i++) {
				sb.append("    ");
			}
			String s = (e instanceof Before) ? ">" : "<";
			sb.append(s);
			sb.append("   ");
			sb.append(e.getSource());
			sb.append("\n");
		}
		return sb.toString();
	}

}
