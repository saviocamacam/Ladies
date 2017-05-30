package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatManager {
	
	private String nickname;
	public String getNickname() {
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
	private LadiesInterface ladies;
	private InetAddress serverAddress;
	
	public ChatManager(String apelide, int udpPort, int tcpPort, String privateAddress) {
		this.nickname = apelide;
		this.udpPort = udpPort;
		this.peers = new LinkedList<>();
		
		try {
			this.serverAddress = InetAddress.getByName(privateAddress);
			this.privateAddress = serverAddress;
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
		
		if(command.matches("connect ladies"))
			formatedMessage = "JOIN [" + nickname + "]";
			
		 else if(command.matches("leave ladies"))
			formatedMessage = "LEAVE [" + nickname + "]";
		
		else if(command.matches("move piece ([0-9][0-9], ?[0-9], ?[0-9])")) {
			
			if(ladies != null) {
				String commandExtracted = this.extractLocaleInformation("movimentar peça ([0-9][0-9], ?[0-9], ?[0-9])", command, 1);
				String[] moveCommands = commandExtracted.split("(, )");
				int piece = Integer.valueOf(moveCommands[0]);
				int sourceX = Integer.valueOf(moveCommands[1]);
				int sourceY = Integer.valueOf(moveCommands[0]);
				try {
					this.ladies.movimentaPeca(this.nickname, piece, sourceX, sourceY);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			
			else {
				System.out.println("You're not in a match currently! Try again later.");
			}
			
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

	public void matchInitialized(String nickname) {
		try {
            System.out.println ("Client started ...");

            if (System.getSecurityManager() == null) {
               System.setSecurityManager(new SecurityManager());
            }

            Registry registry = LocateRegistry.getRegistry("192.168.1.114");
            ladies = (LadiesInterface)registry.lookup("LadiesService");
            
        } catch (Exception e) {
           System.out.println(e);
        }
	}

}
