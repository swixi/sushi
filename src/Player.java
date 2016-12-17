public class Player {
	static final int HUMAN = 0;
	static final int AI = 1;
	private final int TYPE;

	public Player(int type) {
		TYPE = type;
	}

	public int getType() {
		return TYPE;
	}

}
