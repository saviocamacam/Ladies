package client;

import java.net.InetAddress;

public class Peer {
	private String apelide = null;
	
	public Peer(String apelido) {
		this.setApelide(apelido);
	}

	public String getApelide() {
		return apelide;
	}

	public void setApelide(String apelide) {
		this.apelide = apelide;
	}
}
