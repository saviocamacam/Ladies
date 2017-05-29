package client;

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
	
	@Override
	public boolean equals(Object o) {
		return this.apelide.equals(((Peer) o).getApelide());
	}
}
