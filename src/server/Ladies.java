package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.LinkedList;

public class Ladies extends UnicastRemoteObject implements LadiesInterface  {
	
	int[][] board;
	MatchThread matchThread;
	LinkedList<Integer> piecesPlayer1;
	LinkedList<Integer> piecesPlayer2;
	
	public Ladies(MatchThread matchThread) throws RemoteException {
		super();
        System.out.println("Objeto remoto instanciado");
        this.board = new int[8][8];
        this.matchThread = matchThread;
        this.piecesPlayer1 = new LinkedList<Integer>();
        this.piecesPlayer2 = new LinkedList<Integer>();
        this.completBoard();
        this.completPieces();
        
	}

	private void completPieces() {
		int i;
		for(i=1 ; i<= 12 ; i++) {
			piecesPlayer1.add(i);
		}
		for(i=13 ; i<= 24 ; i++) {
			piecesPlayer2.add(i);
		}
	}

	@Override
	public int movimentaPeca(String nickname, int piece, int sourceX, int sourceY) throws RemoteException {
		int i = 0;
		if(matchThread.getOnTime().equals(nickname)) {
			System.out.println(nickname);
			i = 10;
		}
		
		else {
			System.out.println(nickname);
			i = 20;
		}
		return i;
	}
	
	@Override
	public LinkedList<Integer> getPiecesPlayer1() {
		return piecesPlayer1;
	}
	
	@Override
	public LinkedList<Integer> getPiecesPlayer2() {
		return piecesPlayer2;
	}

	private void completBoard() {
		
	}

}
