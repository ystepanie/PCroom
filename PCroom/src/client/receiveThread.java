package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JTextArea;

public class receiveThread extends Thread{
	
	private Socket socket;
	JTextArea messageView;
	String userID;
	
	public receiveThread(JTextArea messageView, String userID) {
		this.messageView = messageView;
		this.userID = userID;
	}
	@Override
	public void run() {
		super.run();
		System.out.println("receiveThread角青吝");
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
			BufferedReader tmpbuf = new BufferedReader(inputStreamReader);
			
			//BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String receiveString;
			
			while(true) {
				receiveString = tmpbuf.readLine();
				String[] strings = receiveString.split("//");
				String strID = strings[0];
				if(strID.equals(userID)) {
					System.out.println("包府磊 : " + strings[1]);
					messageView.append("包府磊 : " + strings[1]+"\n");
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
