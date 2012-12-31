/**
 * Jul 26, 2012
 */
package com.fs.commons.api.mail;

import java.util.List;
import java.util.concurrent.Future;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;

import com.fs.commons.api.callback.CallbackI;

/**
 * @author wu
 * 
 */
public interface MailReceiverI {

	public static interface FactoryI {
		public MailReceiverI create(String server, String user, String password);
	}

	public static class FolderContext {
		public FolderContext(Folder folder, Store store) {
			this.folder = folder;
			this.store = store;
		}

		private Folder folder;

		private Store store;

		/**
		 * @return the folder
		 */
		public Folder getFolder() {
			return folder;
		}

		/**
		 * @return the store
		 */
		public Store getStore() {
			return store;
		}

	}

	public static class MailContext {

		public MailContext(MimeMessageWrapper mw, FolderContext fc) {
			this.messageWrapper = mw;
			this.folderContext = fc;
		}

		private FolderContext folderContext;

		private MimeMessageWrapper messageWrapper;

		/**
		 * @return the folderContext
		 */
		public FolderContext getFolderContext() {
			return folderContext;
		}

		/**
		 * @return the messageWrapper
		 */
		public MimeMessageWrapper getMessageWrapper() {
			return messageWrapper;
		}

	}

	public void executeInStore(CallbackI<Store, Object> cb);

	public void executeInFolder(String folder,
			CallbackI<FolderContext, Boolean> cb);

	public void forEachMessage(String folder,
			CallbackI<MailContext, Boolean> each);

	public void forEachFolder(CallbackI<FolderContext, Boolean> each);

	public Future<Integer> waitMessageCountEvent(String folder, long timeout);

	public <T> List<T> processEachMessage(String folder,
			CallbackI<MailContext, T> each);

}
