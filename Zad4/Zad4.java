import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.Random;
public class Zad4
{
	public static void main(String[] args)
	{
		Random rand = new Random();
		String f_name_io = new String("new_io.txt");
		String f_name_nio = new String("new_nio.txt");
		int a_size = 1000;
		RandomString randString = new RandomString(a_size);

		// -----------------------------------------------------------------------
		// IO
		// -----------------------------------------------------------------------
		Timer ioWriteTime = new Timer();
		Timer ioReadTime = new Timer();
		Timer nioWriteTime = new Timer();
		Timer nioReadTime = new Timer();
		ioWriteTime.start();
		try
		{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new
			FileOutputStream(f_name_io)));
			writer.write(randString.returnString());
			writer.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Can't open file to write");
		}
		catch(IOException e)
		{
			System.out.println("Write file error");
		}
		ioWriteTime.stop();
		ioReadTime.start();
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new
			FileInputStream(f_name_io)));

			String line = null;
			while((line = reader.readLine()) != null)
				System.out.print(line + " ");
			System.out.print("\n");
			reader.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Can't open file to write");
		}
		catch(IOException e)
		{
			System.out.println("Read file error");
		}
		ioReadTime.stop();
		// -----------------------------------------------------------------------
		// NIO
		// -----------------------------------------------------------------------
		Path path = null;
		Charset charset = null;
		nioWriteTime.start();
		try
		{
			charset = Charset.forName("US-ASCII");
			path = Paths.get(f_name_nio);
		}
		catch(IllegalCharsetNameException e)
		{
			System.out.println("Unknown coding");
		}
		catch(InvalidPathException e)
		{
			System.out.println("Invalid path");
		}
		try
		{
			BufferedWriter writer = Files.newBufferedWriter(path, charset);
			writer.write(randString.returnString());
			writer.close();
		}
		catch(IOException e)
		{
			System.out.println("I/O Error");
		}
		nioWriteTime.stop();
		nioReadTime.start();
		try
		{
			BufferedReader reader = Files.newBufferedReader(path, charset);
			String line = null;
			while((line = reader.readLine()) != null)
				System.out.print(line + " ");
			System.out.print("\n\n");
			reader.close();
		}
		catch(IOException e)
		{
			System.out.println("Read file error");
		}
		nioReadTime.stop();

		System.out.println("Duration for java.io*:\nWrite: " + ioWriteTime.duration()
			+ "\nRead: " + ioReadTime.duration() +"\nDuration for java.nio*:\nWrite: "
			+ nioWriteTime.duration() + "\nRead: " + nioReadTime.duration() + "\n*in ms");
	}
}

class RandomString {
	private String randText;
	private int leftLimit = 33;
	private int rightLimit = 126;

	RandomString(int len) {
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(len);
		for(int i = 0; i < len; i++) {
			int randomInt = leftLimit + (int)(random.nextFloat() * (rightLimit - leftLimit + 1));
			buffer.append((char) randomInt);
		}
		randText = buffer.toString();
	}
	String returnString() {
		return randText;
	}
}

class Timer {
	private long startTime = 0;
	private long endTime = 0;

	Timer(){};
	public void start()
	{
		startTime = System.currentTimeMillis();
	}

	public void stop()
	{
		endTime = System.currentTimeMillis();
	}

	public long duration()
	{
		return endTime - startTime;
	}
}
