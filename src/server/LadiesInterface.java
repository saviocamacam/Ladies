/**
 * Define an interface for player Ladies
 * Author: Savio Camacam
 * Date: 18/05/2017
 */

package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;

public interface LadiesInterface extends Remote {
	int[][] board = {};
	
	public int movimentaPeca (int piece, int sourceY, int sourceX) throws RemoteException;
	
	public HashSet<Integer> getPiecesPlayer1();
	
	public HashSet<Integer> getPiecesPlayer2();

}
