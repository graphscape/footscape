/**
 *  Jan 16, 2013
 */
package com.fs.uiclient.impl.gwt.client.testsupport;

import java.util.ArrayList;
import java.util.List;

import com.fs.uicore.api.gwt.client.HandlerI;
import com.fs.uicore.api.gwt.client.UiClientI;

/**
 * @author wuzhen
 * 
 */
public class CollectionTestWorker extends TestWorker {

	protected List<TestWorker> workers;

	public CollectionTestWorker() {
		this.workers = new ArrayList<TestWorker>();
	}

	public CollectionTestWorker addTestWorker(TestWorker tw) {
		this.workers.add(tw);
		return this;
	}

	@Override
	public void start(UiClientI client) {
		this.start(client, 0);
	}

	protected void start(final UiClientI client, final int idx) {
		if (idx == this.workers.size()) {
			this.allTaskDone();
			return;
		}

		TestWorker tw = this.workers.get(idx);
		tw.addHandler(new HandlerI<AllTaskDoneEvent>() {

			@Override
			public void handle(AllTaskDoneEvent t) {
				CollectionTestWorker.this.start(client, idx + 1);
			}
		});
		tw.start(client);
	}

}
