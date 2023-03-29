import java.net.*;
import java.io.*;
import java.util.Scanner;

public class EchoServerUDP{
    public static void main(String args[]){
        System.out.println("The server is running."); // lab instructions
        DatagramSocket aSocket = null;
        byte[] buffer = new byte[1000];  // set up packet buffer for client message
        try{
            // set up ports
            System.out.print("Input a server port number to listen on: ");
            Scanner readline = new Scanner(System.in);
            int serverPort = readline.nextInt(); // convert to int

            // set up sockets to receive client packets
            aSocket = new DatagramSocket(serverPort);
            aSocket.setReuseAddress(true);

            DatagramPacket request = new DatagramPacket(buffer, buffer.length); // syntax for buffer
            while(true){ // loop to continue until 'halt!' is sent
                aSocket.receive(request); // receive request, and format a reply
                DatagramPacket reply = new DatagramPacket(request.getData(),
                        request.getLength(), request.getAddress(), request.getPort()); // syntax for reply from request
                String requestString = new String(request.getData()).substring(0,request.getLength()); // proper length
                System.out.println("Echoing: "+requestString);
                aSocket.send(reply); // send back the reply

                // halt logic
                if(requestString.equalsIgnoreCase("halt!")){
                    System.out.print("Server side quitting");
                    break;
                }
            }
            // catch potential exceptions
        }catch (SocketException e){System.out.println("Socket: " + e.getMessage());
        }catch (IOException e) {System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}
    }
}