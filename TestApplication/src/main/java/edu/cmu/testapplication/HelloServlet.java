package edu.cmu.testapplication;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", urlPatterns = {"/hello-servlet"})
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request.getParameter("create"));

        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println(message);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(request.getParameter("hand") != null){

        }
//        System.out.println(request.getParameter("hand"));
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println(message);
    }

    public void destroy() {
    }
}