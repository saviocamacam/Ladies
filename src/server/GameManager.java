package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;

public class GameManager {
	
	private DatagramSocket udpSocket;
	private int udpPort;
	private server.UDPListeningThread udpThread;
	private boolean statusGame;
	private String nickname;
	private LinkedList<Peer> peers = null;
	private InetAddress privateAddress = null;
	private byte[] messageBytes;
	private DatagramPacket request;
	
	
	public GameManager(String nickname, int udpPort) {
		this.udpPort = udpPort;
		this.nickname = nickname;
		this.peers = new LinkedList<>();
		
		try {
			this.udpSocket = new DatagramSocket(udpPort);
			
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}


	public void initialize() {
		System.out.println("Abrindo Ladies");
		this.statusGame = true;
		udpThread = new UDPListeningThread(udpSocket, this);
		udpThread.start();
	}
	
	public void sendFormatedMessage(int typeMessage) {
		
		String formatedMessage = "";
		
		switch(typeMessage) {
			case 1: 
				formatedMessage = "JOINACK [" + nickname + "]";
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
				
			default: formatedMessage = "Vish, deu merda aqui. Foi mal, aqui e o " + nickname;
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


	public void setStatusGame(boolean b) {
		this.statusGame = b;
	}


	public boolean getStatusGame() {
		return this.statusGame;
	}


	public LinkedList<Peer> getPeers() {
		return peers;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public void setPrivateAddress(InetAddress ip) {
		this.privateAddress = ip;
	}

}
