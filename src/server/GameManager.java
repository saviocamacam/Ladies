package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	private LinkedList<Match> onlineMatches;
	private MatchThread matchRunning;
	
	public GameManager(String nickname, int udpPort) {
		this.udpPort = udpPort;
		this.nickname = nickname;
		this.peers = new LinkedList<>();
		this.setOnlineMatches(new LinkedList<>());
		
		try {
			this.udpSocket = new DatagramSocket(udpPort);
			
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}


	public void initialize() {
		System.out.println("Abrindo Ladies");
		this.matchRunning = new MatchThread(this);
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
				formatedMessage = "MATCH [" + getMatchRunning().getOponent() + "]";
				break;
			case 3:
				formatedMessage = "DENIED [" + nickname + "]";
				break;
		}
		
		messageBytes = formatedMessage.getBytes();
		
		try { 
			request = new DatagramPacket(messageBytes, messageBytes.length, privateAddress, udpPort);
			udpSocket.send(request);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public LinkedList<Peer> getFreePlayers() {
		LinkedList<Peer> listFree = new LinkedList<>();
		for(Peer p : peers) {
			if(p.getStatus()) {
				listFree.add(p);
			}
		}
		return listFree;
	}
	
	
	public String extractLocaleInformation(String regex, String message, int group) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(message);
		matcher.find();
		return matcher.group(group);
	}


	public void setStatusGame(boolean b) {
		this.statusGame = b;
	}


	public int getUdpPort() {
		return udpPort;
	}


	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
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


	public LinkedList<Match> getOnlineMatches() {
		return onlineMatches;
	}


	public void setOnlineMatches(LinkedList<Match> onlineMatches) {
		this.onlineMatches = onlineMatches;
	}


	public DatagramSocket getUdpSocket() {
		return udpSocket;
	}


	public MatchThread getMatchRunning() {
		return matchRunning;
	}


	public void setMatchRunning(MatchThread matchRunning) {
		this.matchRunning = matchRunning;
	}


	public void startMatch() {
		Match match = new Match(peers.get(0), peers.get(1));
		this.matchRunning.setCurrentMatch(match);
		this.matchRunning.start();
	}

}
