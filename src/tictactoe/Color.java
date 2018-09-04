package tictactoe;

public enum Color {
	WHITE("BRANCO", 1),
	BLACK("PRETO", 2);
	
	private String name;
	private int value;
	
	private Color(String name, int value) {
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
