/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 13, 2012
 */
package com.fs.expector.api;

/**
 * @author wu
 * 
 */
public interface ManagerI<T extends MemberI> {

	public String getName();

	public T getMember(String id);

	public T getMember(String id, boolean force);

	public String addMember(T t);

	public T newMember(String id);

	public T newMember();

}
