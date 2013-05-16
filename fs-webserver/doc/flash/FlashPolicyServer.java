/**
 *  
 */
package com.fs.websocket.impl.flash;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fs.commons.api.config.Configuration;
import com.fs.commons.api.lang.FsException;
import com.fs.commons.api.server.ServerI;
import com.fs.commons.api.support.ServerSupport;

/**
 * @author wu
 * 
 */
public class FlashPolicyServer extends ServerSupport {
	private static final Logger LOG = LoggerFactory.getLogger(FlashPolicyServer.class);
	private int port;

	private ExecutorService executor;

	private Future<?> main;

	private ServerSocket serverSocket;

	@Override
	public void cmd(String cmd) {

	}

	@Override
	public void configure(Configuration cfg) {
		super.configure(cfg);
		this.port = cfg.getPropertyAsInt("port", 843);
	}

	@Override
	protected void doStart() {
		this.executor = Executors.newFixedThreadPool(10);//
		LOG.info("start flash policy server on port:" + this.port);
		try {

			this.serverSocket = new ServerSocket(port);

		} catch (IOException e) {
			throw new FsException(e);
		}
		this.main = this.executor.submit(new Runnable() {

			@Override
			public void run() {
				FlashPolicyServer.this.run();
			}
		});
	}

	private void run() {

		while (this.isState(RUNNING, STARTING)) {
			try {

				final Socket socket = this.serverSocket.accept();

				this.executor.submit(new Callable() {

					@Override
					public Object call() throws Exception {
						FlashPolicyServer.this.onSocket(socket);
						return null;
					}
				});
			} catch (SocketException e) {

				if (this.isState(ServerI.SHUTINGDOWN, ServerI.SHUTDOWN)) {
					LOG.info("normal for got a socket close exception:" + e.getMessage());
				} else {
					continue;
				}

			} catch (IOException e) {
				LOG.error("", e);
				continue;
			}
		}

		LOG.warn("exit the loop ");

	}

	/**
	 * 
	 */
	protected void onSocket(Socket socket) {

		try {
			socket.setSoTimeout(10000);

			InputStream in = socket.getInputStream();
			byte[] buffer = new byte[23];
			if (in.read(buffer) == -1
					|| !(new String(buffer, "ISO-8859-1")).startsWith("<policy-file-request/>")) {
				LOG.warn("not a valide request:" + new String(buffer));
				return;
			}

			OutputStream out = socket.getOutputStream();
			String rt = "<?xml version=\"1.0\"?>\n"//
					+ "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\n"//
					+ "<cross-domain-policy> \n"//
					+ "   <site-control permitted-cross-domain-policies=\"master-only\"/>\n"//
					+ "   <allow-access-from domain=\"*\" to-ports=\"*\" />\n" //
					+ "</cross-domain-policy>"//
			;
			if (LOG.isDebugEnabled()) {

				LOG.debug("response:" + rt);
			}
			byte[] bytes = rt.getBytes("ISO-8859-1");

			out.write(bytes);
			out.write(0x00);
			out.flush();
			out.close();
		} catch (SocketException e) {
			LOG.error("", e);
		} catch (IOException e) {
			LOG.error("", e);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				LOG.error("close socket error", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fs.commons.api.support.ServerSupport#doShutdown()
	 */
	@Override
	protected void doShutdown() {
		try {
			this.serverSocket.close();
		} catch (IOException e1) {
			throw new FsException(e1);
		}
		this.executor.shutdown();
		try {
			this.executor.awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {

		}
	}
}
