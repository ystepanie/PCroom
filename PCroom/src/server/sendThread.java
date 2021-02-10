package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class sendThread extends Thread{
	
	private Socket socket;
	
	@Override
	public void run() {
		super.run();
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter sendWriter = new PrintWriter(socket.getOutputStream());
			String sendString;
			
			while(true) {
				sendString = tmpbuf.readLine();
				
				sendWriter.println(sendString);
				sendWriter.flush();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
