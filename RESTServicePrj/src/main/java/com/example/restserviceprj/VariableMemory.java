// This is a JAX-RS service that allows visitors to store, retrieve, and delete
// name value pairs.

package com.example.restserviceprj;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

// handle requests to /variablememory

@Path("/variable-memory")
public class VariableMemory {

    // This map holds variable names and values
    private static Map memory = new TreeMap();

    // This GET will run on a visit to /variable-memory
    @GET
    @Produces("text/plain")
    public Response getDefault() {
        String defaultString = "Visited with /variable-memory addc a slash/name to visit the other GET";
        System.out.println("GET request by visiting with /variable-memory");
        // generate a response
        return Response.status(200).entity(defaultString).build();
    }

    // This GET will run on a visit to /variable-memory/variableName
    @Path("{variableName}")
    @GET
    @Produces("text/plain")
    public Response getFromMemory(@PathParam("variableName") String variableName) {
        System.out.println("GET request by visiting with /variable-memory/" + variableName);
        String output = "";
        // get variable's value by name from the map
        Object lookUp = memory.get(variableName);
        // figure return value
        if (lookUp == null) output = "No variable named " + variableName + " found in memory";
        else output = (String) lookUp;
        // generate a response
        return Response.status(200).entity(output).build();
    }

    // This PUT will run with the body holding a name=value
    @PUT
    @Consumes("text/plain")
    @Produces(value = "text/plain")
    public Response storeInMemory(String fromVisitor) {
        System.out.println("From a call to put " + fromVisitor);
        // spit the name and the value from the visitor
        String[] input = fromVisitor.split("=");
        // store the pair in the map
        memory.put(input[0], input[1]);
        // report visit on the server's console
        System.out.println(input[0] + " stored with value " + input[1]);
        // return a response to the visitor
        return Response.status(200).entity(input[0] + " assigned the value "+ input[1]).build();

    }

    // This DELETE will run with the body holding a name
    @Path("{fromVisitor}")
    @DELETE
    @Produces("text/plain")
    public Response deleteFromMemory(@PathParam("fromVisitor") String fromVisitor) {
        System.out.println("Deleting " + fromVisitor);
        // remove the key value pair from the map
        memory.remove(fromVisitor);
        // report visit on the server's console
        System.out.println("Removing key " + fromVisitor + " from the map");
        // return a response to the visitor
        return Response.status(200).entity("DELETE completed on server").build();

    }


    // List code

    // This GET will run on a visit to /variable-memory/variableName
    @Path("/list/variables")
    @GET
    @Produces("text/plain")
    public Response getVariableList() {
//        System.out.println("GET request by visiting with /variable-memory/" + variableName);
        String output = "";
        // get variable's value by name from the map
        Object list = memory.keySet();
        // figure return value
        if (list == null) output = "No treemap named found in memory";
        else output = (String) String.join("", memory.keySet());
        // generate a response
        return Response.status(200).entity(output).build();
    }

}
