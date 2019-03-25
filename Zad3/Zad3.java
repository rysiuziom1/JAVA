import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Zad3
{
	public static void main(String[] args)
	{
		Random rand = new Random();
		Scanner scan = new Scanner(System.in);
		int r_number = 0, u_number = 0, counter = 0;
		String repeat = "y";
		do
		{
			r_number = rand.nextInt(101);
			do
			{
				System.out.print("Inupt number: ");
				try
				{
					u_number = scan.nextInt();
					if(u_number < r_number)
						System.out.println("Number is lower than random");
					else if(u_number > r_number)
						System.out.println("Number is higher than random");
					counter++;
				}
				catch(InputMismatchException e)
				{
					System.out.println("Inputed value isn't a number. Try again.");
					scan.nextLine();
				}
			} while(u_number != r_number);
			System.out.println("Congratulations! You guessed the number in "
			+ counter + " tries! Do you wanna play again?");
			counter = 0;
			do
			{
				System.out.print("(Y - yes / N - no): ");
				repeat = String.valueOf(scan.next().charAt(0));
			} while(!repeat.matches("[YyNn]"));
		} while(repeat.matches("[Yy]"));
	}
}
