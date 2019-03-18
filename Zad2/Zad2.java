import java.security.InvalidParameterException;
class Zad2
{
	public static void main(String[] args)
	{
		try {
			validateArguments(args);
		}
		catch (InvalidParameterException e) {
			System.out.println("Three arguments required");
			System.exit(0);
		}
		catch(NumberFormatException e) {
			System.out.println("1st arguments must be String, 2nd and 3rd Integer");
			System.exit(0);
		}
		catch(NegativeArraySizeException e) {
			System.out.println("2nd arguments must be higher than 3rd");
			System.exit(0);
		}

		try {
			MySubstring subString = new MySubstring(args[0],
			Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			System.out.println(subString.print());
		}
		catch(IndexOutOfBoundsException e) {
			System.out.println("Index is out of bounds");
			System.exit(0);
		}
	}

	static void validateArguments(String[] arguments) {
		if(arguments.length < 3)
			throw new InvalidParameterException();
		int a = 0, b = 0;
		try {
			a = Integer.parseInt(arguments[1]);
			b = Integer.parseInt(arguments[2]);
		}
		catch(NumberFormatException e) {
			throw e;
		}
		if(b < a)
			throw new NegativeArraySizeException();
	}
}

class MySubstring {
	private String text = "";
	private int begin = 0;
	private int end = 0;
	private int len = 0;
	private String subText = "";
	MySubstring(String t, int b, int en) {
		text = t;
		begin = b;
		end = en;
		len = end - begin + 1;

		try {
			makeSubstring();
		}
		catch(IndexOutOfBoundsException e) {
			throw e;
		}
	}

	private void makeSubstring() {
		char[] data;
		data = new char[len];
		try {
			for(int i = 0, j = begin; i < len; i++, j++)
			{
				data[i] = text.charAt(j);
			}
			subText = String.valueOf(data);
		}
		catch(IndexOutOfBoundsException e) {
			throw e;
		}
		catch(NegativeArraySizeException e) {
			throw e;
		}
	}

	public String print() {
		return subText;
	}
}
