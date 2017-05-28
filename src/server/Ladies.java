package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Ladies extends UnicastRemoteObject implements LadiesInterface  {

	public Ladies() throws RemoteException {
		super();
        System.out.println("Objeto remoto instanciado");
	}

	@Override
	public int movimentaPeca(int origemX, int origemY) throws RemoteException {
		
		return 0;
	}

}
