import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.*;

public class RemoteVariableServerTCP{
    private static TreeMap<Integer, Integer> tree_map = new TreeMap<Integer, Integer>();

    static String instructions = "1. Add a value to your sum.\n" +
            "2. Subtract a value from your sum.\n" +
            "3. Get your sum.\n" +
            "4. Exit client";

    public static void main(String args[]){
        System.out.println("The server is running."); // lab instructions
        Socket clientSocket = null;
        byte[] buffer = new byte[1000];  // set up packet buffer for client message
        try{
            // set up ports
            System.out.print("Input a server port number to listen on: ");
            Scanner readline = new Scanner(System.in);
            int serverPort = readline.nextInt(); // convert to int

            ServerSocket listenSocket = new ServerSocket(serverPort);

            int id;
            int value;

            while(true){ // loop to continue until 'halt!' is sent
                clientSocket = listenSocket.accept();

                Scanner in;
                in = new Scanner(clientSocket.getInputStream());

                PrintWriter out;
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));


                // operation request and reply
                while(in.hasNextLine()){
                    String requestString = in.nextLine();

                    String[] arrRequestString = requestString.split(" ");

                    id = Integer.valueOf(arrRequestString[1]);

                    // treemap logic to ensure unique id is generated
                    if(!tree_map.containsKey(id)){
                        if(arrRequestString[0].equals("1")){
                            value = Integer.valueOf(arrRequestString[2]);
                            tree_map.put(id, value);
                        }
                        else if(arrRequestString[0].equals("2")){
                            value = -1*Integer.valueOf(arrRequestString[2]);
                            tree_map.put(id, value);
                        }
                        else{
                            tree_map.put(id, 0); // default for no id input
                        }
                    }
                    else{
                        if(arrRequestString[0].equals("1")){
                            value = Integer.valueOf(arrRequestString[2]);
                            tree_map.replace(id, tree_map.get(id)+value);
                        }
                        else if(arrRequestString[0].equals("2")){
                            value = Integer.valueOf(arrRequestString[2]);
                            tree_map.replace(id, tree_map.get(id)-value);
                        }
                    }

                    System.out.println("\nVisitorID: " + arrRequestString[1]);
                    System.out.println("Operation #: " + arrRequestString[0]);
                    System.out.println("Return Variable " + String.valueOf(tree_map.get(id)));


                    out.println(String.valueOf(tree_map.get(id)));
                    out.flush();
                }
            }
            // catch potential exceptions
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());

            // If quitting (typically by you sending quit signal) clean up sockets
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // ignore exception on close
            }
        }
    }
}