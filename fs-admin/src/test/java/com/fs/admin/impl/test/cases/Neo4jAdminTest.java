/**
 * Jul 31, 2012
 */
package com.fs.admin.impl.test.cases;

import com.fs.admin.api.neo4j.Neo4jAdminI;
import com.fs.admin.impl.test.cases.support.TestBase;

/**
 * @author wu
 * 
 */
public class Neo4jAdminTest extends TestBase {

	public void testResetNeo4jServer() {
		Neo4jAdminI na = this.container.find(Neo4jAdminI.class, true);
		na.resetDB();//
	}
}
