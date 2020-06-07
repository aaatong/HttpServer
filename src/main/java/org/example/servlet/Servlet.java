package org.example.servlet;

import org.example.server.Request;
import org.example.server.Response;

public interface Servlet {

    void service(Request request, Response response);

}
