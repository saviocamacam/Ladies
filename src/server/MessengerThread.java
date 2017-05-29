package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.LinkedList;

public class MessengerThread extends Thread{
	
	private InetAddress privateAddress = null;
	private int typeMessage;
	private GameManager gameManager;
	private byte[] messageBytes;
	private DatagramPacket request;
	
	public MessengerThread(GameManager gameManager, InetAddress privateAddress) {
		this.gameManager = gameManager;
		this.privateAddress = privateAddress;
	}
	
	public void run() {
		
		String formatedMessage = "";
		
		switch(typeMessage) {
			case 1: 
				formatedMessage = "JOINACK [" + gameManager.getNickname() + "]";
				break;
			case 2: 
				formatedMessage = "PLAYERS [";
				int i;
				LinkedList<Peer> freePlayers  = gameManager.getFreePlayers();
				for(i = 0; i < freePlayers.size()-1 ; i++) {
					formatedMessage = formatedMessage.concat(freePlayers.get(i).getNickname() + ", ");
				}
				formatedMessage = formatedMessage.concat(freePlayers.get(i).getNickname());
				formatedMessage = formatedMessage.concat("]");
				break;
			case 3:
				formatedMessage = "INVITE RECEIVED[]";
				break;
			case 4:
				formatedMessage = "MATCH FAILED[]";
				break;
				
			default: formatedMessage = "Vish, deu merda aqui. Foi mal, aqui é o " + gameManager.getNickname();
		}
		
		messageBytes = formatedMessage.getBytes();
		
		try { 
			request = new DatagramPacket(messageBytes, messageBytes.length, privateAddress, gameManager.getUdpPort());
			gameManager.getUdpSocket().send(request);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
