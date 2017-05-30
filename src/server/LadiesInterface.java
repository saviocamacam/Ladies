/**
 * Define an interface for player Ladies
 * Author: Savio Camacam
 * Date: 18/05/2017
 */

package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface LadiesInterface extends Remote {
	
	public int movimentaPeca (String nickname, int piece, int sourceY, int sourceX) throws RemoteException;
	
	public LinkedList<Integer> getPiecesPlayer1();
	
	public LinkedList<Integer> getPiecesPlayer2();

}
