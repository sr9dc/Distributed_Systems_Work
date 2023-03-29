//*******************************************************
// CalculatorClient.java
// This client gets a remote reference from the rmiregistry
// that is listening on the default port of 1099.
// It allows the client to quit with a "!".
// Otherwise, it computes the sum of two integers
// using the remote calculator.

import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.StringTokenizer;

public class CalculatorClient {
   public static void main(String args[]) throws Exception {
        BufferedReader in  =
                     new BufferedReader(
                         new InputStreamReader(System.in));
        // connect to the rmiregistry and get a remote reference to the Calculator
        // object.
        Calculator c  = (Calculator) Naming.lookup("//localhost/CoolCalculator");
 	System.out.println("Found calculator. Enter ! to quit");
   	while(true) {
           try {
                 // prompt the user
                 System.out.print("client>");
                 // get a line
                 String line  = in.readLine();
                 // if a "!" is entered just exit
                 if(line.equals("!")) System.exit(0);
                 // if it's not a return get the two integers and call add
                 // on the remote calculator.
                 if(!line.equals("")) {
                  StringTokenizer st = new StringTokenizer(line);
                  String v1 = st.nextToken();
                  String v2 = st.nextToken();
                  int i  = Integer.parseInt(v1);
                  int j  = Integer.parseInt(v2);
                  int sum = c.add(i,j);
                  System.out.println(sum);
                  }
               }

	      catch(RemoteException e) {
                   System.out.println("allComments: " + e.getMessage());
              }
	   }
    }
}