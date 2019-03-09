package lab5;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.*; 
import java.util.*; 

public class UDPServer {

	public static void main(String[] args) throws IOException {
	
		//connection for 8888
		DatagramSocket socket = new DatagramSocket(8888); 
		System.out.println("Server Ready at 8888\n");
		try { 
			
			//buffer size 256 byte
			byte[] buf = new byte[256];
			
			
			DatagramPacket packet = new DatagramPacket(buf, buf.length); 
			socket.receive(packet);
			String file = new String(packet.getData(), 0);
			System.out.println("File Name: " + file);
			
			String received = "";
			do {
			 
				
				byte[] buff = new byte[256];
				
				packet = new DatagramPacket(buff, buff.length); 
				socket.receive(packet);
				received = new String(packet.getData(), 0);
				
				if(received.startsWith("CLOSE")) {break;}
				System.out.println(received);
				
				writeFile(received,file);
				
			} 
			
			while( !received.startsWith("CLOSE") );
			
			
			System.out.println("Received CLOSE packet.");			
			InetAddress address = packet.getAddress(); 
			int port = packet.getPort();
			// new packet sent to the client
			String ok = "OK";
			buf = ok.getBytes();
			packet = new DatagramPacket (buf, buf.length, address, port); 
			socket.send(packet); 
			
			System.out.println("OK response sent."); 
		}
		catch (IOException e) 
		{ 
		   e.printStackTrace(); 
		} 
		socket.close();
		

	}

	
	
	static void writeFile(String data, String file) {
		
		try(FileWriter fw = new FileWriter("Server-" + file, true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				    out.println(data.trim());
				    
				} catch (IOException e) {
				}
			
		
		
	}

}
