package server;

import java.io.IOException;
import java.net.DatagramPacket;
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
	private boolean statusGame;
	private String apelide;
	private LinkedList<Peer> peers = null;
	private InetAddress privateAddress = null;
	private byte[] messageBytes;
	private DatagramPacket request;
	
	
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


public void sendFormatedMessage(int typeMessage) {
		
		String formatedMessage = "";
		
		switch(typeMessage) {
			case 1: 
				formatedMessage = "JOINACK [" + apelide + "]";
				break;
			case 2: 
				formatedMessage = "PLAYERS [";
				int i;
				LinkedList<Peer> freePlayers  = getFreePlayers();
				for(i = 0; i < freePlayers.size()-1 ; i++) {
					formatedMessage = formatedMessage.concat(freePlayers.get(i).getApelido() + ", ");
				}
				formatedMessage = formatedMessage.concat(freePlayers.get(i).getApelido());
				formatedMessage = formatedMessage.concat("]");
				break;
				
			default: formatedMessage = "Vish, deu merda aqui. Foi mal, aqui e o " + apelide;
		}
		
		messageBytes = formatedMessage.getBytes();
		
		try { 
			request = new DatagramPacket(messageBytes, messageBytes.length, privateAddress, udpPort);
			udpSocket.send(request);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private LinkedList<Peer> getFreePlayers() {
		LinkedList<Peer> listFree = new LinkedList<>();
		for(Peer p : peers) {
			if(p.getStatus()) {
				listFree.add(p);
			}
		}
		return listFree;
	}


	public void setPrivateAddress(InetAddress ip) {
		this.privateAddress = ip;
	}

}
