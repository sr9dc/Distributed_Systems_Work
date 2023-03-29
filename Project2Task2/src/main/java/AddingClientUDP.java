import java.net.*;
import java.io.*;
import java.util.Scanner;

public class AddingClientUDP{
    static InetAddress aHost;
    static int serverPort;
    static DatagramSocket aSocket;
    static String replyString = "";


    public static void main(String args[]){
        // args give message contents and server hostname
        System.out.println("The client is running.");
        try {
            // set to localhost to host on local machine and set port
            aHost = InetAddress.getByName("localhost");
            serverPort = 6789;

            // input server port to use
            System.out.print("Input a server side port number: ");
            Scanner readline = new Scanner(System.in);
            serverPort = readline.nextInt();

            String nextLine;
            BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));
            while ((nextLine = typed.readLine()) != null) {
                // halt logic
                if(nextLine.equalsIgnoreCase("halt!")){
                    System.out.println("Client side quitting.");
                    break;
                }
                else{
                    int result = add(Integer.parseInt(nextLine));
                    System.out.println("The server returned: " + replyString + "."); // send reply if not halt
                }
            }

            // catch potential exceptions
        }catch (SocketException e) {System.out.println("Socket: " + e.getMessage());
        }catch (IOException e){System.out.println("IO: " + e.getMessage());
        }finally {if(aSocket != null) aSocket.close();}
    }
    public static int add(int i) throws IOException {
        // set up network socket to allow communication between server and client
        aSocket = new DatagramSocket();

        byte [] m = String.valueOf(i).getBytes(); // convert console in into byte packets

        DatagramPacket request = new DatagramPacket(m,  m.length, aHost, serverPort);
        aSocket.send(request); // send packet

        byte[] buffer = new byte[1000]; // create a buffer to receiver server packet
        DatagramPacket reply = new DatagramPacket(buffer, buffer.length); // format for receiving packet
        aSocket.receive(reply); // receive from socket
        replyString = new String(reply.getData()).substring(0,reply.getLength()); // get proper length of string

        return Integer.parseInt(replyString);
    }
}