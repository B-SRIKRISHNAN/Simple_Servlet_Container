package org.sample.server.handlers;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {

        System.out.println(" Thread has started " );
        try {
            InputStream input = null;
            OutputStream output = null;
            if (socket.isConnected()) {
                System.out.println("Server has obtained request");
                input = socket.getInputStream();
                output = socket.getOutputStream();
                // Need to process the input data, so that it is of type http

                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

                String content = "";
                String line = "";
                while ((line = reader.readLine()) != null && reader.ready()) {
                    content += line + "\n";
                }
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(10000);
                System.out.println(content);

                writer.write("GET / HTTP/1.1\r\n");
                writer.write("Host: localhost:8080\r\n");
                writer.write("\r\n");

                writer.flush();
                writer.close();

                reader.close();


//					writer.close();

//					socket.
//					writer.close();

//					reader.close();
//					socket.close();
//					serverSocket.close();

//					SimpleServletRequest request = new SimpleServletRequest(content.toString());
//
//
//					ServletOutputStream stream = new SimpleServletOutputStream(writer);
//					SimpleServletResponse response = new SimpleServletResponse(stream);

//					new SimpleAppServlet().service(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
