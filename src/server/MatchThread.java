package server;

public class MatchThread extends Thread {
	
	private GameManager gameManager;
	private Match currentMatch;
	private String oponent;
	private LadiesInterface ladies;
	
	public MatchThread(GameManager gameManager) {
		this.gameManager = gameManager;
		this.setCurrentMatch(currentMatch);
	}
	
	public void run() {
		
		this.notifyOponents();
		this.setOnTime(currentMatch.getPlayer1().getNickname());
		
		while(ladies.getPiecesPlayer1().size() != 0 && ladies.getPiecesPlayer2().size() != 0) {
			
		}
		
	}

	private void setOnTime(String nickname) {
		
	}

	private void notifyOponents() {
		gameManager.setPrivateAddress(currentMatch.getPlayer1().getIp());
		this.oponent = currentMatch.getPlayer2().getNickname();
		gameManager.sendFormatedMessage(2);
		
		gameManager.setPrivateAddress(currentMatch.getPlayer2().getIp());
		this.oponent = currentMatch.getPlayer1().getNickname();
		gameManager.sendFormatedMessage(2);
	}

	public Match getCurrentMatch() {
		return currentMatch;
	}

	public void setCurrentMatch(Match currentMatch) {
		this.currentMatch = currentMatch;
	}

	public String getOponent() {
		return oponent;
	}

	public void setLadies(LadiesInterface ladies) {
		this.ladies = ladies;
	}

}
