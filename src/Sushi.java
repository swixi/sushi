public class Sushi {
	public static boolean descriptiveNames = false;
	
	public static void main(String[] args) {
		new Sushi().run();
		System.exit(0);
	}
	
	public void run() {
		System.out.println("\nMMMM SUSHI GO!");
		
		while(true) {
			//TODO had to hack these two method usages because doing it the right way would be too much work.
			int userChoice = Util.intMenu("\nMain menu", new String[]{"New game", "Options", "Hand score test", "Exit"})[0];
			
			if(userChoice == 1)
				newGame();
			else if(userChoice == 2) {
				int optionsChoice = Util.intMenu("\nOptions", new String[]{"Toggle descriptive names (" + 
						(descriptiveNames ? "on" : "off") + ")", "Back to main menu"})[0];
				
				if(optionsChoice == 1)
					descriptiveNames = !descriptiveNames;
				else
					continue;
			}
			else if(userChoice == 3) {
				//userChoice = -1;
				
			}
			else if(userChoice == 4)
				break;
		}		
	}
	
	public void newGame() {
		int playerCount = Util.intMenu("\nSelect number of players (2-5):", 2, 5);
		new Game(playerCount);
	}	
}