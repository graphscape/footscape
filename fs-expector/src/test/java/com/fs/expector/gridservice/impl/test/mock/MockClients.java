/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
 */
package com.fs.expector.gridservice.impl.test.mock;

import com.fs.commons.api.ContainerI;

/**
 * @author wu
 * 
 */
public class MockClients {

	public MockClient[] clients;

	public ContainerI container;

	public MockClients(ContainerI c) {
		this.container = c;
	}

	public void start(int size) {

		this.clients = new MockClient[size];

		for (int i = 0; i < size; i++) {
			this.clients[i] = new MockClient(this.container);
			String email = "user" + i + "@domain.com";
			String nick = "nick" + i;
			this.clients[i].start();
			this.clients[i].signupAndLogin(email, nick);

		}

	}

	public void createExpections(int size) {//

		for (int i = 0; i < this.clients.length; i++) {
			MockClient c = this.clients[i];
			for (int j = 0; j < size; j++) {
				String body = "user" + i + " expecting exp" + j;
				c.newExp(body);
			}
		}

	}

	/**
	 * Dec 6, 2012
	 */
	public MockClient getClient(int i) {
		return this.clients[i];

	}

}
