package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatManager {
	
	private String apelide;
	private int udpPort;
	private DatagramSocket udpSocket;
	private byte[] messageBytes;
	private InetAddress privateAddress = null;
	private SocketAddress privatePort;
	private DatagramPacket request;
	private client.UDPListeningThread udpThread;
	private LinkedList<Peer> peers = null;
	private boolean statusChat = false;
	
	public ChatManager(String apelide, int udpPort, int tcpPort) {
		this.apelide = apelide;
		this.udpPort = udpPort;
		this.peers = new LinkedList<>();
		
		try {
			udpSocket = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void initialize() {
		this.statusChat = true;
		udpThread = new UDPListeningThread(udpSocket, this);
		udpThread.start();
	}

	public void executeCommand(String command) {
		
		String formatedMessage = "";
		
		if(command.matches("conectar ladies"))
			formatedMessage = "JOIN [" + apelide + "]";
			
		 else if(command.matches("desconectar ladies"))
			formatedMessage = "LEAVE [" + apelide + "]";
		
		
		else if(command.matches("jogadores disponiveis"))
			formatedMessage = "GET PLAYERS [" + apelide + "]";
		
		else if(command.matches("jogar com (.)*"))
			formatedMessage = "LEAVE [" + apelide + "]";
		
		messageBytes = formatedMessage.getBytes();
		
		try {
			
			udpSocket.send(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String extractLocaleInformation(String regex, String message, int group) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(message);
		matcher.find();
		return matcher.group(group);
	}

	public LinkedList<Peer> getPeers() {
		return peers;
	}

	public void setPeers(LinkedList<Peer> peers) {
		this.peers = peers;
	}

	public boolean getStatusChat() {
		return statusChat;
	}

}
