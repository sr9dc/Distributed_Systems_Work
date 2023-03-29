import java.net.*;
import java.io.*;
public class EchoServerUDP{
	public static void main(String args[]){ 
	DatagramSocket aSocket = null;
	byte[] buffer = new byte[1000];
	try{
		aSocket = new DatagramSocket(6789);
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);	
 		while(true){
			aSocket.receive(request);     
			DatagramPacket reply = new DatagramPacket(request.getData(), 
      	   	request.getLength(), request.getAddress(), request.getPort());
			String requestString = new String(request.getData());
			System.out.println("Echoing: "+requestString);
			aSocket.send(reply);
		}
	}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
	}catch (IOException e) {System.out.println("IO: " + e.getMessage());
	}finally {if(aSocket != null) aSocket.close();}
	}
}
