package server;

public class MainServer {

	public static void main(String[] args) {
		
		int udpPort = 6799;
		
		GameManager gameManager = new GameManager("Ladies", udpPort);
		gameManager.initialize();
		
	}

}
