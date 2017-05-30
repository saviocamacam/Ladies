package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				
				else if(message.matches("MATCH \\[(.+)\\]")) {
					Pattern pattern = Pattern.compile("MATCH \\[(.+)\\]");
					Matcher matcher = pattern.matcher(message);
					matcher.find();
					
					String nickname = matcher.group(1);
					chatManager.matchInitialized(nickname);
				}
				
				else if(message.matches("DENIED \\[(.+)\\]")) {
					Pattern pattern = Pattern.compile("DENIED \\[(.+)\\]");
					Matcher matcher = pattern.matcher(message);
					matcher.find();
					
					String server = matcher.group(1);
					System.out.println(server + " denied your request!");
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
					String replyString ="MSG [" + chatManager.getNickname() + "] Unprocessed message. Protocol Error.";
					byte[] replyBytes = replyString.getBytes();
					DatagramPacket reply = new DatagramPacket(replyBytes, replyBytes.length, request.getAddress(), request.getPort());
					this.udpSocket.send(reply);
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
