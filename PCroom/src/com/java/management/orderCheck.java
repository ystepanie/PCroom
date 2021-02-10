package com.java.management;

import java.awt.Container;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class orderCheck extends JFrame {
	
	File file;
	public orderCheck(int iSeat) {
		setTitle(iSeat+" 번 좌석의 주문입니다.");
		 setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    Container c= getContentPane();
		c.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(36, 21, 750, 500);
		c.add(scrollPane);
		
		 try{
			 //파일 객체 생성
			 file = new File(iSeat+" 번 좌석 주문.txt");
			 //입력 스트림 생성
			 if(file.exists() == true) {
				 FileReader file_reader = new FileReader(file);
				 int cur = 0;
	             String strOrder = null;
	             while((cur = file_reader.read()) != -1){
	                System.out.print((char)cur);
	                strOrder += (char)cur;
	             }
	             textArea.append(strOrder);
	             file_reader.close();}
			 }catch (FileNotFoundException e1) {
	                e1.getStackTrace();
	                }catch(IOException e2){
	                e2.getStackTrace();
	                }
	
		JButton btnNew = new JButton("확인");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(file.exists()) {
					file.delete();
				} else {
					JOptionPane.showMessageDialog(null, "파일이 없습니다.", "메세지", JOptionPane.ERROR_MESSAGE);
				}
				dispose();
			}
		});
		btnNew.setBounds(20, 700, 97, 42);
		c.add(btnNew);
		
		setSize(1200,800);
		setVisible(true);
	}	
	
}
