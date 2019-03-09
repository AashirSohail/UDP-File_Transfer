package lab5;

import java.net.*;
import java.util.Scanner;
import java.io.*; 

public class UDPClient {

	public static void main(String[] args) throws IOException  {
		
		// file name
		Scanner input = new Scanner (System.in);
		System.out.print("Enter filename.format(test.txt): ");	
		String file = input.nextLine();
		
		//file handling
		FileReader fileReader = new FileReader(file);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		StringBuffer stringBuffer = new StringBuffer();
		String line;
		
		// connection for UDP
		DatagramSocket socket = new DatagramSocket();
		byte[] buf = new byte[256]; 
		buf = file.getBytes();
		InetAddress address = InetAddress.getByName("localhost"); 
		int port = 8888;
		
		// new UDP socket for fixed buffer size ( 256 byte ), IP address and port
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port); 	
		// send the packet to server
		socket.send(packet); 
		
		//loop to send
		while ((line = bufferedReader.readLine()) != null) {
			
			//reading and printing line
			stringBuffer.append(line);
			stringBuffer.append("\n");
			System.out.printf("\n%s", line);
			buf = "        ".getBytes();
			//saving the line in "buff" and sending
			buf = line.getBytes();
			packet = new DatagramPacket(buf, buf.length, address, port); 
			socket.send(packet); 
			
		}
		
		//close packet
		String close = "CLOSE";
		buf = close.getBytes();
		packet = new DatagramPacket(buf, buf.length, address, port); 
		socket.send(packet);
		System.out.println("\nSent CLOSE packet.");
		
		buf = "        ".getBytes();
		
		// new packet to receive from server "OK"
		packet = new DatagramPacket(buf, buf.length); 
		socket.receive(packet);
		// message stored in a string
		String received = new String(packet.getData(), 0);
		System.out.println("Server Response: " + received);
        fileReader.close();
		socket.close();

	}

}
