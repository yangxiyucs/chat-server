package Chat;
//server side listening

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ServerThread implements Runnable {

	BufferedReader br;
	Socket s;
	int id;

<<<<<<< HEAD
	public ServerThread(Socket s, int id) throws IOException {
=======
	public ServerThread(Socket s) throws IOException {
>>>>>>> 862cbe00fc49835242ffa5d0bee9ab38a5be9c05
		// TODO Auto-generated constructor stub
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		this.id = id;
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
<<<<<<< HEAD

		} catch (IOException e) {
=======
		} catch (Exception e) {
>>>>>>> 862cbe00fc49835242ffa5d0bee9ab38a5be9c05
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// method to read message from clients
<<<<<<< HEAD
	private String readFromClient() {
		// TODO Auto-generated method stub
		try {

			String readCon = br.readLine();

			System.out.println("hello world");
			/*
			 * if (readCon.equalsIgnoreCase("HELLO")) {
			 * System.out.println("I am in hello"); }
			 */

			switch (readCon) {
			case "KILL_SERVICE":
				this.s.close();
				Server.sockets.remove(s);
				break;
			case "HELLO":
				 return ("HELO text" + "StudentID:[your student ID]" + 17303731 + "\n");
				

			case "LEAVE_CHATROOM":
				return ("LEFT_CHATROOM:" + Thread.currentThread().getName() + "JOIN_ID:"
						+ s.getInetAddress().getHostAddress());
				
		
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			Server.sockets.remove(s);
			e.printStackTrace();
		}
=======
	private String readFromClient() throws Exception {
		// TODO Auto-generated method stub

		String readCon = br.readLine();
		System.out.println(readCon);

		if (readCon.equalsIgnoreCase("HELLO")) {
			System.out.println("I am in hello");
		} else if (readCon.equalsIgnoreCase("KILL_SERVICE")) {
			this.s.close();
			Server.sockets.remove(s);
			System.out.println("closed socket");
		} else if (readCon.equalsIgnoreCase("HELLO text")) {

			System.out.println("HELO text" + "\"IP:[ip address]" + s.getInetAddress().getHostAddress() + "\n"
					+ "Port:[port number]" + ((InetSocketAddress) s.getRemoteSocketAddress()).getPort() + "\n"
					+ "StudentID:[your student ID]" + 12345 + "\n");
			/*
			 * }
			 * 
			 * 
			 * case "LEAVE_CHATROOM:" + Thread.currentThread().getName() +
			 * "JOIN_ID:"+ s.getInetAddress().getHostAddress() :
			 * System.out.println( "LEFT_CHATROOM:"
			 * +Thread.currentThread().getName() +
			 * "JOIN_ID:"+s.getInetAddress().getHostAddress());
			 */

		}

>>>>>>> 862cbe00fc49835242ffa5d0bee9ab38a5be9c05
		return null;

	}
}