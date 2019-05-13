// Java implementation for a client 
// Save file as Client.java 

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 
import javax.swing.JFrame;

// Client class 
public class Client 
{ 
	public static void main(String[] args) throws IOException 
	{ 
		try
		{ 
			Scanner scn = new Scanner(System.in); 
			
			// getting localhost ip 
			InetAddress ip = InetAddress.getByName("localhost"); 
	
			// establish the connection with server port 5056 
			Socket s = new Socket(ip, 5056); 
	
			// obtaining input and out streams 
			DataInputStream dis = new DataInputStream(s.getInputStream()); 
			DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
	
			// the following loop performs the exchange of 
			// information between client and client handler 
			while (true) 
			{ 
				String received = dis.readUTF();
				System.out.println(received);
				String tosend = scn.nextLine(); 
				dos.writeUTF(tosend);
				// If client sends exit,close this connection 
				// and then break from the while loop 
				if(tosend.equalsIgnoreCase("Exit")) 
				{ 
					System.out.println("Closing this connection : " + s); 
					s.close(); 
					System.out.println("Connection closed"); 
					break; 
				}
				else if(tosend.equalsIgnoreCase("Message"))	
				{	
					received = dis.readUTF();
					System.out.println(received);
					tosend = scn.nextLine();
					dos.writeUTF(tosend);
					received = dis.readUTF();
					JFrame frame = new JFrame(received);
					frame.pack();
					frame.setVisible(true);
				}
			} 
			
			// closing resources 
			scn.close(); 
			dis.close(); 
			dos.close(); 
		} catch(Exception e){ 
			e.printStackTrace(); 
		} 
	} 
} 

class ServerListener extends Thread {
	final Socket s;
	final DataInputStream dis;
	public ServerListener(DataInputStream dis, Socket s) {
		this.s = s;
		this.dis = dis;
	}


	@Override
	public void run() 
	{ 
		String received = null; 
		while(true)
		{
			try {  
				// receive the answer from server 
				received = dis.readUTF(); 
				if(received.length() > 0) 
				{
					JFrame frame = new JFrame(received);
					frame.pack();
					frame.setVisible(true);
					break;
				}
			} 

			catch (IOException e) { 
				e.printStackTrace(); 
			}
		} 
	} 
}
