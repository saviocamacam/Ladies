package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;

public class Ladies extends UnicastRemoteObject implements LadiesInterface  {
	
	int[][] board;
	
	public Ladies() throws RemoteException {
		super();
        System.out.println("Objeto remoto instanciado");
        this.board = new int[8][8];
	}

	@Override
	public int movimentaPeca(int piece, int sourceX, int sourceY) throws RemoteException {
		
		return 0;
	}

	@Override
	public HashSet<Integer> getPiecesPlayer2() {
		
		return null;
	}

	@Override
	public HashSet<Integer> getPiecesPlayer1() {
		
		return null;
	}

}
