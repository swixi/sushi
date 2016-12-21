public class Sushi {	
	public static void main(String[] args) {
		new Sushi().run();
		System.exit(0);
	}
	
	public void run() {
		System.out.println("MMMM SUSHI GO!");
		
		while(true) {
			int userChoice = Util.intMenu(new String[]{"Choose an option", "New game", "Exit"});
			
			if(userChoice == 1)
				newGame();
			else if(userChoice == 2)
				break;
		}		
	}
	
	public void newGame() {
		int playerCount = Util.intMenu("Select number of players (2-5)", 2, 5);
		new Game(playerCount);
	}	
}