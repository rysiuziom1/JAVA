import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;
import java.util.*;
public class Zad4
{
	public static void main(String[] args)
	{
		Random rand = new Random();
		String f_name_io = new String("nowy_io.txt");
		String f_name_nio = new String("nowy_nio.txt");
		int a_size = 1000, a_min = 0, a_max = 4000;
		int[] tab = rand.ints(a_size, a_min, a_max).toArray();
		
		// ----------------------------------------------------------------------------------------------------------------
		// IO
		// ----------------------------------------------------------------------------------------------------------------
		long ioStartTimeWrite = System.currentTimeMillis();
		try
		{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f_name_io)));
			for(int i = 0; i < a_size; i++)
			{
				writer.write(Integer.toString(tab[i]));
				writer.newLine();
			}
			writer.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Nie udalo sie otworzyc pliku do zapisu");
		}
		catch(IOException e)
		{
			System.out.println("Blad zapisu do pliku");
		}
		long ioEndTimeWrite = System.currentTimeMillis();
		long ioTimeWrite = ioEndTimeWrite - ioStartTimeWrite;
		long ioStartTimeRead = System.currentTimeMillis();
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f_name_io)));
			String line = null;
			while((line = reader.readLine()) != null)
				System.out.print(line + " ");
			System.out.print("\n");
			reader.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Nie udalo sie otworzyc pliku do odczytu");
		}
		catch(IOException e)
		{
			System.out.println("Blad odczytu z pliku");
		}
		long ioEndTimeRead = System.currentTimeMillis();
		long ioTimeRead = ioEndTimeRead - ioStartTimeRead;
		// ----------------------------------------------------------------------------------------------------------------
		// NIO
		// ----------------------------------------------------------------------------------------------------------------
		Path path = null;
		Charset charset = null;
		long nioStartTimeWrite = System.currentTimeMillis();
		try
		{
			charset = Charset.forName("US-ASCII");
			path = Paths.get(f_name_nio);
		}
		catch(IllegalCharsetNameException e)
		{
			System.out.println("Nieznany typ kodowania");
		}
		catch(InvalidPathException e)
		{
			System.out.println("Invalid Path");
		}
		try
		{
			BufferedWriter writer = Files.newBufferedWriter(path, charset);
			for(int i = 0; i < a_size; i++)
			{
				writer.write(Integer.toString(tab[i]));
				writer.newLine();
			}
			writer.close();
		}
		catch(IOException e)
		{
			System.out.println("I/O Error");
		}
		long nioEndTimeWrite = System.currentTimeMillis();
		long nioTimeWrite = nioEndTimeWrite - nioStartTimeWrite;
		long nioStartTimeRead = System.currentTimeMillis();
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
			System.out.println("Blad odczytu z pliku");
		}
		long nioEndTimeRead = System.currentTimeMillis();
		long nioTimeRead = nioEndTimeRead - nioStartTimeRead;
		
		System.out.println("Czas pracy dla java.io*:\nZapis: " + ioTimeWrite + "\nOdczyt: " 
			+ ioTimeRead +"\nCzas pracy dla java.nio*:\nZapis: " + nioTimeWrite + "\nOdczyt: " 
			+ nioTimeRead + "\n*w ms");
	}
}