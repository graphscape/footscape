/**
 *  Dec 31, 2012
 */
package com.fs.commons.api.struct;

import java.util.List;

import com.fs.commons.api.lang.ClassUtil;

/**
 * @author wuzhen
 * 
 */
public abstract class Tree<T> {

	public abstract Node<T> addNode(Path p);

	public abstract Node<T> addNode(Path p, T t);

	public abstract Node<T> getNode(Path p);
	
	public abstract T getTarget(Path p);
	
	public abstract List<T> getTargetListInPath(Path p);

	public abstract List<Node<T>> getChildNodeList(Path p);

	public abstract Node<T> getOrCreateNode(Path p);

	/**
	 * @return
	 */
	public static <X> Tree<X> newInstance() {
		return (Tree<X>) ClassUtil
				.newInstance("com.fs.commons.impl.struct.TreeImpl");

	}

}
