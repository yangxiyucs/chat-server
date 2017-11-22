package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientStart {

	// TODO Auto-generated constructor stub
	public static void main(String[] args) throws Exception {
		Socket s;
		int id = (int) (Math.random());
		s = new Socket("localhost", 9999);
		//start a new thread 
		new Thread(new ClientThread(s, id)).start();
		PrintStream ps = new PrintStream(s.getOutputStream());
		String line = null;
		//read the input from keyboard
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//constantly print out the 
		while ((line = br.readLine()) != null) {
			if (line == "" || "".equals(line.trim())) {
				System.out.println("can not be null");
			} else {
				ps.println(line);
			}

		}
	}

}
