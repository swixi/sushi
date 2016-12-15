import java.util.Scanner;

public class Sushi {
	Scanner reader;
	static final int NEW_GAME = 0;
	static final int EXIT = 1;
	
	public static void main(String[] args) {
		try {
			new Sushi().run();
		}
		catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		System.exit(0);
	}
	
	public void run() {
		reader = new Scanner(System.in);
		System.out.println("MMMM SUSHI GO!");
		
		while(true) {
			int userChoice = menu();
			
			if(userChoice == EXIT)
				break;
			else if(userChoice == NEW_GAME)
				newGame();
		}
		
	}
	
	public int menu() {
		System.out.print("Type \"new\" for a new game; type \"exit\" to exit: ");
		String input = reader.nextLine();
		
		if(input.contains("new"))
			return NEW_GAME;
		else if(input.contains("exit"))
			return EXIT;
		else {
			System.out.println("I didn't say you could type that...");
			return menu();
		}
	}
	
	public void newGame() {
		System.out.print("Select number of players (2-5): ");
		String input = reader.nextLine();
		
		try {
			int players = Integer.parseInt(input);
			
			if(players >= 2 && players <= 5)
				new Game(players);
			else {
				System.out.println("Can't play Sushi Go! with that many players.");
				newGame();
			}
		} 
		catch(NumberFormatException e) {
			System.out.println("That's not a number, dingus.");
			newGame();
		}
	}	
}