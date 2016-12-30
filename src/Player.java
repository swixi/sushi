import java.util.*;

public class Player {
	static final int HUMAN = 0;
	static final int AI = 1;
	private final int TYPE;
	private int score;
	private List<List<Card>> deck;
	private int puddings;
	private int[] maki;
	String name;

	public Player(int type, String name) {
		TYPE = type;
		deck = new ArrayList<List<Card>>(3);
		maki = new int[3];
		puddings = 0;
		this.name = name;
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
	
	public List<Card> getHand(int round) {
		return deck.get(round);
	}
	
	public void initDeckHand(int handSize){
		List<Card> hand = new ArrayList<Card>(handSize);
		deck.add(hand);
	}
	
	public void addToDeck(Card card, int round) {
		deck.get(round).add(card);
	}
	
	public void addPudding() {
		puddings++;
	}
	
	public int puddingScore() {
		return puddings;
	}
	
	public void addMaki(int amount, int round) {
		maki[round] += amount;
	}
	
	public int getMaki(int round) {
		return maki[round];
	}
	
	@Override
	public String toString() {
		return name;
	}
}
