/**
 * Jul 31, 2012
 */
package com.fs.admin.impl.neo4j;

import com.fs.admin.api.neo4j.Neo4jAdminI;
import com.fs.commons.api.config.support.ConfigurableSupport;
import com.fs.commons.api.exec.ExecutorI;
import com.fs.commons.api.lang.FsException;

/**
 * @author wu
 * 
 */
public class Neo4jAdminImpl extends ConfigurableSupport implements Neo4jAdminI {

	/* */
	@Override
	public void resetDB() {

		this.execInSsh(new String[] { "service", "neo4j-service", "stop" });

		String neohome = this.config.getProperty("neo4j.home", true);
		this.execInSsh(new String[] { "rm", "-r", neohome + "/data/graph.db" });// TODO
		// configurable?
		this.execInSsh(new String[] { "service", "neo4j-service", "start" });

	}

	protected int execInSsh(String[] cmds) {
		ExecutorI.FactoryI ef = this.container.find(ExecutorI.FactoryI.class,
				true);
		ExecutorI e = ef.create("ssh");
		String user = System.getProperty("user.name");// TODO remote user?
		e.addArgument(user + "@localhost");
		for (int i = 0; i < cmds.length; i++) {
			e.addArgument(cmds[i]);
		}

		return e.run();
	}
}
