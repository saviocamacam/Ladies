package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatManager {
	
	private String nickname;
	public String getApelide() {
		return nickname;
	}

	public void setApelide(String nickname) {
		this.nickname = nickname;
	}

	private int udpPort;
	private DatagramSocket udpSocket;
	private byte[] messageBytes;
	private InetAddress privateAddress = null;
	private DatagramPacket request;
	private client.UDPListeningThread udpThread;
	private LinkedList<Peer> peers = null;
	private boolean statusChat = false;
	
	public ChatManager(String apelide, int udpPort, int tcpPort, String privateAddress) {
		this.nickname = apelide;
		this.udpPort = udpPort;
		this.peers = new LinkedList<>();
		
		try {
			this.privateAddress = InetAddress.getByName(privateAddress);
			udpSocket = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
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
			formatedMessage = "JOIN [" + nickname + "]";
			
		 else if(command.matches("desconectar ladies"))
			formatedMessage = "LEAVE [" + nickname + "]";
		
		else if(command.matches("jogadores disponiveis"))
			formatedMessage = "GET PLAYERS [" + nickname + "]";
		
		else if(command.matches("jogar com (.*)")) {
			Pattern pattern = Pattern.compile("jogar com (.*)");
			Matcher matcher = pattern.matcher(command);
			matcher.find();
			String nickname = matcher.group(1);
			String player = "";
			if(peers.contains(new Peer(nickname)))
				player = nickname;
			else{
				System.out.print(nickname + "não está na sua lista");
				return;
			}
			
			formatedMessage = "PLAY [" + nickname +", " + player + "]";
		}
		
		messageBytes = formatedMessage.getBytes();
		
		try {
			request = new DatagramPacket(messageBytes, messageBytes.length, privateAddress, udpPort);
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
