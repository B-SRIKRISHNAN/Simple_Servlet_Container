package org.sample.application;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class SimpleAppServlet implements Servlet {



    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        String host = servletRequest.getRemoteHost();
        String protocol = servletRequest.getProtocol();
        String scheme = servletRequest.getScheme();

        System.out.println("Connection Data: "+host+" "+protocol+" "+scheme);

        OutputStream stream = servletResponse.getOutputStream();

        stream.write("HELLO WORLD".getBytes());
        stream.flush();
        stream.close();

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
