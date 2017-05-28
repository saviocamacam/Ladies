/**
 * Define an interface for player Ladies
 * Author: Savio Camacam
 * Date: 18/05/2017
 */

package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LadiesInterface extends Remote {
	public int movimentaPeca (int origemX, int origemY) throws RemoteException;

}
