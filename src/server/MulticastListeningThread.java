package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MulticastListeningThread extends Thread {

	private GameManager gameManager;
	private InetAddress group = null;
	private MulticastSocket multicastSocket;
	private byte[] messageByte;
	
	public MulticastListeningThread(MulticastSocket multicastSocket, GameManager gameManager, InetAddress group) {
		this.multicastSocket = multicastSocket;
		this.messageByte = new byte[1000];
		this.gameManager = gameManager;
		this.group = group;
	}
	
	
	public void run() {
		try {
			this.multicastSocket.joinGroup(group);
			DatagramPacket messageIn = null;
			
			gameManager.setStatusGame(true);
			while(gameManager.getStatusGame()) {
				messageIn = new DatagramPacket(messageByte, messageByte.length);
				multicastSocket.receive(messageIn);
				
				String message = new String(messageIn.getData(), messageIn.getOffset(), messageIn.getLength());
				
				if (message.matches("JOIN \\[.+\\]")) {
					Pattern pattern = Pattern.compile("JOIN \\[(.+)\\]");
					Matcher matcher = pattern.matcher(message);
					matcher.find();
					
					String apelide = matcher.group(1);
					Peer peer = new Peer(messageIn.getAddress(), apelide);
					
					if(!gameManager.getPeers().contains(peer) && !peer.getApelido().equals(gameManager.getApelide())) {
						System.out.println(apelide + " entrou!");
						gameManager.getPeers().add(peer);
					}
					gameManager.setPrivateAddress(peer.getIp());
					gameManager.sendFormatedMessage(1, 2);
				}
				
				else if(message.matches("LEAVE \\[(.+)\\]")) {
					Pattern pattern = Pattern.compile("LEAVE \\[([a-zA-Z0-9]+)\\]");
					Matcher matcher = pattern.matcher(message);
					matcher.find();
					
					String apelide = matcher.group(1);
					System.out.println(apelide + " saiu!");
					Peer peer = new Peer(messageIn.getAddress(), apelide);
					gameManager.getPeers().remove(peer);
				}
				
				else {
					System.out.println(message);
					System.out.println("MULTI: Mensagem recebida em formato inapropriado. Erro de protocolo");
					String replyString ="MSG [" + gameManager.getApelide() + "] Mensagem nao processada. Erro de protocolo.";
					byte[] replyBytes = replyString.getBytes();
					DatagramPacket reply = new DatagramPacket(replyBytes, replyBytes.length, messageIn.getAddress(), messageIn.getPort());
					this.multicastSocket.send(reply);
				}
			
			}
			multicastSocket.leaveGroup(group);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
