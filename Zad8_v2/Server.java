import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.Map;
import java.util.SortedMap;

public class Server
{
	public static void main(String[] args)
	{
		// server is listening on port 5056
		ServerSocket ss = new ServerSocket(5056);

		// infinity loop to getting clients requests
		while(true)
		{
			// declaring socket for comunication
			Socket sComunication = null;
			// declaring socket for norifier
			Socket sNotifier = null;

			try
			{
				// accepting client request
				sComuniaction = ss.accept();
				sNotifier = ss.accept();

				System.out.println("A new Client " + sComuniaction + " is connected");
				ClientHandler cHandler = new ClientHandler(sComuniaction);
				ClientNotifier cNotifier = new ClientNotifier(sNotifier);
				cHandler.start();
				cNotifier.start();
			}

			catch(IOException e)
			{
				e.printStackTrace();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}

class Notify
{
	private String text;
	private Date date;

	public Notify(String text, Date date)
	{
		this.text = text;
		this.date = date;
	}

	public Date returnNotifyDate()
	{
		return this.date;
	}

	public String returnNotifyText()
	{
		return this.text;
	}
}

class ClientNotifier extends Thread
{
	final Socket sNotifier;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private SortedMap<Date, String> listOfNotifies;

	public ClientNotifier(Socket sNotifier)
	{
		this.sNotifier = sNotifier;
		ois = new ObjectInputStream(sNotifier.getInputStream());
		oos = new ObjectOutputStream(sNotifier.getOutputStream());
		listOfNotifies = new TreeMap<Date, String>();
	}

	public void addNotify(Notify notify)
	{
		listOfNotifies.put(notify.returnNotifyDate, notify.returnNotifyText);
	}

	@Override
	public void run()
	{
		SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm:ss");
		String text;
		Date date;
		oos.writeUTF("Type a message:");
		text = ois.readUTF();
		oos.writeUTF("Type a date:");
		date = dateformat.parse(ois.readUTF());
		Notify notify = new Notify(date, text);
		addNotify(notify);
	}

}

class ClientHandler extends Thread
{
	final Socket sComuniaction;
	private ObjectInputStream oisComunication;
	private ObjectOutputStream oosComunication;

	public ClientHandler(Socket sComuniaction, Socket sNotifier)
	{
		this.sComuniaction = sComuniaction;
		this.sNotifier = sNotifier;
		this.oisComunication = new ObjectInputStream(sComuniaction.getInputStream());
		this.oosComunication = new ObjectOutputStream(sComuniaction.getOutputStream());
		this.oisNotifier = new ObjectInputStream(sNotifier.getInputStream());
		this.oosNotifier = new ObjectOutputStream(sNotifier.getOutputStream());
		notifier = new ClientNotifier(oisNotifier, oosNotifier);
	}

	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				String received;
				oosComunication.writeUTF("What do you want to do? [M] - Message, [E] - Exit");
				received = oisComunication.readUTF();
				if(received.equalsIgnoreCase("E"))
				{
					System.out.println("Closing connection for " + this.sComuniaction);
					oisComunication.close();
					oisNotifier.close();
					oosComunication.close();
					oosNotifier.close();
					sComuniaction.close();
					sNotifier.close();
					System.out.println("Connection closed");
					break;
				}
				else if(received.equalsIgnoreCase("M"))
				{

				}
			}
		}
	}
}

