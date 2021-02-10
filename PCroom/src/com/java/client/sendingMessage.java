package com.java.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.receiveThread;
import client.sendThread;

public class sendingMessage extends JFrame{

	private Socket socket;
    private BufferedWriter bw;
    OutputStream os = null;
    OutputStreamWriter osw =null;

	public sendingMessage(int iSeat, String userID, Socket s_socket) {
		this.setSocket(s_socket);
		setTitle(userID+"님의 메세지");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c= getContentPane();
        c.setLayout(new BorderLayout());
        
        JTextArea messageView = new JTextArea(17, 22);
        JScrollPane scrollPane = new JScrollPane(messageView);
        messageView.setEditable(false);
        JPanel panCenter = new JPanel();
        panCenter.add(scrollPane);
        panCenter.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));
        c.add(panCenter, BorderLayout.CENTER);
        JTextField txtMessage = new JTextField(17);
        JButton btnSending = new JButton("전송");
        JPanel panSouth = new JPanel();
        panSouth.add(txtMessage);
        panSouth.add(btnSending);
        c.add(panSouth, BorderLayout.SOUTH);
        
        try{
            //파일 객체 생성
            File file = new File("chat"+userID+".txt");
             //입력 스트림 생성
            if(file.exists() == true) {
             FileReader file_reader = new FileReader(file);
             int cur = 0;
             String strMessage = null;
             while((cur = file_reader.read()) != -1){
                System.out.print((char)cur);
                strMessage += (char)cur;
             }
             messageView.append(strMessage);
             file_reader.close();
             receiveThread rec_thread = new receiveThread(messageView, userID);
             rec_thread.setSocket(socket);
             rec_thread.start();
            }
            }catch (FileNotFoundException e) {
                e.getStackTrace();
            }catch(IOException e){
                e.getStackTrace();
            }
        
		setSize(300, 400);
		setVisible(true);
		
				
		txtMessage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = txtMessage.getText();
				if(!msg.equals("")) {
                try {
                	bw.write("1//"+iSeat+"//"+userID+"//"+msg+"\n");
                    bw.flush();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
				messageView.append(userID + " : " + msg + "\n");
				messageView.setCaretPosition(messageView.getDocument().getLength());
				txtMessage.setText("");}
			}
		});
		btnSending.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = txtMessage.getText();
				if(!msg.equals("")) {
                try {
                    bw.write("1//"+iSeat+"//"+userID+"//"+msg+"\n");
                    bw.flush();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
				messageView.append(userID + " : " + msg + "\n");
				messageView.setCaretPosition(messageView.getDocument().getLength());
				txtMessage.setText("");}
			}
		});
	}
	
	public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            OutputStream out = socket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
	
}
