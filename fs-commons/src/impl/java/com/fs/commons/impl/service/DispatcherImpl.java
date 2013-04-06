/**
 *  Dec 31, 2012
 */
package com.fs.commons.impl.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.service.DispatcherI;
import com.fs.commons.api.service.HandlerI;
import com.fs.commons.api.struct.Node;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.struct.Tree;
import com.fs.commons.api.support.HasContainerSupport;

/**
 * @author wuzhen
 * 
 */
public class DispatcherImpl<T> extends HasContainerSupport implements DispatcherI<T> {

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
			if (this.strict && p.equals(this.path) || !this.strict && path.isSubPath(p, true)) {
				this.target.handle(sc);
				return true;
			}
			return false;
		}

	}

	protected static final Logger LOG = LoggerFactory.getLogger(DispatcherImpl.class);

	private Tree<PathEntyHandler<T>> tree;

	private String name;

	public DispatcherImpl(String name) {
		this.name = name;
		this.tree = Tree.newInstance();
	}

	@Override
	public void active(ActiveContext ac) {
		super.active(ac);
	}

	@Override
	public void dispatch(Path p, T ctx) {

		List<PathEntyHandler<T>> chL = this.tree.getTargetListInPath(p);
		int count = 0;
		for (PathEntyHandler<T> ch : chL) {
			if (ch == null) {
				continue;
			}
			count += ch.handle(p, ctx);
		}

		if (count == 0) {
			LOG.warn("dispatcher:" + this.name + " has no handler/s for ctx:" + ctx + " with path:" + p);
		}
	}

	@Override
	public void addHandler(String cfgId, Path p, HandlerI<T> h) {
		this.addHandler(cfgId, p, false, h);
	}

	@Override
	public void addHandler(String cfgId, Path p, boolean strict, HandlerI<T> h) {
		Configuration cfg = cfgId == null ? null : Configuration.properties(cfgId);
		this.addHandler(cfg, p, strict, h);
	}

	public void addHandler(Configuration cfg, Path p, boolean strict, HandlerI<T> h) {
		Node<PathEntyHandler<T>> node = this.tree.getOrCreateNode(p);
		PathEntyHandler<T> cl = node.getTarget();
		if (cl == null) {
			cl = new PathEntyHandler<T>();
			node.setTarget(cl);
		}
		cl.addHandler(p, strict, h);
		ActiveContext ac = this.activeContext.newActiveContext(internal);

		ac.active(cfg, h);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.service.DispatcherI#addHandler(java.lang.String)
	 */
	@Override
	public void addHandler(String cfgId) {
		Configuration cfg = Configuration.properties(cfgId);
		String pathS = cfg.getProperty("path", true);
		boolean strict = cfg.getPropertyAsBoolean("strict", false);
		HandlerI<T> h = cfg.getPropertyAsNewInstance("class", true);

		Path p = Path.valueOf(pathS);
		this.addHandler(cfg, p, strict, h);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.service.DispatcherI#addHandlers(java.lang.String,
	 * java.lang.String[])
	 */
	@Override
	public void addHandlers(String prefix, String[] names) {
		for (String name : names) {
			String cfg = prefix + "." + name;
			this.addHandler(cfg);
		}
	}

	/*
	 * Dec 31, 2012
	 */
	@Override
	public void addHandler(Path p, HandlerI<T> h) {
		this.addHandler(null, p, h);
	}

	/*
	 * Dec 31, 2012
	 */
	@Override
	public void addHandler(Path p, boolean strict, HandlerI<T> h) {
		this.addHandler((String) null, p, strict, h);
	}

}
