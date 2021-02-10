package com.java.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.reservateThread;
import client.sendThread;

public class seatSelection extends JFrame{

	Socket socket;
	
	seatSelection() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c= getContentPane();
        c.setLayout(new BorderLayout());
        
        c.add(new JLabel("좌석을 선택해주세요."), BorderLayout.NORTH);
        JPanel center = new JPanel(null);
        center.setLayout(new GridLayout(5, 3, 100, 10));
        c.add(center, BorderLayout.CENTER);
        JPanel[] computer = new JPanel[15];
        ArrayList<JPanel> comList = new ArrayList<JPanel>();
        for(int i = 0; i < 15; i++) {
        	center.add(computer[i] = new JPanel());
        	computer[i].setBackground(Color.LIGHT_GRAY);
        	computer[i].add(new JLabel("NO."+Integer.toString(i+1)));
        	comList.add(computer[i]);
        }
        
        try {
			socket = new Socket("192.168.0.3", 3333);
			System.out.println("소켓 생성");
			sendThread send_thread = new sendThread();
			reservateThread res_thread = new reservateThread(comList);
			System.out.println("스레드 생성");
			send_thread.setSocket(socket);
			send_thread.setFunction(-2);
			send_thread.setSeatNum(0);
			res_thread.setSocket(socket);
			send_thread.start();
			res_thread.start();
			System.out.println("스레드 시작");
			} catch(IOException exception) {
				exception.printStackTrace();
			}
        
        setSize(550, 350);
        setVisible(true);
	}
	public static void main(String[] args) {
		new seatSelection();
	}

}
