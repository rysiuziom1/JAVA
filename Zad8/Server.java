// Java implementation of Server side 
// It contains two classes : Server and ClientHandler 
// Save file as Server.java 

import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
import java.util.Timer;
import java.util.TimerTask;

// Server class 
public class Server 
{ 
	public static void main(String[] args) throws IOException 
	{ 
		// server is listening on port 5056 
		ServerSocket ss = new ServerSocket(5056); 
		
		// running infinite loop for getting 
		// client request 
		while (true) 
		{ 
			Socket s = null; 
			
			try
			{ 
				// socket object to receive incoming client requests 
				s = ss.accept(); 
				
				System.out.println("A new client is connected : " + s); 
				
				// obtaining input and out streams 
				DataInputStream dis = new DataInputStream(s.getInputStream()); 
				DataOutputStream dos = new DataOutputStream(s.getOutputStream()); 
				
				System.out.println("Assigning new thread for this client"); 

				// create a new thread object 
				Thread t = new ClientHandler(s, dis, dos); 

				// Invoking the start() method 
				t.start(); 
				
			} 
			catch (Exception e){ 
				s.close(); 
				e.printStackTrace(); 
			} 
		} 
	} 
} 

// ClientHandler class 
class ClientHandler extends Thread 
{
	final DataInputStream dis; 
	final DataOutputStream dos; 
	final Socket s; 
	

	// Constructor 
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) 
	{ 
		this.s = s; 
		this.dis = dis; 
		this.dos = dos; 
	} 

	@Override
	public void run() 
	{ 
		String received; 
		String toReturn = null; 
		while (true) 
		{ 
			try {  
				dos.writeUTF("What do you want to do? [Message] or [Exit]:");
				// receive the answer from client 
				received = dis.readUTF(); 
				
				if(received.equalsIgnoreCase("Exit")) 
				{ 
					System.out.println("Client " + this.s + " sends exit..."); 
					System.out.println("Closing this connection."); 
					this.s.close(); 
					System.out.println("Connection closed"); 
					break; 
				}
				else if(received.equalsIgnoreCase("Message"))
				{
					dos.writeUTF("Type message:");
					received = dis.readUTF();
					toReturn = received;
					new Reminder(5, dos, dis, s, toReturn);
					break;
				}

			} catch (IOException e) { 
				e.printStackTrace(); 
			}
		} 
	} 
}

class Reminder {
	private Timer timer;
	final String toReturn;
	final DataOutputStream dos;
	final DataInputStream dis;
	final Socket s;

	public Reminder(int seconds, DataOutputStream dos, DataInputStream dis, Socket s, String toReturn) {
		timer = new Timer();
		this.toReturn = toReturn;
		timer.schedule(new RemindTask(), seconds*1000);
		this.dos = dos;
		this.dis = dis;
		this.s = s;
		System.out.println("--------------------REMINDER RUNNING----------------------");
	}

	class RemindTask extends TimerTask {
		@Override
		public void run() {
			try {
				dos.writeUTF(toReturn);
				dos.close();
				dis.close();
				s.close();
				System.out.println("-------------------------TASK DONE-----------------------");
				timer.cancel();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
