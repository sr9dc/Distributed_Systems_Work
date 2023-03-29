// Client side code making calls to an HTTP service
// The service provides GET, DELETE, and PUT
// Simple example client storing and deleting name, value pairs on the server

package edu.cmu.andrew.srajulad;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

// A simple class to wrap an RPC result.
class Result {
    private int responseCode;
    private String responseText;

    public int getResponseCode() { return responseCode; }
    public void setResponseCode(int code) { responseCode = code; }
    public String getResponseText() { return responseText; }
    public void setResponseText(String msg) { responseText = msg; }

    public String toString() { return responseCode + ":" + responseText; }
}
public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Begin main of REST lab.");
        System.out.println("Assign 100 to the variable named x");
        System.out.println(assign("x", "100"));

        System.out.println("Assign 199 to the variable named x");
        System.out.println(assign("x", "199"));
        System.out.println("Send a request to read x");
        System.out.println(read("x"));


        System.out.println("Sending a DELETE request for x");
        System.out.println(clear("x"));
        System.out.println("x is deleted but let's try to read it");
        System.out.println(read("x"));


        // place a quote in some variables
        assign("Line1", "Computer Science is no more about computers\n ");
        assign("Line2", "than astronomy is about telescopes.\n");
        assign("Line3", "Edsger W. Dijkstra\n");

        // read them from the server
        System.out.println(read("Line1"));
        System.out.println(read("Line2"));
        System.out.println(read("Line3"));

        //Code commented out for the moment
        System.out.println(getVariableList());

        System.out.println("End main of REST lab");

    }

    // Call doPut with name and value pair
    public static Result assign(String name, String value) {
        Result r = doPut(name, value);
        return r;
    }

    // Call doGet with a name
    public static Result read(String name) {
        Result r = doGet(name);
        return r;
    }

    // call doDelete with a name
    public static Result clear(String name) {
        Result r = doDelete(name);
        return r;
    }

    // Call doGet with a name
    public static Result getVariableList() {
        Result r = doGetList();
        return r;
    }

    // Make an HTTP GET request
    public static Result doGet(String name) {

        HttpURLConnection conn;
        int status = 0;
        Result result = new Result();
        try {
            // GET wants us to pass the name on the URL line
            URL url = new URL("http://localhost:8080/RESTServicePrj-1.0-SNAPSHOT/api/variable-memory/" + name);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // we are sending plain text
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            // tell the server what format we want back
            conn.setRequestProperty("Accept", "text/plain");

            // wait for response
            status = conn.getResponseCode();

            // set http response code
            result.setResponseCode(status);
            // set http response message - this is just a status message
            // and not the body returned by GET
            result.setResponseText(conn.getResponseMessage());

            if (status == 200) {
                String responseBody = getResponseBody(conn);
                result.setResponseText(responseBody);
            }

            conn.disconnect();

        }
        // handle exceptions
        catch (MalformedURLException e) {
            System.out.println("URL Exception thrown" + e);
        } catch (IOException e) {
            System.out.println("IO Exception thrown" + e);
        } catch (Exception e) {
            System.out.println("IO Exception thrown" + e);
        }
        return result;
    }

    // Make an HTTP GET request
    public static Result doGetList() {

        HttpURLConnection conn;
        int status = 0;
        Result result = new Result();
        try {
            // GET wants us to pass the name on the URL line
            URL url = new URL("http://localhost:8080/RESTServicePrj-1.0-SNAPSHOT/api/variable-memory/list/variables");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // we are sending plain text
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            // tell the server what format we want back
            conn.setRequestProperty("Accept", "text/plain");

            // wait for response
            status = conn.getResponseCode();

            // set http response code
            result.setResponseCode(status);
            // set http response message - this is just a status message
            // and not the body returned by GET
            result.setResponseText(conn.getResponseMessage());

            if (status == 200) {
                String responseBody = getResponseBody(conn);
                result.setResponseText(responseBody);
            }

            conn.disconnect();

        }
        // handle exceptions
        catch (MalformedURLException e) {
            System.out.println("URL Exception thrown" + e);
        } catch (IOException e) {
            System.out.println("IO Exception thrown" + e);
        } catch (Exception e) {
            System.out.println("IO Exception thrown" + e);
        }
        return result;
    }


















    // Make an HTTP PUT request and pass the name and value
    public static Result doPut(String name, String value) {

        HttpURLConnection conn;
        int status = 0;
        Result result = new Result();

        try {
            // establish the URL
            // Note, PUT does not use the URL line for its message to the server
            URL url = new URL("http://localhost:8080/RESTServicePrj-1.0-SNAPSHOT/api/variable-memory/");
            conn = (HttpURLConnection) url.openConnection();
            // we are sending plain text
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            conn.setRequestProperty("Accept", "text/plain");
            // prepare to put
            conn.setRequestMethod("PUT");
            // we are sending data with this put request
            conn.setDoOutput(true);
            // write to the connection
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            // write name value pair
            out.write(name + "=" + value);
            out.close();

            // see how things went
            status = conn.getResponseCode();
            result.setResponseCode(status);
            result.setResponseText(conn.getResponseMessage());

            if (status == 200) {
                // things went well, gather up the response body
                String responseBody = getResponseBody(conn);
                result.setResponseText(responseBody);
            }

        }
        // handle exceptions
        catch (MalformedURLException e) {
            System.out.println("URL Exception thrown" + e);
        } catch (IOException e) {
            System.out.println("IO Exception thrown" + e);
        }

        // return result
        return result;
    }


    // Make an HTTP DELETE request with a name
    public static Result doDelete(String name) {

        HttpURLConnection conn;
        int status = 0;
        Result result = new Result();

        // Send an HTTP DELETE to server along with name on the URL line
        try {
            URL url = new URL("http://localhost:8080/RESTServicePrj-1.0-SNAPSHOT/api/variable-memory/" + name);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            // we are sending plain text
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            // tell the server what format we want back
            conn.setRequestProperty("Accept", "text/plain");
            // wait for response
            status = conn.getResponseCode();
            result.setResponseCode(status);
            result.setResponseText(conn.getResponseMessage());
            if (status == 200) {
                // things went well, gather up the response body
                String responseBody = getResponseBody(conn);
                result.setResponseText(responseBody);
            }
        }
        // handle exceptions
        catch (MalformedURLException e) {
            System.out.println("URL Exception thrown" + e);
        } catch (IOException e) {
            System.out.println("IO Exception thrown" + e);
        }
        return result;
    }

    // Gather up a response body from the connection
    // and close the connection.
    public static String getResponseBody(HttpURLConnection conn) {
        String responseText = "";
        try {
            String output = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            while ((output = br.readLine()) != null) {
                responseText += output;
            }
            conn.disconnect();
        } catch (IOException e) {
            System.out.println("Exception caught " + e);
        }
        return responseText;
    }
}

