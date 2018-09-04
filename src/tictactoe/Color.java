package tictactoe;

public enum Color {
	WHITE(1),
	BLACK(2);
	
	private int value;
	
	private Color(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
