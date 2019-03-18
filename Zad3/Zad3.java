import java.util.Random;
import java.util.Scanner;
public class Zad3
{
	public static void main(String[] args)
	{
		Random rand = new Random();
		Scanner scan = new Scanner(System.in);
		int r_number = 0, u_number = 0;
		String repeat = "y";
		do
		{
			r_number = rand.nextInt(101);
			do
			{
				System.out.print("Podaj liczbe: ");
				u_number = scan.nextInt();
				if(u_number < r_number)
					System.out.println("Podana przez Ciebie liczba jest mniejsza od wylosowanej");
				else if(u_number > r_number)
					System.out.println("Podana przez Ciebie liczba jest wieksza od wylosowanej");
			} while(u_number != r_number);
			System.out.println("Udalo Ci sie odgadnac liczbe! Chcesz zagrac ponownie?");
			do
			{
				System.out.print("(Y - tak / N - nie): ");
				repeat = String.valueOf(scan.next().charAt(0));
			} while(!repeat.matches("[YyNn]"));
		} while(repeat.matches("[Yy]"));
	}
}