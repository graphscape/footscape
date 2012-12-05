/**
 * Jul 31, 2012
 */
package com.fs.admin.impl;

import com.fs.admin.impl.neo4j.Neo4jAdminImpl;
import com.fs.commons.api.ActiveContext;
import com.fs.commons.api.support.SPISupport;

/**
 * @author wu
 * TODO JMX based admin.
 */
public class AdminSPI extends SPISupport {

	/** */
	public AdminSPI(String id) {
		super(id);

	}

	/* */
	@Override
	public void active(ActiveContext ac) {
		Neo4jAdminImpl na = new Neo4jAdminImpl();
		ac.active("NEO4J_ADMIN", na);
	}

	/* */
	@Override
	public void deactive(ActiveContext ac) {
	}

}
