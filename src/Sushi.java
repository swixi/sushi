public class Sushi {	
	private boolean descriptiveNames = false;
	public static void main(String[] args) {
		new Sushi().run();
		System.exit(0);
	}
	
	public void run() {
		System.out.println("MMMM SUSHI GO!");
		
		while(true) {
			int userChoice = Util.intMenu("\nMain menu", new String[]{"New game", "Options", "Exit"});
			
			if(userChoice == 1)
				newGame();
			else if(userChoice == 2) {
				int optionsChoice = Util.intMenu("\nOptions", new String[]{"Toggle descriptive names (" + 
						(descriptiveNames ? "on" : "off") + ")", "Back to main menu"});
				
				if(optionsChoice == 1)
					descriptiveNames = !descriptiveNames;
				else
					continue;
			}
			else if(userChoice == 3)
				break;
		}		
	}
	
	public void newGame() {
		int playerCount = Util.intMenu("\nSelect number of players (2-5):", 2, 5);
		new Game(playerCount);
	}	
}