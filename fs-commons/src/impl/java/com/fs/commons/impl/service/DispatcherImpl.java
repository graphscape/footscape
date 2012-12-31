/**
 *  Dec 31, 2012
 */
package com.fs.commons.impl.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.struct.Node;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.struct.Tree;

/**
 * @author wuzhen
 * 
 */
public class DispatcherImpl<T> implements DispatcherI<T> {

	public static class PathEntyHandler<T> {

		protected List<HandlerEntry<T>> handlers = new ArrayList<HandlerEntry<T>>();

		public void addHandler(Path p, boolean strict, HandlerI<T> h) {

			this.handlers.add(new HandlerEntry(p, strict, h));

		}

		public int handle(Path p, T sc) {
			int rt = 0;
			for (HandlerEntry<T> h : handlers) {
				boolean hs = h.tryHandle(p, sc);

				if (hs) {
					rt++;
				}

			}
			return rt;
		}

	}

	public static class HandlerEntry<T> {

		private Path path;

		private boolean strict;

		private HandlerI<T> target;

		public HandlerEntry(Path p, boolean strict, HandlerI<T> h) {
			this.path = p;
			this.strict = strict;
			this.target = h;
		}

		public boolean tryHandle(Path p, T sc) {
			if (this.strict && p.equals(this.path) || !this.strict
					&& path.isSubPath(p, true)) {
				this.target.handle(sc);
				return true;
			}
			return false;
		}

	}

	protected static final Logger LOG = LoggerFactory
			.getLogger(DispatcherImpl.class);

	private Tree<PathEntyHandler<T>> tree;

	private String name;

	public DispatcherImpl(String name) {
		this.name = name;
		this.tree = Tree.newInstance();
	}

	@Override
	public void dispatch(Path p, T ctx) {

		List<PathEntyHandler<T>> chL = this.tree.getTargetListInPath(p);
		int count = 0;
		for (PathEntyHandler<T> ch : chL) {
			if(ch == null){
				continue;
			}
			count += ch.handle(p, ctx);
		}

		if (count == 0) {
			LOG.warn("no handler/s for ctx:" + ctx + " with path:" + p
					+ " in dispatcher:" + this.name);
		}
	}

	@Override
	public void addHandler(Path p, HandlerI<T> h) {
		this.addHandler(p, false, h);
	}

	@Override
	public void addHandler(Path p, boolean strict, HandlerI<T> h) {
		Node<PathEntyHandler<T>> node = this.tree.getOrCreateNode(p);
		PathEntyHandler<T> cl = node.getTarget();
		if (cl == null) {
			cl = new PathEntyHandler<T>();
			node.setTarget(cl);
		}
		cl.addHandler(p, strict, h);
	}

}
