import java.util.Scanner;
import java.io.*;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Zad5 {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String userPath = new String("");
		String pathToFileWithUserPath = new String("path.txt");
		System.out.print("Input file path: ");
		userPath = scan.nextLine();
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new
			FileOutputStream(pathToFileWithUserPath)));
			writer.write(userPath);
			writer.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Can't open file to write");
		}
		catch(IOException e) {
			System.out.println("Write file error");
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new
			FileInputStream(userPath)));

			String line = null;
			StringBuilder sb = new StringBuilder();
			while((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			String fileAsString = sb.toString();
			reader.close();
			MyKeyListener frame = new MyKeyListener("Key Listener", fileAsString);
		}
		catch(FileNotFoundException e) {
			System.out.println("Can't open file to read");
			System.exit(0);
		}
		catch(IOException e) {
			System.out.println("Read file error");
			System.exit(0);
		}
	}
}

class MyKeyListener extends JFrame implements KeyListener {
	private String stringToPrint;
	private Random rand;
	private int startIndex;

	MyKeyListener(String title, String s) {
		super(title);
		stringToPrint = s;
		rand = new Random();
		startIndex = 0;
		addKeyListener(this);
		pack();
		setVisible(true);
	}

	void printRandom() {
		int number = rand.nextInt(4)+1;
		try {
			System.out.print(stringToPrint.substring(startIndex, startIndex+number));
			startIndex += number;
		}
		catch(IndexOutOfBoundsException e) {
			System.out.print(stringToPrint.substring(startIndex));
			startIndex = stringToPrint.length();
			throw e;
		}
	} 

	@Override
	public void keyReleased(KeyEvent event) {
		try {
			printRandom();
		}
		catch(IndexOutOfBoundsException e) { 
			removeKeyListener(this);
			dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent event){}

	@Override
	public void keyTyped(KeyEvent event){}
}
