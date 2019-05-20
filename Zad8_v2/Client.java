import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
 
public class Client 
{
	public static void main(String[] args) throws IOException 
	{
		Scanner scanner = new Scanner(System.in);
		InetAddress ip = InetAddress.getByName("localhost");
		Socket sCommunication = new Socket(ip, 5056);
		ObjectInputStream oisCommunication = new ObjectInputStream(sCommunication.getInputStream());
		ObjectOutputStream oosCommunication = new ObjectOutputStream(sCommunication.getOutputStream());

		Socket sNotifier = new Socket(ip, 5057);
		ObjectInputStream oisNotifier = new ObjectInputStream(sNotifier.getInputStream());
		ObjectOutputStream oosNotifier = new ObjectOutputStream(sNotifier.getOutputStream());

		Thread notifyListener = new NotifyListener(sNotifier, oosNotifier, oisNotifier);
		notifyListener.start();
		System.out.println("Notifier is running");
		while(true)
		{
			try
			{
				String received = (String) oisCommunication.readObject();
				System.out.println(received);
				String toSend = scanner.nextLine();
				oosCommunication.writeObject(toSend);
				if(toSend.equalsIgnoreCase("E"))
				{
					System.out.println("Closing this connection : " + sCommunication); 
					sCommunication.close();
					sNotifier.close();
					System.out.println("Connection closed"); 
					break;
				}
				else if(toSend.equalsIgnoreCase("M"))
				{
					received = (String) oisCommunication.readObject();
					System.out.println(received);
					toSend = scanner.nextLine();
					oosCommunication.writeObject(toSend);
					received = (String) oisCommunication.readObject();
					System.out.println(received);
					toSend = scanner.nextLine();
					oosCommunication.writeObject(toSend);
					received = (String) oisCommunication.readObject();
					System.out.println(received);
					received = null;
				}
			}
			catch(IOException e)
			{
				System.err.println(e.toString());
			}
			catch(ClassNotFoundException e)
			{
				System.err.println(e.toString());
			}
		}
	}
}

class NotifyListener extends Thread
{
	final Socket sNotifier;
	final ObjectOutputStream oosNotifier;
	final ObjectInputStream oisNotifier;

	public NotifyListener(Socket sNotifier, ObjectOutputStream oosNotifier, ObjectInputStream oisNotifier) throws IOException
	{
		this.sNotifier = sNotifier;
		this.oosNotifier = oosNotifier;
		this.oisNotifier = oisNotifier;
	}

	@Override
	public void run()
	{
		try
		{
			String received = null;
			while(true)
			{
				received = (String) oisNotifier.readObject();
				if(received != null)
				{
					JFrame frame = new JFrame("Java notification");
					JLabel label = new JLabel(received);
					label.setFont(label.getFont().deriveFont(24.0f));
					frame.getContentPane().add(label);
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setLocationRelativeTo(null);
					frame.pack();
					frame.setVisible(true);
					received = null;
				}
			}
		}
		catch(IOException e)
		{
			System.out.println("Listener stopped");
		}
		catch(ClassNotFoundException e)
		{
			System.err.println(e.toString());
		}

	}
}