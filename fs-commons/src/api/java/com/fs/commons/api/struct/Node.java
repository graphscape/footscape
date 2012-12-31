/**
 *  Dec 31, 2012
 */
package com.fs.commons.api.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fs.commons.api.support.MapProperties;

/**
 * @author wuzhen
 * 
 */
public class Node<T> extends MapProperties<Object> {

	private String name;

	private Map<String, Node<T>> nodeMap = new HashMap<String, Node<T>>();

	public T setTarget(T target) {
		T rt = this.target;
		this.target = target;
		return rt;
	}

	private T target;

	public Node(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Node<T>> getChildList() {
		return new ArrayList<Node<T>>(this.nodeMap.values());
	}

	public Node<T> getNode(List<String> nl) {
		Node<T> rt = this;

		for (int i = 0; i < nl.size(); i++) {

			String name = nl.get(i);
			rt = rt.getChild(name);
			if (rt == null) {
				break;
			}
		}
		return rt;
	}

	public Node<T> getChild(String name) {
		return this.nodeMap.get(name);
	}

	/**
	 * @param name2
	 */
	public Node<T> addChild(String name2) {
		Node<T> rt = new Node<T>(name2);
		this.nodeMap.put(name2, rt);
		return rt;
	}

	public T getTarget() {
		return target;
	}

}
