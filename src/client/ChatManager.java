package client;

import java.net.DatagramSocket;
import java.net.SocketException;

public class ChatManager {
	
	private String apelide;
	private int udpPort;
	private DatagramSocket udpSocket;
	
	public ChatManager(String apelide, int udpPort, int tcpPort) {
		this.apelide = apelide;
		this.udpPort = udpPort;
		
		try {
			udpSocket = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void initialize() {
		
	}

	public void executeCommand(String command) {
		
		String formatedMessage = "";
		
		if(command.matches("conectar ladies")) {
			formatedMessage = "JOIN [" + apelide + "]";
			
		} else if(command.matches("desconectar ladies")) {
			formatedMessage = "LEAVE [" + apelide + "]";
		}
		
	}

}
