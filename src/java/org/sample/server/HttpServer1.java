package org.sample.server;

import org.sample.application.SimpleAppServlet;
import org.sample.server.handlers.RequestHandler;
import org.sample.servlet.basic.SimpleServletOutputStream;
import org.sample.servlet.basic.SimpleServletRequest;
import org.sample.servlet.basic.SimpleServletResponse;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.sql.Time;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.*;

public class HttpServer1{

	private volatile Boolean shutdown = false;
	private Integer PORT;
	private String HOST;
	private int QUEUE_SIZE;
	private boolean started = false;
	private volatile ServerSocket serverSocket;
	private volatile ThreadPoolExecutor exec ;
	private BlockingQueue<Runnable> requestQueue ;
	public static void main(String[] args)
	{
		HttpServer1 server1 = new HttpServer1();
		server1.PORT = 8080;
		server1.HOST="127.0.0.1";
		server1.QUEUE_SIZE=100;

		server1.init();
		Thread.ofPlatform().name("SAMPLE SERVER").start(new Runnable() {
			public void run () {
				server1.await();
			}
		});
		try {
			Thread.sleep(6000);
			server1.shutdown();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}
	/**
	* This method waits for a request from a client, generates ServletRequest, ServletResponse objects
	 * 	and passes them to the servlet.
	*/
	public void await(){
		RequestHandler requestHandler = null;
		System.out.println("STARTING "+Thread.currentThread().getName());
		while(!this.shutdown && started)
		{
			Socket socket = null;
			try{
				socket = serverSocket.accept();
				requestHandler = new RequestHandler(socket);
				exec.submit(requestHandler);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("ENDING "+Thread.currentThread().getName());
	}
	
	/**
	*	Starts server starting threads??
	*/
	public void init(){
		try {
			// the second argument is backlog number of connections to socket.??
			requestQueue =  new ArrayBlockingQueue<>(QUEUE_SIZE);
			this.serverSocket = new ServerSocket(PORT, 1, InetAddress.getByName(HOST));
			exec = new ThreadPoolExecutor(10,1000,100,TimeUnit.SECONDS,requestQueue, Thread.ofVirtual().factory());
			this.started =  true;
		}catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	* Shuts down the server making it stop listening for requests and stops all active thread.
	*/
	public void shutdown() throws IOException {
		this.shutdown=true;

		this.serverSocket.close();
		this.serverSocket =null;

		// forcibly terminate
		this.exec.shutdownNow();
		this.exec.close();
		this.exec = null;

		requestQueue.clear();
		this.requestQueue = null;
		this.started = false;
	}
	
	
	
}