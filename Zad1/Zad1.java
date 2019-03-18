import java.text.NumberFormat;
import java.security.InvalidParameterException;

class Zad1
{
	public static void main(String[] args)
	{
		try {
			validateArguments(args);
		}
		catch(InvalidParameterException e) {
			System.out.println("Three arguments required");
			System.exit(0);
		}
		catch(NumberFormatException e) {
			System.out.println("Arguments must be Integers");
			System.exit(0);
		}
		
		Equation equation = new Equation(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
		System.out.println(equation.print());
	}
	
	static void validateArguments(String[] arguments) {
		if(arguments.length < 3)
			throw new InvalidParameterException();
		double a = 0, b = 0, c = 0;
		try {
			a = Double.parseDouble(arguments[0]);
			b = Double.parseDouble(arguments[1]);
			c = Double.parseDouble(arguments[2]);
		}
		catch(NumberFormatException e) {
			throw e;
		}
	}
}

class Equation {
	private int A = 0;
	private int B = 0;
	private int C = 0;
	private int delta = 0;
	private double x1 = 0;
	private double x2 = 0;
	
	Equation(int a, int b, int c)
	{
		A = a;
		B = b;
		C = c;
		delta = B*B - 4*A*C;
		
		solveEquation();
	}
	
	private void solveEquation()
	{
		if (A == 0 && B != 0 )
			x1 = -(double)C/B;
		else if(A == 0 && B == 0)
			x1 = -C;
		else if(delta > 0)
		{
			x1 = (-B-Math.sqrt(delta))/(2*A);
			x2 = (-B+Math.sqrt(delta))/(2*A);
		}
		else if(delta == 0)
			x1 = -B/(2*A);
	}
	public String print()
	{
		if(delta > 0 && A != 0)
			return "Your equation has two solutions: x1 = " + x1 + ", x2 = " + x2;
		else if(delta == 0 || A == 0)
			return "Your equation has one solution: x = " + x1;
		else
			return "Your equation doesn't have any solutions";
	}
}