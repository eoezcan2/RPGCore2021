package at.emreeocn.rpgcore.util;

public enum ServerActionType {

	RELOAD("Server Reload"),
	STOP("Server Stopp");
	
	String text;
	
	ServerActionType(String text) {
		this.text = text;
	}
	
	public String getText() { return text; }
	
}
