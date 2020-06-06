package org.example.Servlet;

import org.example.server.Request;
import org.example.server.Response;

public interface Servlet {

    public void service(Request request, Response response);
}
