import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Zad3 {
	public static void main(String[] args)
	{
		Random rand = new Random();
		Scanner scan = new Scanner(System.in);
		int randomNumber = 0, userNumber = 0, counter = 0;
		String repeat = "y";
		do
		{
			randomNumber = rand.nextInt(101);
			do
			{
				System.out.print("Inupt number: ");
				try
				{
					userNumber = scan.nextInt();
					if(userNumber < randomNumber)
						System.out.println("Number is lower than random");
					else if(userNumber > randomNumber)
						System.out.println("Number is higher than random");
					counter++;
				}
				catch(InputMismatchException e)
				{
					System.out.println("Inputed value isn't a number. Try again.");
					scan.nextLine();
				}
			} while(userNumber != randomNumber);
			System.out.println("Congratulations! You guessed the number in "
			+ counter + " tries! Do you want play again?");
			counter = 0;
			do
			{
				System.out.print("(Y - yes / N - no): ");
				repeat = String.valueOf(scan.next().charAt(0));
			} while(!repeat.matches("[YyNn]"));
		} while(repeat.matches("[Yy]"));
	}
}
