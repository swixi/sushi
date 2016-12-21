import java.util.Scanner;

public class Util {	
	public static final int PARSE_ERROR = -1;
	
	//INPUT: an array of strings to display to the user, where arg[0] is the title, and arg[1] -> arg[n] are the options
	//the options will be displayed with the numbering choices[0], choices[1], ...
	//choices must contain non-negative integers (else will throw errors)
	//OUTPUT: the user's (valid) choice
	public static int intMenu(String[] args, int[] choices) {
		String display = args[0] + ": ";
		
		//the indices of choices are assumed to match those of args for convenience
		for(int i = 1; i < args.length; i++) {
			display += "\n" + choices[i] + ". " + args[i];
		}
		
		System.out.println(display);
		
		while(true) {
			int userChoice = strToInt(getUserChoice());
			
			if(userChoice == PARSE_ERROR) {
				System.out.println("That's not a number, dingus.");
				continue;
			}
			
			if(findInArray(choices, userChoice))
				return userChoice;
			
			System.out.println("Not a valid option.");
		}	
	}
	
	//INPUT: an array of strings to display to the user, where arg[0] is the title, and arg[1] -> arg[n] are the options
	//the options will be displayed with the numbering 1, 2, ...
	//OUTPUT: the user's (valid) choice or -1 if an error
	public static int intMenu(String[] args) {
		if(args.length < 2) {
			System.out.println("Error: title with no options!");
			return -1;
		}
		
		int[] choices = new int[args.length];
		for(int i = 1; i < args.length; i++) {
			choices[i] = i;
		}
		return intMenu(args, choices);		
	}
	
	public static int intMenu(String title, int[] choices) {
		String[] args = new String[1];
		args[0] = title;
		return intMenu(args, choices);
	}
	
	//assuming min and max are non-negative
	public static int intMenu(String title, int min, int max) {
		String[] args = new String[1];
		args[0] = title;
		int[] choices = new int[max-min+1];
		for(int i = 0; i < choices.length; i++) {
			choices[i] = min+i;
		}
		return intMenu(args, choices);
	}
	
	public static String strMenu(String[] args) {
		return null;
	}
	
	public static String getUserChoice() {
		Scanner scanner = new Scanner(System.in);
		String output = scanner.nextLine();
		//scanner.close();
		return output;
	}
	
	public static int strToInt(String input) {
		try {
			int parsed = Integer.parseInt(input);
			return parsed;
		}
		catch(NumberFormatException e) {
			return PARSE_ERROR;
		}
	}
	
	public static boolean findInArray(int[] arr, int key) {
		for(int elt : arr)
			if(elt == key)
				return true;
		return false;
	}
}