import java.util.Scanner;
import java.io.*;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Zad5
{
	public static void main(String[] args)
	{
		Scanner scan = new Scanner(System.in);
		String userPath = new String("");
		String pathToFileWithUserPath = new String("path.txt");
		System.out.print("Input file path: ");
		userPath = scan.nextLine();
		try
		{
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new
			FileOutputStream(pathToFileWithUserPath)));
			writer.write(userPath);
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

		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new
			FileInputStream(userPath)));

			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			String fileAsString = sb.toString();
			reader.close();

			System.out.print(fileAsString);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Can't open file to read");
		}
		catch(IOException e)
		{
			System.out.println("Read file error");
		}
	}
}
