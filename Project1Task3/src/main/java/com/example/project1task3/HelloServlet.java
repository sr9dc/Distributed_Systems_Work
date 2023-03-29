 package com.example.project1task3;

import java.io.*;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", urlPatterns = {"/submit", "/getResults"})
public class HelloServlet extends HttpServlet {
    private String message;
    public int aCount = 0;
    public int bCount = 0;
    public int cCount = 0;
    public int dCount = 0;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // determine what type of device our user is
        String ua = request.getHeader("User-Agent");

        boolean mobile;
        // prepare the appropriate DOCTYPE for the view pages
        if (ua != null && ((ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1))) {
            mobile = true;
            /*
             * This is the latest XHTML Mobile doctype. To see the difference it
             * makes, comment it out so that a default desktop doctype is used
             * and view on an Android or iPhone.
             */
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {
            mobile = false;
            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        // check url subpattern, if it contains submit then pull from form
        if(request.getServletPath().contains("/submit")){
            response.setContentType("text/html");
            String chosen = request.getParameter("input"); // check what was chosen in form
            request.setAttribute("selected", chosen);
            RequestDispatcher view = request.getRequestDispatcher("index.jsp"); // reset view, now registered vals will show
            view.forward(request, response);
            if(chosen.equalsIgnoreCase("a")) aCount++; // increment each variable if submitted from form
            if(chosen.equalsIgnoreCase("b")) bCount++;
            if(chosen.equalsIgnoreCase("c")) cCount++;
            if(chosen.equalsIgnoreCase("d")) dCount++;
        }
        else {
            doPost(request,response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String goToResults = "false";
        if(aCount+bCount+cCount+dCount > 0) goToResults="true";

        // update boolean check if any variables have been incremented
        request.setAttribute("goToResults", goToResults);

        // update variables for access in jsp file
        request.setAttribute("aCount", String.valueOf(aCount));
        request.setAttribute("bCount", String.valueOf(bCount));
        request.setAttribute("cCount", String.valueOf(cCount));
        request.setAttribute("dCount", String.valueOf(dCount));


        // reset form methods
        RequestDispatcher view = request.getRequestDispatcher("getResults.jsp");
        view.forward(request, response);

        aCount=0;
        bCount=0;
        cCount=0;
        dCount=0;
    }

    public void main(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request,response);
    }


    public void destroy() {
    }
}