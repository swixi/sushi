import java.util.List;
import java.util.Scanner;
import cards.Card;

public class Util {	
	public static final int PARSE_ERROR = -1;
	
	//INPUT: a title and an array of strings (options) to display to the user
	//the options will be displayed with the numbers numbering[0], numbering[1], ...
	//choices must contain non-negative integers (else will throw errors)
	//OUTPUT: the user's (valid) choice
	public static int intMenu(String title, String[] options, int[] numbering) {
		String display = title;
		
		for(int i = 0; i < options.length; i++) {
			display += "\n" + numbering[i] + ". " + options[i];
		}
		
		System.out.println(display);
		
		while(true) {
			int userChoice = strToInt(getUserChoice());
			
			if(userChoice == PARSE_ERROR) {
				System.out.println("That's not a number, dingus.");
				continue;
			}
			
			if(findInArray(numbering, userChoice))
				return userChoice;
			
			System.out.println("Not a valid option.");
		}	
	}
	
	//INPUT: a title and an array of strings (options) to display to the user
	//the options will be displayed with the default numbering 1, 2, ...
	//OUTPUT: the user's (valid) choice or -1 if an error
	public static int intMenu(String title, String[] options) {		
		int[] numbering = new int[options.length];
		for(int i = 0; i < options.length; i++) {
			numbering[i] = i+1;
		}
		return intMenu(title, options, numbering);		
	}
	
	//no options displayed, only a given set of valid numbers
	public static int intMenu(String title, int[] numbering) {
		return intMenu(title, new String[0], numbering);
	}
	
	//assuming min and max are non-negative
	public static int intMenu(String title, int min, int max) {
		int[] numbering = new int[max-min+1];
		for(int i = 0; i < numbering.length; i++) {
			numbering[i] = min+i;
		}
		return intMenu(title, numbering);
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
	
	public static String[] cardsToStrings(List<Card> input) {
		String[] output = new String[input.size()];
		for(int i = 0; i < input.size(); i++)
			output[i] = input.get(i).getName();
		return output;			
	}
	
	public static String[] concat(String[] arr1, String[] arr2) {
		String[] output = new String[arr1.length + arr2.length];
		for(int i = 0; i < arr1.length; i++)
			output[i] = arr1[i];
		for(int i = 0; i < arr2.length; i++)
			output[arr1.length + i] = arr2[i];
		return output;
	}
}