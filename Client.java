package Chat;

import java.net.Socket;

public class Client {

	public int joinID;
	public String name;
	public Socket socket;

	public Client() {
		joinID = assignNumber();
		name = "";
	}

	private int assignNumber() {
		Server.JOIN_ID++;
		return Server.JOIN_ID;
	}
}
