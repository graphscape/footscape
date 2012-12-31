/**
 *  Dec 31, 2012
 */
package com.fs.commons.impl.struct;

import java.util.ArrayList;
import java.util.List;

import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.struct.Node;
import com.fs.commons.api.struct.Path;
import com.fs.commons.api.struct.Tree;

/**
 * @author wuzhen
 * 
 */
public class TreeImpl<T> extends Tree<T> {

	private Node<T> root = new Node<T>(null);

	@Override
	public Node<T> addNode(Path p) {

		Path pp = p.getParent();
		Node<T> pn = this.getNode(pp);
		if (pn == null) {
			pn = addNode(pp);
		}
		// parent got
		String name = p.getName();
		Node<T> old = pn.getChild(name);
		if (old != null) {
			throw new FsException("node existed for path:" + p);
		}
		return pn.addChild(name);
	}

	@Override
	public Node<T> getNode(Path p) {
		List<String> nl = p.getNameList();
		return root.getNode(nl);
	}

	@Override
	public Node<T> getOrCreateNode(Path p) {
		Node<T> rt = this.getNode(p);
		if (rt != null) {
			return rt;
		}
		rt = this.addNode(p);
		return rt;
	}

	@Override
	public List<Node<T>> getChildNodeList(Path p) {
		Node<T> node = this.getNode(p);
		if (node == null) {
			return new ArrayList<Node<T>>();
		}
		return node.getChildList();
	}

	@Override
	public Node<T> addNode(Path p, T t) {
		// TODO Auto-generated method stub
		Node<T> rt = this.addNode(p);
		rt.setTarget(t);
		return rt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.struct.Tree#getTarget(com.fs.commons.api.struct.Path)
	 */
	@Override
	public T getTarget(Path p) {
		Node<T> rt = this.getNode(p);
		return rt == null ? null : rt.getTarget();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.commons.api.struct.Tree#getTargetListInPath(com.fs.commons.api
	 * .struct.Path)
	 */
	@Override
	public List<T> getTargetListInPath(Path p) {
		List<T> rt = new ArrayList<T>();
		Path tp = p;
		while (true) {
			T t = this.getTarget(tp);
			rt.add(0, t);
			if (tp.isRoot()) {
				break;
			}
			tp = tp.getParent();//
		}
		return rt;
	}

}
