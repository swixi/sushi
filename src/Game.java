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
		
		int[] finalScores = scoreGame();
		for(int i = 0; i < PLAYER_COUNT; i++)
			System.out.println("Player: " + (i+1) + finalScores[i]);	
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
			
			System.out.println("");
			for(int j = 0; j < PLAYER_COUNT; j++) {
				System.out.println("Player " + (j+1) + ": " + players.get(j).getHand(round));
			}
			System.out.println("");
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
		int[] makiSums = new int[PLAYER_COUNT];
		
		for(int i = 0; i < PLAYER_COUNT; i++) {
			Player player = players.get(i);
			List<Card> hand = player.getHand(round);
			
			//first count maki and remove from hand
			for(int j = 0; j < hand.size(); j++) {
				Card card = hand.get(j);
				if(card.getName().contains("maki")) {
					int amount = Integer.parseInt(card.getName().substring(5));
					makiSums[i] += amount;
					hand.remove(j);
					j--;
				}
			}			
			
			roundScores[i] += scoreHand(hand);			
		}
		
		scoreMaki(makiSums, roundScores);
		
		for(int i = 0; i < PLAYER_COUNT; i++) {
			players.get(i).addScore(roundScores[i]);
			System.out.println("Player " + (i+1) + ": " + players.get(i).getScore() + " (+" + roundScores[i] + ")"
					+ " (Maki score: " + makiSums[i] + ")");
		}
		
		System.out.println("");
	}
	
	//no maki
	//MODIFIES PLAYER HANDS
	public static int scoreHand(List<Card> hand) {
		int score = 0;
		int nextInstance = -1;
		Card card;
		while(hand.size() > 0) {
			card = hand.get(0);
			
			if(card.getName().equals("tempura")) {
				nextInstance = Util.findCardInHand(hand, card);
				if(nextInstance != -1) {
					hand.remove(nextInstance);
					score += 5;
				}
			} 
			else if(card.getName().equals("sashimi")) {
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
			else if(card.getName().equals("dumplings")) {
				nextInstance = Util.findCardInHand(hand, card);
				int count = 1;
				
				while(nextInstance != -1) {					
					count++;
					hand.remove(nextInstance);
					nextInstance = Util.findCardInHand(hand, card);
				}
				
				int dumplingScore = (count*(count+1)/2);
				if(dumplingScore > 15)
					dumplingScore = 15;
				score += dumplingScore;
			}
			else if(card.getName().equals("wasabi")) {
				for(int i = 1; i < hand.size(); i++) {
					if(hand.get(i).getName().contains("nigiri")) {
						if(hand.get(i).getName().contains("egg"))
							score += 3;
						if(hand.get(i).getName().contains("salmon"))
							score += 6;
						if(hand.get(i).getName().contains("squid"))
							score += 9;
						
						hand.remove(i);
						break;
					}
				}
			}
			else if(card.getName().equals("nigiri egg"))
				score += 1;
			else if(card.getName().equals("nigiri salmon"))
				score += 2;
			else if(card.getName().equals("nigiri squid"))
				score += 3;
			
			hand.remove(0);	
		}
		
		return score;
	}
	
	//can probably simplify this a lot if you think about it for 10 minutes
	public void scoreMaki(int[] makiSums, int[] roundScores) {
		int largestSum = 0;
		int secondLargestSum = 0;
		int firstPlaceCount = 0;
		int secondPlaceCount = 0;
		
		for(int sum : makiSums) {
			if(sum > largestSum) {
				secondLargestSum = largestSum;
				largestSum = sum;
			}
		}
		
		for(int i = 0; i < PLAYER_COUNT; i++) {
			if(makiSums[i] == largestSum)
				firstPlaceCount++;
			if(makiSums[i] == secondLargestSum)
				secondPlaceCount++;
		}
		
		int firstPoints = 0;
		int secondPoints = 0;
		if(largestSum > 0)
			firstPoints = 6/firstPlaceCount;
		if(secondLargestSum > 0 && firstPlaceCount == 1)
			secondPoints = 3/secondPlaceCount;
		
		for(int i = 0; i < PLAYER_COUNT; i++) {
			if(makiSums[i] == largestSum)
				roundScores[i] += firstPoints;
			else if(makiSums[i] == secondLargestSum)
				roundScores[i] += secondPoints;
		}
	}
	
	//so many loops...
	public int[] scoreGame() {
		//score puddings
		List<Integer> maxPuddingPlayers = new ArrayList<Integer>();
		List<Integer> minPuddingPlayers = new ArrayList<Integer>();
		int maxPudding = players.get(0).puddingScore();
		int minPudding = maxPudding;
		int[] puddingPoints = new int[PLAYER_COUNT];

		for(int i = 0; i < PLAYER_COUNT; i++) {
			Player curPlayer = players.get(i);
			if(curPlayer.puddingScore() > maxPudding) {
				maxPudding = curPlayer.puddingScore();
				maxPuddingPlayers = new ArrayList<Integer>();
				maxPuddingPlayers.add(i);
			}
			else if(curPlayer.puddingScore() < minPudding) {
				minPudding = curPlayer.puddingScore();
				minPuddingPlayers = new ArrayList<Integer>();
				minPuddingPlayers.add(i);
			}
			else if(curPlayer.puddingScore() == maxPudding) 
				maxPuddingPlayers.add(i);			
			else if(curPlayer.puddingScore() == minPudding)
				minPuddingPlayers.add(i);
		}
		
		for(Integer playerIndex : maxPuddingPlayers) 
			puddingPoints[playerIndex] = 6/maxPuddingPlayers.size();
				
		for(Integer playerIndex : minPuddingPlayers) 
			puddingPoints[playerIndex] = 6/minPuddingPlayers.size();
		
		System.out.println("Pudding scores:");
		for(int i = 0; i < PLAYER_COUNT; i++)
			System.out.println("Player: " + (i+1) + (puddingPoints[i] < 0 ? "-" : "+") + puddingPoints[i]); 
		System.out.println("");
		
		//final scores
		int[] finalScores = new int[PLAYER_COUNT];
		for(int i = 0; i < PLAYER_COUNT; i++) {
			players.get(i).addScore(puddingPoints[i]); 
			finalScores[i] = players.get(i).getScore();
		}



		return finalScores;
		
		/*
		//return winner
		int maxScore = Util.max(finalScores);
		int[] winners = new int[PLAYER_COUNT];
		for(int i = 0; i < PLAYER_COUNT; i++)
			if(players.get(i).getScore() == maxScore)
				winners[i] = 1;
		return winners;
		*/
	}
}
