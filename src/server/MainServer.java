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
			LadiesInterface ladies = new Ladies();
			
			Registry registry = LocateRegistry.getRegistry("localhost");
			registry.bind("LadiesService", ladies);

			System.out.println("Server ready ...");

			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
		
	}

}
