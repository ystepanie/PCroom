package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class sendThread extends Thread{
	
	public final int login = 0;
	public final int message = 1;
	public final int food = 2;
	private Socket socket;
	private int function;
	private int seatNum;
	private String userID;
	private String content = null;
	
	@Override
	public void run() {
		super.run();
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter sendWriter = new PrintWriter(socket.getOutputStream());
			String sendString;
			System.out.println(function+"//"+seatNum+"//"+userID+"//"+ content);
			sendWriter.println(function+"//"+seatNum+"//"+userID+"//"+ content);
			sendWriter.flush();
			while(true) {
				sendString = tmpbuf.readLine();
				
				if (sendString.equals("exit")){
					break;
				}
				sendWriter.println(sendString);
				sendWriter.flush();
			}
			sendWriter.close();
			tmpbuf.close();
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public void setFunction(int function) {
		this.function = function;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
}
