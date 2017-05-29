package server;

public class Match {
	
	private Peer player1;
	private Peer player2;
	private String status;
	
	public Match(Peer player1, Peer player2) {
		this.setPlayer1(player1);
		this.setPlayer2(player2);
	}

	public Peer getPlayer1() {
		return player1;
	}

	public void setPlayer1(Peer player1) {
		this.player1 = player1;
	}

	public Peer getPlayer2() {
		return player2;
	}

	public void setPlayer2(Peer player2) {
		this.player2 = player2;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
