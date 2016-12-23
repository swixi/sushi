import java.util.*;

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
		
		System.out.println("\nStarting new game with " + PLAYER_COUNT + " players.\n");
		
		players = new ArrayList<Player>(PLAYER_COUNT);
		players.add(new Player(Player.HUMAN));
		for(int i = 0; i < PLAYER_COUNT - 1; i++)
			players.add(new Player(Player.AI));
		
		initPool();
		
		for(int i = 0; i < 3; i++)
			runRound(i);
		
		//scoreGame();
			
	}

	public void runRound(int round) {		
		System.out.println(" --------\n|ROUND " + (round+1) + "!|\n --------\n");
		for(Player player : players)
			player.initDeckHand(HAND_SIZE);
		
		//key = which player to display to, value = hand
		Map<Integer, List<Card>> hands = initHands();
		
		for(int i = 0; i < HAND_SIZE; i++) {
			//process humans
			Player curPlayer = players.get(0);			
			List<Card> curHand = hands.get(0);
			int choice = Util.intMenu("Choose a card:", Util.cardsToStrings(curHand));
			curPlayer.addToDeck(curHand.get(choice-1), round);
			curHand.remove(choice-1);
			
			//process AI (right now, always choose the first card)
			for(int j = 1; j < PLAYER_COUNT; j++) {
				players.get(j).addToDeck(hands.get(j).get(0), round);
				hands.get(j).remove(0);
			}
			
			hands = rotateHands(hands);
			System.out.println("\nYour cards: " + curPlayer.getHand(round));
		}
		
		scoreRound(round);
		Util.waitForEnter();
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
	
	public void scoreRound(int round) {
		System.out.println("\nScores:");
		int[] roundScores = new int[PLAYER_COUNT];
		int[] makiScores = new int[PLAYER_COUNT];
		
		for(int i = 0; i < PLAYER_COUNT; i++) {
			Player player = players.get(i);
			List<Card> hand = player.getHand(round);
			roundScores[i] += scoreHand(hand);
			player.addScore(roundScores[i]);
			
			for(Card card : hand) {
				if(card.getName() == "maki 1")
					makiScores[i] += 1;
				if(card.getName() == "maki 2")
					makiScores[i] += 2;
				if(card.getName() == "maki 3")
					makiScores[i] += 3;
			}
		}
		
		
		
		for(int i = 0; i < PLAYER_COUNT; i++) 
			System.out.println("Player " + (i+1) + ": " + players.get(i).getScore() + " (+" + roundScores[i] + ")");
		
		System.out.println("");
	}
	
	//no maki
	//MODIFIES PLAYER HANDS
	public int scoreHand(List<Card> hand) {
		int score = 0;
		int nextInstance = -1;
		Card card;
		while(hand.size() > 0) {
			card = hand.get(0);
			
			if(card.getName() == "tempura") {
				nextInstance = Util.findCardInHand(hand, card);
				if(nextInstance != -1) {
					hand.remove(nextInstance);
					score += 5;
				}
			} else if(card.getName() == "sashimi") {
				nextInstance = Util.findCardInHand(hand, card);
				if(nextInstance != -1) {
					hand.remove(nextInstance);
					nextInstance = Util.findCardInHand(hand, card);
					if(nextInstance != -1) {
						hand.remove(nextInstance);
						score += 10;
					}
				}
			}
				
				
				
			hand.remove(0);	
		}
		
		
		return score;
	}
}
