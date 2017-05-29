package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPListeningThread extends Thread {

	private DatagramSocket udpSocket;
	private ChatManager chatManager;
	private byte[] buffer;
	private DatagramPacket request;

	public UDPListeningThread(DatagramSocket udpSocket, ChatManager chatManager) {
		this.udpSocket = udpSocket;
		this.setChatManager(chatManager);
		buffer = new byte[1000];
		request = new DatagramPacket(buffer, buffer.length);
	}
	
	public void run() {
		String message = null;
			
		try {
			while(chatManager.getStatusChat()) {
				this.udpSocket.receive(request);
				message = new String(request.getData(), request.getOffset(), request.getLength());
				
				if (message.matches("JOINACK \\[(.+)\\]")) {
					
				}
				
				else if(message.matches("PLAYERS \\[(.+)\\] (.*)")) {
					String namePlayers = this.chatManager.extractLocaleInformation("FILES \\[(.+)\\]", message, 1);
					String[] namePlayersArray = namePlayers.split("(, )");
					int i;
					for(i = 0 ; i < namePlayersArray.length ; i++) {
						this.chatManager.getPeers().add(new Peer(namePlayersArray[i]));
						System.out.println("Player " + i + ": " + namePlayersArray[i]);
					}
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void setChatManager(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

}
