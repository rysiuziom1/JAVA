import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*; 
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.Semaphore;

public class Server
{
	public static void main(String[] args)
	{
		// server is listening on port 5056
		try
		{
			ServerSocket ss1 = new ServerSocket(5056);
			ServerSocket ss2 = new ServerSocket(5057);

			// infinity loop to getting clients requests
			while(true)
			{
				// declaring socket for communication
				Socket sCommunication = null;
				Socket sNotifier = null;
				// declaring socket for notifier

				// accepting client request
				sCommunication = ss1.accept();
				ObjectOutputStream oosCommunication = new ObjectOutputStream(sCommunication.getOutputStream());
				ObjectInputStream oisCommunication = new ObjectInputStream(sCommunication.getInputStream());
				System.out.println("A new Client " + sCommunication + " is connected");

				sNotifier = ss2.accept();
				ObjectOutputStream oosNotifier = new ObjectOutputStream(sNotifier.getOutputStream());
				ObjectInputStream oisNotifier = new ObjectInputStream(sNotifier.getInputStream());
				System.out.println("Notifier for Client " + sNotifier + " is declared");
				
				Semaphore sem = new Semaphore(1);
				SortedMap<Date, String> listOfNotifies = new TreeMap<Date, String>();

				// running client handler
				Thread cHandler = new ClientHandler(sCommunication, sNotifier, oosCommunication, oisCommunication, oosNotifier, listOfNotifies, sem);
				Thread cNotifier = new ClientNotifier(sNotifier, oosNotifier, oisNotifier, listOfNotifies, sem);
				cHandler.start();
				cNotifier.start();

				System.out.println("Notifier for Client " + sCommunication + " is running");
			}
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

class ClientHandler extends Thread
{
	final Socket sCommunication;
	final Socket sNotifier;
	final ObjectInputStream oisCommunication;
	final ObjectOutputStream oosCommunication;
	final ObjectOutputStream oosNotifier;
	private SortedMap<Date, String> listOfNotifies;
	private Semaphore sem;

	public ClientHandler(Socket sCommunication, Socket sNotifier, ObjectOutputStream oosCommunication, 
		ObjectInputStream oisCommunication, ObjectOutputStream oosNotifier, SortedMap<Date, String> listOfNotifies, Semaphore sem) throws IOException
	{
		this.sCommunication = sCommunication;
		this.sNotifier = sNotifier;
		this.oosCommunication = oosCommunication;
		this.oisCommunication = oisCommunication;
		this.oosNotifier = oosNotifier;
		this.listOfNotifies = listOfNotifies;
		this.sem = sem;
	}

	@Override
	public void run()
	{
		while(true)
		{
			String received;
			try
			{
				oosCommunication.writeObject(new String("What do you want to do? [M] - Message, [E] - Exit"));
				received = (String) oisCommunication.readObject();
				if(received.equalsIgnoreCase("E"))
				{
					System.out.println("Closing connection for " + this.sCommunication);
					oisCommunication.close();
					oosCommunication.close();
					sCommunication.close();
					sNotifier.close();
					System.out.println("Connection closed");
					break;
				}
				else if(received.equalsIgnoreCase("M"))
				{
					oosCommunication.writeObject(new String("Type a message: "));
					String message = (String) oisCommunication.readObject();
					oosCommunication.writeObject(new String("Type a time (dd-MM-yyyy HH:mm:ss): "));
					String timeString = (String) oisCommunication.readObject();
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					dateFormat.setLenient(false);
					Date time = dateFormat.parse(timeString);
					if(DateValidator.validate(time))
						throw new DateInPastException("Date in past");
					sem.acquire();
					listOfNotifies.put(time, message);
					System.out.println("Notifications in " + sNotifier + " queue: " + listOfNotifies.size());
					sem.release();
					oosCommunication.writeObject(new String("Notification added"));
				}
			}
			catch(IllegalThreadStateException e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			catch(IOException e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			catch(ParseException e)
			{
				System.err.println("Invalid date format");
				e.printStackTrace();
			}
			catch(ClassNotFoundException e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			catch(InterruptedException e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			catch(DateInPastException e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
				try
				{
					oosCommunication.writeObject(e.getMessage());
				}
				catch(IOException e2)
				{
					System.err.println(e2.getMessage());
					e2.printStackTrace();
				}
			}
		}
		try
		{
			sCommunication.close();
			sNotifier.close();
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

class ClientNotifier extends Thread
{
	final Socket sNotifier;
	final ObjectInputStream ois;
	final ObjectOutputStream oos;
	private SortedMap<Date, String> listOfNotifies;
	private Semaphore sem;

	public ClientNotifier(Socket sNotifier, ObjectOutputStream oosNotifier, ObjectInputStream oisNotifier, SortedMap<Date, String> listOfNotifies, Semaphore sem) throws IOException
	{
		this.sNotifier = sNotifier;
		this.oos = oosNotifier;
		this.ois = oisNotifier;
		this.listOfNotifies = listOfNotifies;
		this.sem = sem;
	}

	@Override
	public void run()
	{
		while(!this.interrupted())
		{
			try
			{
				sem.acquire();
				Date date = new Date();
				if(sNotifier.isClosed())
					this.interrupt();
				if(listOfNotifies.size() > 0)
				{
					if(date.compareTo(listOfNotifies.firstKey()) >= 0)
					{
						oos.writeObject(listOfNotifies.remove(listOfNotifies.firstKey()));
						System.out.println("Notification sended");
						System.out.println("Notifications in " + sNotifier + " queue: " + listOfNotifies.size());
					}
				}
				sem.release();
				this.sleep(200);
			}
			catch(ClassCastException e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			catch(IOException e)
			{
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			catch(InterruptedException e){}
		}
	}
}


class Notify implements Serializable
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

class DateValidator
{
	static public boolean validate(Date date)
	{
		Date currentDate = new Date();
		if(currentDate.compareTo(date) < 0)
			return false;
		return true;
	}
}

class DateInPastException extends Exception
{
	public DateInPastException(String errorMessage)
	{
		super(errorMessage);
	}
}


