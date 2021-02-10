package com.java.management;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class sendingMessage extends JFrame{

	private Socket socket;
    private BufferedWriter bw;
    OutputStream out;
    File file;

	public sendingMessage(int iSeat, String userID, Socket socket) {
		this.setSocket(socket);
		setTitle("No."+iSeat+" ID : " + userID);
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
        JButton btnSending = new JButton("傈价");
        JPanel panSouth = new JPanel();
        panSouth.add(txtMessage);
        panSouth.add(btnSending);
        c.add(panSouth, BorderLayout.SOUTH);
        
        try{
            //颇老 按眉 积己
            file = new File("chat"+userID+".txt");
             //涝仿 胶飘覆 积己
            if(file.exists() == true) {
             FileReader file_reader = new FileReader(file);
             int cur = 0;
             String strMessage = null;
             while((cur = file_reader.read()) != -1){
                System.out.print((char)cur);
                strMessage += (char)cur;
             }
             messageView.append(strMessage);
             file_reader.close();}
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
                	out = socket.getOutputStream();
                	bw = new BufferedWriter(new OutputStreamWriter(out));
                    bw.write(userID + "//" + msg+"\n");
                    bw.flush();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                	System.out.println("捞其捞瘤 巩力");
                    e1.printStackTrace();
                }
                try {
                	String fileText = ReadFileText(file);
                	BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));
                	String Text;
                	Text = fileText + "包府磊 : " + msg + "\r\n";
                	buffWrite.write(Text, 0, Text.length());
                	buffWrite.flush();
                	buffWrite.close();
                }catch(IOException e2) {
                	e2.printStackTrace();
                }
				
				messageView.append("包府磊 : " + msg + "\n");
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
	                	out = socket.getOutputStream();
	                	bw = new BufferedWriter(new OutputStreamWriter(out));
	                    bw.write(userID + "//" + msg+"\n");
	                    bw.flush();
	                } catch (IOException e1) {
	                    // TODO Auto-generated catch block
	                	System.out.println("捞其捞瘤 巩力");
	                    e1.printStackTrace();
	                }finally {
	                	try {
	                		out.close();
	                		bw.close();
	                	}catch (Exception e2) {
							e2.printStackTrace();
						}
	                }
                try {
                	String fileText = ReadFileText(file);
                	BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));
                	String Text;
                	Text = fileText + "包府磊 : " + msg + "\r\n";
                	buffWrite.write(Text, 0, Text.length());
                	buffWrite.flush();
                	buffWrite.close();
                }catch(IOException e2) {
                	e2.printStackTrace();
                }
				messageView.append("包府磊 : " + msg + "\n");
				messageView.setCaretPosition(messageView.getDocument().getLength());
				txtMessage.setText("");}
			}
		});
	}
	
	public void setSocket(Socket socket) {
        this.socket = socket;
    } 

	private String ReadFileText(File file) {
		String strText = "";
		int nBuffer;
		try {
			BufferedReader buffRead = new BufferedReader(new FileReader(file));
			while((nBuffer = buffRead.read()) != -1) {
				strText += (char)nBuffer;
			}
			buffRead.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return strText;
	}
}