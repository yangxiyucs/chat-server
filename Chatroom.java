package Chat;

import java.util.ArrayList;

public class Chatroom {

	public int roomRef;
	public String name;
	public ArrayList<ClientThread> clients;

	public Chatroom(String a) {
		name = a;
		roomRef = addNum();
		clients = new ArrayList<ClientThread>();
	}

	private int addNum() {
		Server.ROOM_REF++;
		return Server.ROOM_REF;
	}

	public void addClient(ClientThread a) {

		clients.add(a);
	}
}
