package server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;

public class GameManager {
	
	private InetAddress multicastAddress;
	private int multicastPort;
	private DatagramSocket udpSocket;
	private MulticastSocket multicastSocket;
	private int udpPort;
	private server.UDPListeningThread udpThread;
	private server.MulticastListeningThread multicastThread;
	private boolean statusGame;
	private String apelide;
	private LinkedList<Peer> peers = null;
	private InetAddress privateAddress = null;
	
	
	public GameManager(String apelide, int multicastPort, int udpPort) {
		this.multicastPort = multicastPort;
		this.udpPort = udpPort;
		this.apelide = apelide;
		
		try {
			this.multicastAddress = InetAddress.getByName("225.1.2.3");
			this.udpSocket = new DatagramSocket(udpPort);
			multicastSocket = new MulticastSocket(multicastPort);
			multicastSocket.setLoopbackMode(false);
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void initialize() {
		udpThread = new UDPListeningThread(udpSocket, this);
		udpThread.start();
		
		multicastThread = new MulticastListeningThread(multicastSocket, this, multicastAddress);
		multicastThread.start();
	}


	public void setStatusGame(boolean b) {
		this.statusGame = b;
	}


	public boolean getStatusGame() {
		return this.statusGame;
	}


	public LinkedList<Peer> getPeers() {
		return peers;
	}


	public String getApelide() {
		return apelide;
	}


	public void setApelide(String apelide) {
		this.apelide = apelide;
	}


	public void sendFormatedMessage(int i, int j) {
		
	}


	public void setPrivateAddress(InetAddress ip) {
		this.privateAddress = ip;
	}

}
