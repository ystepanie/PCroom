package client;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import javax.security.auth.login.LoginContext;
import javax.swing.JPanel;

import com.java.client.login;

public class reservateThread extends Thread{
	
	private Socket socket;
	ArrayList<JPanel> comList;
	MouseListener[] mouseListener = new MouseListener[15];
	
	public reservateThread(ArrayList<JPanel> comList) {
		this.comList = comList;
		for(int i=0;i<15;i++) {
			int click = i;
			mouseListener[i] = new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					new login((click+1));
				}
			};
			comList.get(i).addMouseListener(mouseListener[i]);
		}
	}
	
	@Override
	public void run() {
		super.run();
		System.out.println("스레드 실행");
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
			BufferedReader tmpbuf = new BufferedReader(inputStreamReader);
			String receiveString;

			System.out.println("스레드 실행");
			while(true) {
				receiveString = tmpbuf.readLine();
				int seatNum;
				if(receiveString != null) {
				String[] strings = receiveString.split("//");
				if(strings.length == 1) {
					String[] splitReservate = receiveString.split("/");
					for(int i=0; i<15; i++) {
						comList.get(i).setBackground(Color.LIGHT_GRAY);
						comList.get(i).addMouseListener(mouseListener[i]);
					}if(!receiveString.equals("취소")) {
					for(int i=0; i<splitReservate.length; i++) {
						comList.get((Integer.parseInt(splitReservate[i])-1)).setBackground(Color.black);
						comList.get((Integer.parseInt(splitReservate[i])-1)).removeMouseListener(mouseListener[(Integer.parseInt(splitReservate[i])-1)]);
					}}
				}}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
}
