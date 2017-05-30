package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
				this.udpSocket.receive(request);
				
				message = new String(request.getData(), request.getOffset(), request.getLength());
				
				if (message.matches("JOIN \\[.+\\]")) {
					Pattern pattern = Pattern.compile("JOIN \\[(.+)\\]");
					Matcher matcher = pattern.matcher(message);
					matcher.find();
					
					String apelide = matcher.group(1);
					Peer peer = new Peer(request.getAddress(), apelide);
					
					if(!gameManager.getPeers().contains(peer)) {
						System.out.println(apelide + " entrou!");
						gameManager.getPeers().add(peer);
						
						if(gameManager.getCurrentMatch().getPlayer1() == null) {
							gameManager.getCurrentMatch().setPlayer1(peer);
						}
						else if(gameManager.getCurrentMatch().getPlayer2() == null) {
							gameManager.getCurrentMatch().setPlayer2(peer);
							gameManager.getMatchRunning().start();
						}
					}
					gameManager.setPrivateAddress(peer.getIp());
					gameManager.sendFormatedMessage(1);
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
				
				else if(message.matches("GET PLAYERS \\[(.+)\\]")) {
					Pattern pattern = Pattern.compile("GET PLAYERS \\[(.+)\\]");
					Matcher matcher = pattern.matcher(message);
					matcher.find();
					
					String apelide = matcher.group(1);
					System.out.println(apelide + " pediu para ver os players. Respondendo... ");
					this.gameManager.setPrivateAddress(request.getAddress());
					this.gameManager.sendFormatedMessage(2);
				}
				
				else if(message.matches("PLAY \\[(.+)\\]")) {
					String namePlayers = this.gameManager.extractLocaleInformation("PLAY \\[(.+)\\]", message, 1);
					String[] namePlayersArray = namePlayers.split("(, )");
					
					int index = gameManager.getPeers().indexOf(new Peer(namePlayersArray[0]));
					Peer invitingPeer = gameManager.getPeers().get(index);
					
					if(gameManager.getPeers().contains(new Peer(namePlayersArray[1]))) {
						
						index = gameManager.getPeers().indexOf(new Peer(namePlayersArray[1]));
						Peer invitedPeer = gameManager.getPeers().get(index);
						
						Match match = new Match(invitingPeer, invitedPeer);
						
						match.setStatus("convite");
						
						gameManager.getOnlineMatches().add(match);
						gameManager.setPrivateAddress(invitedPeer.getIp());
						gameManager.sendFormatedMessage(3);
					} 
					
					else {
						gameManager.setPrivateAddress(invitingPeer.getIp());
						gameManager.sendFormatedMessage(4);
					}
					
				}
				
				else if(message.matches("INVITATION RESPONSE \\[(.+)\\]")) {
					String namePlayers = this.gameManager.extractLocaleInformation("INVITATION RESPONSE \\[(.+)\\]", message, 1);
					String[] namePlayersArray = namePlayers.split("(, )");
					
					int index = gameManager.getPeers().indexOf(new Peer(namePlayersArray[0]));
					Peer invitingPeer = gameManager.getPeers().get(index);
					
					index = gameManager.getPeers().indexOf(new Peer(namePlayersArray[1]));
					Peer invitedPeer = gameManager.getPeers().get(index);
					
					Match match = new Match(invitingPeer, invitedPeer);
					
					String response = namePlayersArray[2];
					
					if(response.matches("ACCEPTED")) {
						int indexMatch = gameManager.getOnlineMatches().indexOf(match);
						gameManager.getOnlineMatches().get(indexMatch).setStatus("accepted");
						
					}
					else if(response.matches("DENIED")) {
						
					}
				}
				
				
				
				else {
					System.out.println(message);
					System.out.println("MULTI: Mensagem recebida em formato inapropriado. Erro de protocolo");
					String replyString ="MSG [" + gameManager.getNickname() + "] Mensagem nao processada. Erro de protocolo.";
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
