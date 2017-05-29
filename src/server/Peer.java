package server;

import java.net.InetAddress;

public class Peer {

	private String apelide = null;
	private InetAddress ip = null;
	private boolean status;
	
	public Peer(InetAddress inetAddress, String apelido) {
		this.status = true;
		this.setApelido(apelido);
		this.setIp(inetAddress);
	}

	public String getApelido() {
		return apelide;
	}

	public void setApelido(String apelido) {
		this.apelide = apelido;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress inetAddress) {
		this.ip = inetAddress;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.apelide.equals(((Peer) o).getApelido());
	}

	public boolean getStatus() {
		return status;
	}

}