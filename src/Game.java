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
		
		for(int i = 0; i < 3; i++)
			runRound(i);
			
	}

	public void runRound(int round) {		
		System.out.println("Starting ROUND " + (round+1) + "!");
		for(Player player : players)
			player.initRoundDeck(HAND_SIZE);
		
		//key = which player to display to, value = hand
		Map<Integer, List<Card>> hands = initHands();
		
		for(int i = 0; i < HAND_SIZE; i++) {
			System.out.println("Your cards: " + players.get(0).getDeck(round));
			int choice = Util.intMenu("Choose a card:", Util.cardsToStrings(hands.get(0)));
			players.get(0).addToDeck(hands.get(0).get(choice-1), round);
			hands.get(0).remove(choice-1);
			
			//pick for the AI
			for(int j = 1; j < PLAYER_COUNT; j++)
				hands.get(j).remove(0);
			
			hands = rotateHands(hands);
			
		}
	}
	
	//moves each hand to the left
	public Map<Integer, List<Card>> rotateHands(Map<Integer, List<Card>> hands) {
		Map<Integer, List<Card>> rotatedHands = new HashMap<Integer, List<Card>>();
		for(int i = 0; i < PLAYER_COUNT; i++) {
			rotatedHands.put(i, hands.get( (i+1) % PLAYER_COUNT ));
		}
		return rotatedHands;
	}
	
	//randomly create hands, one for each player, each of size HAND_SIZE
	public HashMap<Integer, List<Card>> initHands() {
		HashMap<Integer, List<Card>> hands = new HashMap<Integer, List<Card>>();
		
		for(int i = 0; i < PLAYER_COUNT; i++) {
			List<Card> hand = new ArrayList<Card>(HAND_SIZE);
			
			for(int j = 0; j < HAND_SIZE; j++) {
				String randCard = Card.NAMES[(int) (System.nanoTime() % Card.NAMES.length)];
				//System.out.println(randCard);
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

	public void initPool() {
		cardPool = new HashMap<String, Integer>();
		
		cardPool.put("tempura", 14);
		cardPool.put("sashimi", 14);
		cardPool.put("dumplings", 14);
		cardPool.put("maki 1", 6);
		cardPool.put("maki 2", 12);
		cardPool.put("maki 3", 8);
		cardPool.put("nigiri egg", 5);
		cardPool.put("nigiri salmon", 10);
		cardPool.put("nigiri squid", 5);
		cardPool.put("pudding", 10);
		cardPool.put("wasabi", 6);
		cardPool.put("chopsticks", 4);		
	}
	
	public int poolSize() {
		int size = 0;
		Set<String> keys = cardPool.keySet();
		for(String card : keys)
			size += cardPool.get(card);
		return size;
	}
}
