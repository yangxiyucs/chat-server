package Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable {

	Socket s;
	BufferedReader br;
	int id;

	public ClientThread(Socket s,int id) throws IOException {
		
		this.s = s;
		this.id = id;
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));

	}

	@Override
	//start the thread of client
	public void run() {
		
		String content;
		try {
			//constantly get message from server and print 
			while ((content = br.readLine()) != null) {
				System.out.println(content);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
