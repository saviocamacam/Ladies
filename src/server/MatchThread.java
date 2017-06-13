package server;

import java.rmi.RemoteException;

public class MatchThread extends Thread {
	
	private GameManager gameManager;
	private Match currentMatch;
	private String oponent;
	private LadiesInterface ladies;
	private String onTime;
	
	public MatchThread(GameManager gameManager) {
		this.gameManager = gameManager;
		this.setCurrentMatch(currentMatch);
	}
	
	public void run() {
		
		this.notifyOponents();
		this.setOnTime(currentMatch.getPlayer1().getNickname());
		
		try {
			while(ladies.getPiecesPlayer1().size() != 0 && ladies.getPiecesPlayer2().size() != 0) {
				
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	private void setOnTime(String nickname) {
		this.onTime = nickname;
	}

	public String getOnTime() {
		return onTime;
	}

	private void notifyOponents() {
		System.out.println("IP1: " + currentMatch.getPlayer1().getIp());
		gameManager.setPrivateAddress(currentMatch.getPlayer1().getIp());
		this.oponent = currentMatch.getPlayer2().getNickname();
		gameManager.sendFormatedMessage(2);
		
		System.out.println("IP2: " + currentMatch.getPlayer2().getIp());
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
		System.out.println("Server ready ...");
	}

}
