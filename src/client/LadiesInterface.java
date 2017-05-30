package client;

import java.rmi.RemoteException;

public interface LadiesInterface {
	int[][] board = {};
	
	public int movimentaPeca (int origemX, int origemY) throws RemoteException;
}
