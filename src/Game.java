import java.util.*;

import cards.*;

public class Game {
	final int PLAYER_COUNT;
	final int HAND_SIZE;
	private Map<String, Integer> cardPool;
	private List<Player> players;
	
	public Game(int playerCount) {
		PLAYER_COUNT = playerCount;
		
		if(PLAYER_COUNT == 2)
			HAND_SIZE = 10;
		else if(PLAYER_COUNT == 3)
			HAND_SIZE = 9;
		else if(PLAYER_COUNT == 4)
			HAND_SIZE = 8;
		else if(PLAYER_COUNT == 5)
			HAND_SIZE = 7;
		else
			HAND_SIZE = -1;
		
		System.out.println("Starting new game with " + PLAYER_COUNT + " players.");
		
		players = new ArrayList<Player>(PLAYER_COUNT);
		players.add(new Player(Player.HUMAN));
		for(int i = 0; i < PLAYER_COUNT - 1; i++)
			players.add(new Player(Player.AI));
		
		initPool();
		
		for(int i = 1; i <= 3; i++)
			runRound(i);
		
		
	}

	public void runRound(int round) {
		System.out.println("Starting round " + round + "!");
		
		//key = which player to display to, value = hand
		Map<Integer, List<Card>> hands = initHands();
		
		for(int i = 0; i < HAND_SIZE; i++) {
			
		}
		
	}
	
	//randomly create hands, one for each player, each of size HAND_SIZE
	public HashMap<Integer, List<Card>> initHands() {
		HashMap<Integer, List<Card>> hands = new HashMap<Integer, List<Card>>();
		
		for(int i = 1; i <= PLAYER_COUNT; i++) {
			List<Card> hand = new ArrayList<Card>(HAND_SIZE);
			
			for(int j = 0; j < HAND_SIZE; j++) {
				String randCard = Card.NAMES[(int) System.nanoTime() % Card.NAMES.length];
				int copiesLeft = cardPool.get(randCard);
				
				if(copiesLeft > 0) {
					hand.add(new Card(randCard));
					cardPool.put(randCard, copiesLeft-1);
				}
				else
					j--;
			}
			
			hands.put(i, hand);
		}
		
		
		return hands;
	}
	
	public List<Card> pickFromPool(int number) {
		//for(int i = 0; i < )
		
		
		
		return null;
	}

	public void initPool() {
		cardPool = new HashMap<String, Integer>();
	}
}
