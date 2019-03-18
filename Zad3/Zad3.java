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
				System.out.print("Inupt number: ");
				u_number = scan.nextInt();
				if(u_number < r_number)
					System.out.println("Number is lower than random");
				else if(u_number > r_number)
					System.out.println("Number is higher than random");
			} while(u_number != r_number);
			System.out.println("Congratulations! You guessed the number! Do you wanna play again?");
			do
			{
				System.out.print("(Y - yes / N - no): ");
				repeat = String.valueOf(scan.next().charAt(0));
			} while(!repeat.matches("[YyNn]"));
		} while(repeat.matches("[Yy]"));
	}
}