/**
 * Define an interface for player Ladies
 * Author: Savio Camacam
 * Date: 18/05/2017
 */

package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LadiesInterface  extends Remote{
	int[][] board = {};
	
	public int movimentaPeca (int piece, int sourceY, int sourceX) throws RemoteException;
	
}
