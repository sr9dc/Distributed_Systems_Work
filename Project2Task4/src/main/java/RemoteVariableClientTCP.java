import java.net.*;
import java.io.*;
import java.util.Scanner;

public class RemoteVariableClientTCP{
    static InetAddress aHost;
    static int serverPort;
    static Socket clientSocket = null;
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

            clientSocket = new Socket("localhost", serverPort);


            String ID = "";
            String operation = "";
            String value = "";

            String packet = "";

            // create packet to server
            while (true) {
                System.out.println("\n1. Add a value to your sum.\n" +
                        "2. Subtract a value from your sum.\n" +
                        "3. Get your sum.\n" +
                        "4. Exit client");

                int in = readline.nextInt();

                if(in == 1){
                    operation = "1 ";
                    System.out.println("Enter value to add: ");
                    value = String.valueOf(readline.nextInt());
                    System.out.println("Enter your ID: ");
                    ID = String.valueOf(readline.nextInt()) + " ";

                    packet += operation + ID + value;

                }
                else if(in == 2){
                    operation = "2 ";
                    System.out.println("Enter value to subtract: ");
                    value = String.valueOf(readline.nextInt());
                    System.out.println("Enter your ID: ");
                    ID = String.valueOf(readline.nextInt()) + " ";

                    packet += operation + ID + value;

                }
                else if(in == 3){
                    operation = "3 ";
                    System.out.println("Enter your ID: ");
                    ID = String.valueOf(readline.nextInt()) + " ";
                    value = "";

                    packet += operation + ID + value;
                }
                else if(in == 4){
                    System.out.println("Client side quitting. The remote variable server is still running.");
                    // send reply if not halt
                    clientSocket.close();
                    break;
                }

                System.out.println("The result is: " + communicate(packet));

                packet = "";


            }

            // catch potential exceptions
        }catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());
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
    // ----------------------- Proxy style communication code -----------------------
    // sends packet and reads reply
    public static String communicate(String in) throws IOException {

        BufferedReader read = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));

        out.println(in);
        out.flush();

        String replyString = read.readLine();

        return replyString;
    }
}