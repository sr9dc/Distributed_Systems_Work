import java.net.*;
import java.io.*;
import java.util.Scanner;

public class SimpleWebServer {
    public static void main(String[] args) {
        Socket clientSocket = null;
        try {
            int serverPort = 7777; // the server port we are using

            // Create a new server socket
            ServerSocket listenSocket = new ServerSocket(serverPort);

            /*
             * Block waiting for a new connection request from a client.
             * When the request is received, "accept" it, and the rest
             * the tcp protocol handshake will then take place, making
             * the socket ready for reading and writing.
             */
            clientSocket = listenSocket.accept();
            // If we get here, then we are now connected to a client.

            // Set up "inFromSocket" to read from the client socket
            Scanner inFromSocket;
            inFromSocket = new Scanner(clientSocket.getInputStream());

            // Set up "outToSocket" to write to the client socket

            PrintWriter outToSocket = null;
            outToSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));

            /*
             * Forever,
             *   read a line from the socket
             *   print it to the console
             *   echo it (i.e. write it) back to the client
             */
            Boolean open = true;
            while (open) {
                if (inFromSocket.hasNextLine()) {
                    String data = inFromSocket.nextLine();
                    if (data.contains("GET")) {
                        if (data.split(" ").length > 1) {
                            String file = "src/" + data.split(" ")[1].substring(1);
                            System.out.println("Echoing: " + file);
                            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                                String st;
                                if((st = br.readLine()) != null){
                                    System.out.println("HTTP/1.1 200 OK\n\n");

                                    outToSocket.println("HTTP/1.1 200 OK\n\n");
                                }
                                while ((st = br.readLine()) != null) {
                                    System.out.println(st);

                                    outToSocket.println(st);
                                }
                            }catch (FileNotFoundException ex){
                                System.out.println("HTTP/1.1 404 File Not Found\n\n");
                                System.out.println("Not found");

                                outToSocket.println("HTTP/1.1 404 File Not Found\n\n");
                                outToSocket.println("Not found");
                            }
                            catch (IOException ex){
                            }
                            finally {
                                outToSocket.flush();
                                outToSocket.close();
                            }
                        }
                    }
                } else {
                    clientSocket = listenSocket.accept();
                    inFromSocket = new Scanner(clientSocket.getInputStream());
                    outToSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
                }
            }
        // Handle exceptions
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