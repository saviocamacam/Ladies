package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainServer {

	public static void main(String[] args) {
		
		int udpPort = 6799;
		
		GameManager gameManager = new GameManager("Ladies", udpPort);
		gameManager.initialize();
		
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
		
		try {
			LadiesInterface ladies = new Ladies(gameManager.getMatchRunning());
			
			Registry registry = LocateRegistry.getRegistry("localhost");
			registry.bind("LadiesService", ladies);
			
			gameManager.getMatchRunning().setLadies(ladies);
			gameManager.getMatchRunning().start();
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
		
	}

}
