import java.net.*;
import java.io.*;
public class EchoClientUDP{
    public static void main(String args[]){ 
	// args give message contents and server hostname
	DatagramSocket aSocket = null;
	try {
		InetAddress aHost = InetAddress.getByName(args[0]);
		int serverPort = 6789;
		aSocket = new DatagramSocket();
		String nextLine;
		BufferedReader typed = new BufferedReader(new InputStreamReader(System.in));
		while ((nextLine = typed.readLine()) != null) {
		  byte [] m = nextLine.getBytes();	
		  DatagramPacket request = new DatagramPacket(m,  m.length, aHost, serverPort);
  		aSocket.send(request);
  		byte[] buffer = new byte[1000];
  		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
  		aSocket.receive(reply);
  		System.out.println("Reply: " + new String(reply.getData()));
    }

	}catch (SocketException e) {System.out.println("Socket: " + e.getMessage());
	}catch (IOException e){System.out.println("IO: " + e.getMessage());
	}finally {if(aSocket != null) aSocket.close();}
    }
}
