package server;

import java.net.InetAddress;

public class Peer {

	private String nickname = null;
	private InetAddress ip = null;
	private boolean status;
	
	public Peer(InetAddress inetAddress, String apelido) {
		this.status = true;
		this.setNickname(apelido);
		this.setIp(inetAddress);
	}

	public Peer(String nickname) {
		this.nickname = nickname;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress inetAddress) {
		this.ip = inetAddress;
	}
	
	@Override
	public boolean equals(Object o) {
		return this.nickname.equals(((Peer) o).getNickname());
	}

	public boolean getStatus() {
		return status;
	}

}
