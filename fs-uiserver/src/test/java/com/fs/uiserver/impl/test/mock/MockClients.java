/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 5, 2012
 */
package com.fs.uiserver.impl.test.mock;

import java.util.List;

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

	public void cooperRequest(int maxSize) {
		for (int i = 0; i < this.clients.length; i++) {

			MockClient c1 = this.clients[i];
			MockClient c2 = i == this.clients.length - 1 ? this.clients[0]
					: this.clients[i + 1];

			MockUserSnapshot us1 = c1.getUserSnapshot(true);
			MockUserSnapshot us2 = c2.getUserSnapshot(true);

			List<String> expList1 = us1.expIdList;
			List<String> expList2 = us2.expIdList;

			for (int j = 0; j < maxSize && j < expList1.size(); j++) {
				String expId1 = expList1.get(j);
				String expId2 = expList2.get(j);

				c1.cooperRequest(expId1, expId2);
			}
		}

	}

	public void cooperConfirm(int maxSize) {
		for (int i = 0; i < this.clients.length; i++) {

			MockClient c1 = this.clients[i];
			MockClient c2 = i == this.clients.length - 1 ? this.clients[0]
					: this.clients[i + 1];

			MockUserSnapshot us1 = c1.getUserSnapshot(true);

			List<String> crIdL1 = us1.cooperRequestIdList;
			for (String id : crIdL1) {
				c1.cooperConfirm(id);
			}
		}

	}

}
