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
	
	
	private boolean shutdown = false;
	
	public static void main(String[] args)
	{
		HttpServer1 server1 = new HttpServer1();
		server1.await();
	}
	/**
	* This method waits for a request from a client, generates ServletRequest, ServletResponse objects
	 * 	and passes them to the servlet.
	*/
	public void await(){
		RequestHandler requestHandler=null;
		Thread thread = null;
		ServerSocket serverSocket = null;
		BlockingQueue<Runnable> requestQueue = new ArrayBlockingQueue<>(100);
		ThreadPoolExecutor exec = null;
		try{
			// the second argument is backlog number of connections to socket.??
			serverSocket = new ServerSocket(8080,1,InetAddress.getByName("127.0.0.1"));
			exec = new ThreadPoolExecutor(10,1000,1,TimeUnit.SECONDS,requestQueue, Thread.ofVirtual().factory());

		}catch(IOException ioe){

			ioe.printStackTrace();
			System.exit(1);
		}
		while(true)
		{
			Socket socket = null;
			InputStream input = null;
			OutputStream output = null;
			
			try{
				socket = serverSocket.accept();
				requestHandler = new RequestHandler(socket);
				exec.submit(requestHandler);
			}catch(Exception e)
			{
				e.printStackTrace();
				System.exit(1);
			}
		}
		
	}
	
	/**
	*	Starts server starting threads??
	*/
	public void init(){

	}
	
	/**
	* Shuts down the server making it stop listening for requests and stops all active thread.
	*/
	public void shutdown(){



	}
	
	
	
}