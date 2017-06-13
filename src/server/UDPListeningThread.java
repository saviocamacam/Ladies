package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UDPListeningThread extends Thread {

	private DatagramSocket udpSocket;
	private GameManager gameManager;
	private byte[] buffer;
	private DatagramPacket request;

	public UDPListeningThread(DatagramSocket udpSocket, GameManager gameManager) {
		this.udpSocket = udpSocket;
		this.setChatManager(gameManager);
		buffer = new byte[1000];
		request = new DatagramPacket(buffer, buffer.length);
	}
	
	public void run() {
		String message = null;
			
		try {
			while(gameManager.getStatusGame()) {
				System.out.print("Waiting...");
				this.udpSocket.receive(request);
				System.out.println("Message received");
				
				message = new String(request.getData(), request.getOffset(), request.getLength());
				
				if (message.matches("JOIN \\[.+\\]")) {
					Pattern pattern = Pattern.compile("JOIN \\[(.+)\\]");
					Matcher matcher = pattern.matcher(message);
					matcher.find();
					
					String nickname = matcher.group(1);
					gameManager.setPrivateAddress(request.getAddress());
					
					if(gameManager.getPeers().size() <= 2) {
						Peer peer = new Peer(request.getAddress(), nickname);
						
						if(!gameManager.getPeers().contains(peer)) {
							System.out.println(nickname + " entrou!");
							gameManager.getPeers().add(peer);
							
							if(gameManager.getPeers().size() == 2) {
								gameManager.startMatch();
							}
							gameManager.sendFormatedMessage(1);
						}
						else
							gameManager.sendFormatedMessage(3);
					}
					else
						gameManager.sendFormatedMessage(3);
				}
				
				else if(message.matches("LEAVE \\[(.+)\\]")) {
					Pattern pattern = Pattern.compile("LEAVE \\[([a-zA-Z0-9]+)\\]");
					Matcher matcher = pattern.matcher(message);
					matcher.find();
					
					String apelide = matcher.group(1);
					System.out.println(apelide + " saiu!");
					Peer peer = new Peer(request.getAddress(), apelide);
					gameManager.getPeers().remove(peer);
				}
				
				else if(message.matches("MSG \\[(.+)\\] (.*)")) {
					Pattern pattern = Pattern.compile("MSG \\[(.+)\\] (.*)");
					Matcher matcher = pattern.matcher(message);
					matcher.find();
					
					String nickname = matcher.group(1);
					String message2 = matcher.group(2);
					
					System.out.println(nickname + " diz: " + message2);
				}
				
				else {
					System.out.println(message);
					System.out.println("MULTI: Message received in a improper format. Protocol Error!");
					String replyString ="MSG [" + gameManager.getNickname() + "] Unprocessed message. Protocol Error.";
					byte[] replyBytes = replyString.getBytes();
					DatagramPacket reply = new DatagramPacket(replyBytes, replyBytes.length, request.getAddress(), request.getPort());
					this.udpSocket.send(reply);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setChatManager(GameManager gameManager) {
		this.gameManager = gameManager;
	}

}
