package server;

public class MainServer {

	public static void main(String[] args) {
		
		int multicastPort = 6789;
		int udpPort = 6799;
		
		GameManager gameManager = new GameManager("Ladies", multicastPort, udpPort);
		gameManager.initialize();
		
	}

}
