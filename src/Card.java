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
	
	public String descriptiveName() {
		if(name.equals("tempura"))
			return "Tempura (x1 = 0, x2 = 5)";
		if(name.equals("sashimi"))
			return "Sashimi (x1 = 0, x2 = 0, x3 = 10)";
		if(name.equals("dumplings"))
			return "Dumplings (x1 = 1, x2 = 3, x3 = 6, x4 = 10, x5 = 15)";
		if(name.equals("wasabi"))
			return "Wasabi (x3 multiplier for next nigiri)";
		if(name.contains("maki")) {
			int num = Integer.parseInt(name.substring(5));
			return "Maki " + num + " (most maki earns 6, second earns 3)";
		}
		if(name.equals("nigiri egg"))
			return "Egg Nigiri (1)";
		if(name.equals("nigiri salmon"))
			return "Salmon Nigiri (2)";
		if(name.equals("nigiri squid"))
			return "Squid Nigiri (3)";
		if(name.equals("chopsticks"))
			return "Chopsticks (can swap for two cards)";
		if(name.equals("pudding"))
			return "Pudding (no points until end of game)";
		
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}