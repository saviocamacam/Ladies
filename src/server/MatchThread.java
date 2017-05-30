package server;

public class MatchThread extends Thread {
	
	private GameManager gameManager;
	private Match currentMatch;
	
	public MatchThread(GameManager gameManager, Match currentMatch) {
		this.gameManager = gameManager;
		this.currentMatch = currentMatch;
	}
	
	public void run() {
		
		while(true) {
			
		}
		
	}

}
