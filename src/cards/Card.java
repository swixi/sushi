package cards;

public class Card {
	private String name;
	public static String[] NAMES = new String[]{"tempura", "sashimi", "dumplings", "wasabi", "maki 1", "maki 2",
			"maki 3", "nigiri egg", "nigiri salmon", "nigiri squid", "chopsticks", "pudding"};
	
	public Card(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
