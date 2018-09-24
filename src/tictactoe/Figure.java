package tictactoe;

public enum Figure{
	CIRCLE("CIRCLE", 1),
	X("X", 2);
	
	private String name;
	private int value;
	
	private Figure(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}
	
	public int getValue() {
		return value;
	}
	
}
