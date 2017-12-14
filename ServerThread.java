package Chat;
//server side listening

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class ServerThread implements Runnable {

	BufferedReader br;
	Socket s;
	int id;

	public ServerThread(Socket s) throws IOException {
		// TODO Auto-generated constructor stub
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));

	}

	// constantly receive all the message from clients when connect
	@Override
	public void run() {
		try {
			/*
			 * System.out.println(
			 * "JOINED_CHATROOM"+Thread.currentThread().getName()
			 * +"SERVER_IP"+s.getLocalAddress() +"PORT"+s.getLocalPort()
			 * +"ROOM_REF"+id +"JOIN_ID"+s.getInetAddress().getHostAddress());
			 */
			// TODO Auto-generated method stub
			String content = null;

			while ((content = readFromClient()) != null) {
				for (Socket s : Server.sockets) {
					PrintStream ps;
					// push all messages to all Sockets
					ps = new PrintStream(s.getOutputStream());

					ps.println(content);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// method to read message from clients
	private String readFromClient() {
		// TODO Auto-generated method stub
		try {

			String readCon = br.readLine();

			System.out.println("hello world");
			/*
			 * if (readCon.equalsIgnoreCase("HELLO")) {
			 * System.out.println("I am in hello"); }
			 */

			if (readCon.contains("KILL_SERVICE")) {
				this.s.close();
				Server.sockets.remove(s);
			} else if (readCon.contains("HELLO")) {
				return ("HELO text" + "StudentID:[your student ID]" + 17303731 + "IP"
						+ InetAddress.getLocalHost().toString() + "port" + "9999" + "\n");
			}

			else if (readCon.contains("JOIN_CHATROOM")) {

				String chatroomName = getData(readCon);
				String roomRef = "";
				int roomNum = CountChatRoom(chatroomName);
				if (roomNum > 0)
					roomRef = "" + roomNum;
				else {
					Chatroom name = new Chatroom(chatroomName);
					Server.chatrooms.put(name.roomRef, name);
					roomRef = "" + name.roomRef;
					roomNum = name.roomRef;
				}
				return "JOINED_CHATROOM: " + chatroomName + "\n" + "SERVER_IP: 1.1.1.1\n" + "PORT: 9999\n"
						+ "ROOM_REF: " + roomRef + "\n";

			} else if (readCon.contains("LEAVE_CHATROOM")) {
				int roomRef = Integer.parseInt(getData(readCon));
				return ("LEFT_CHATROOM:" + roomRef + "JOIN_ID:" + s.getInetAddress().getHostAddress());

			} else if (readCon.contains("DISCONNECT")) {
				removeFromAllChatrooms(getData(readCon));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Server.sockets.remove(s);
			e.printStackTrace();
		}
		return null;

	}

	public void removeFromAllChatrooms(String name) {

		int thisClientID = 0;
		Map<Integer, ClientThread> map = Server.clients;
		for (Entry<Integer, ClientThread> entry : map.entrySet()) {
			if (name.equalsIgnoreCase(entry.getValue().name)) {
				thisClientID = entry.getKey();
			}
		}
		ClientThread myClient = Server.clients.get(thisClientID);
		if (thisClientID != 0) {
			Map<Integer, Chatroom> map2 = Server.chatrooms;
			for (Chatroom entry2 : map2.values()) {
				for (int i = 0; i < entry2.clients.size(); i++) {
					if (entry2.clients.get(i).join_id == thisClientID) {
						String message = "CHAT: " + entry2.roomRef + "\n" + "CLIENT_NAME: " + myClient.name + "\n"
								+ "MESSAGE: " + myClient.name + " has just left the chatroom\n\n";
						notifyOtherClients(entry2.clients, myClient, message);
						entry2.clients.remove(i);
					}
				}
			}
		}

		Server.clients.remove(thisClientID);
	}

	public void removeClientFromChatroom(Chatroom a, Client b) {

		for (int i = 0; i < a.clients.size(); i++) {
			if (a.clients.get(i).join_id == b.joinID) {
				a.clients.remove(i);
			}
		}
	}

	public int CountChatRoom(String a) {
		int res = 0;
		Map<Integer, Chatroom> map = Server.chatrooms;
		for (Map.Entry<Integer, Chatroom> entry : map.entrySet()) {
			if (a.equalsIgnoreCase(entry.getValue().name)) {
				res = (int) entry.getKey();
			}
		}
		return res;
	}

	public void notifyOtherClients(ArrayList<ClientThread> clients, ClientThread myClient, String message) {

		for (int i = 0; i < clients.size(); i++) {
			try {
				PrintStream out = new PrintStream(clients.get(i).s.getOutputStream());
				out.write(message.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public int clientRepeat(String a) {
		int res = 0;
		Map<Integer, ClientThread> map = (Map<Integer, ClientThread>) Server.clients;
		for (Entry<Integer, ClientThread> entry : map.entrySet()) {
			if (a.equalsIgnoreCase(entry.getValue().name))
				res = (int) entry.getKey();
		}
		return res;
	}

	public boolean clientIn(Chatroom a, ClientThread b) {

		for (int i = 0; i < a.clients.size(); i++) {
			if (a.clients.get(i) != null && a.clients.get(i).join_id == b.join_id) {
				return true;
			}
		}
		return false;
	}

	public String getData(String a) {

		char[] b = a.toCharArray();
		boolean active = false;
		String res = "";
		for (int i = 0; i < a.length(); i++) {

			if (b[i] == '\\') {
				break;
			}
			if (active && Character.isLetterOrDigit(b[i])) {
				res += b[i];
			}
			if (b[i] == ' ') {
				active = true;
			}
		}
		return res;
	}
}