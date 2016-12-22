import cards.Card;
import java.util.*;

public class Player {
	static final int HUMAN = 0;
	static final int AI = 1;
	private final int TYPE;
	private int score;
	private List<List<Card>> deck;

	public Player(int type) {
		TYPE = type;
		deck = new ArrayList<List<Card>>(3);
	}

	public int getType() {
		return TYPE;
	}
	
	public void addScore(int amount) {
		score += amount;
	}
	
	public int getScore() {
		return score;
	}
	
	public List<Card> getDeck(int round) {
		return deck.get(round);
	}
	
	public void initDeck(int handSize){
		List<Card> roundDeck = new ArrayList<Card>(handSize);
		deck.add(roundDeck);
	}
	
	public void addToDeck(Card card, int round) {
		deck.get(round).add(card);
	}
}
