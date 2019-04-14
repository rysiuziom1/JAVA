import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Vector;
import java.util.stream.Stream;
import java.io.*;

public class Zad6 {
	public static void main(String[] args) {
		Vector<Integer> firstVector;
		Vector<Integer> secondVector;
		int flag = 0;
		do {
			flag = 0;
			System.out.println("Podaj pierwszy wektor: ");
			firstVector = readVectorOfIntegers();
			System.out.println("Podaj drugi wektor: ");
			secondVector = readVectorOfIntegers();
			try {
				Vector<Integer> sumOfVectors = addVectorsOfIntegers(firstVector, secondVector);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new
				FileOutputStream("sumOfVectors.txt")));
				writer.write(sumOfVectors.toString());
				writer.close();
			}
			catch(WektoryRoznejDlugosciException e) {
				System.out.println(e.errorDescription());
				System.out.println("Sprobuj ponownie");
				flag = 1;
			}
			catch(FileNotFoundException e) {
				System.out.println("Can't open file to write");
			}
			catch(IOException e) {
				System.out.println("Write file error");
			}
		} while(flag == 1);
	}

	static Vector<Integer> readVectorOfIntegers() {
		Scanner scan = new Scanner(System.in);
		return Stream.of(scan.nextLine().split(" "))
			.filter(s -> {
				try{
					Integer.parseInt(s);
				}
				catch(NumberFormatException e) {
					return false;
				}
				return true;
			})
			.map(Integer::valueOf)
			.collect(Collectors.toCollection(Vector::new));
	}

	static Vector<Integer> addVectorsOfIntegers(Vector<Integer> v1, Vector<Integer> v2) throws WektoryRoznejDlugosciException {
		if(v1.size() != v2.size())
			throw new WektoryRoznejDlugosciException(v1.size(), v2.size());
		Vector<Integer> sumOfVectors = new Vector<Integer>();
		for(int i = 0; i < v1.size(); i++)
			sumOfVectors.add(v1.elementAt(i)+v2.elementAt(i));
		return sumOfVectors;
	}
}

class WektoryRoznejDlugosciException extends Exception {
	private int sizeA;
	private int sizeB;

	WektoryRoznejDlugosciException(int a, int b) {
		sizeA = a;
		sizeB = b;
	}

	String errorDescription() {
		return "Dlugosc pierwszego wektora to "+sizeA+" a drugiego to "+sizeB;
	}
}