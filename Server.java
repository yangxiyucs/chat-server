package Chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {

	// 定义保存所有Socket的ArrayList
	// public static ArrayList<ArrayList<Socket>> socketList = new
	// ArrayList<ArrayList<Socket>>();
	public static ArrayList<Socket> sockets = new ArrayList<Socket>();
    public static HashMap<Integer,Chatroom> chatrooms = new HashMap<Integer,Chatroom>();
    public static Map<Integer, ClientThread> clients = new HashMap<Integer,ClientThread>();
    public static int ROOM_REF=1;
    public static int JOIN_ID =1;
	public static void main(String[] args) {

		try {
			ServerSocket ss = new ServerSocket(9999);
			/// Rooms.add("Room1");
			System.out.println("New Room has been created");

			System.out.println("Server is started");
			while (true) {
				// 此代码会阻塞，将一直等待别人的连接
				Socket s = ss.accept();
				sockets.add(s);
				// 每当客户端连接后启动一条ServerThread线程为该客户端服务
				//int id = (int) (Math.random());
				new Thread(new ServerThread(s)).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
