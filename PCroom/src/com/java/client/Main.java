package com.java.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;


import DBconn.DBConnection;
import client.JFrameWindowClosingEventHandler;

public class Main extends JFrame {

	int remain = 0;
	Timer timer = new Timer();
	OutputStream out;
    BufferedWriter bw;
	
	  Main(int iSeat, String userID, Socket socket, int pctime){
	      setTitle("PC����");
	        Container c= getContentPane();
	        c.setLayout(new FlowLayout());
	        this.remain = pctime;
	        try {
		        out = socket.getOutputStream();
		        bw = new BufferedWriter(new OutputStreamWriter(out));
	        }catch (Exception e) {
				e.printStackTrace();
			}
	        
	        JPanel panNum = new JPanel();
	        panNum.setPreferredSize(new Dimension(450, 50));
	        JLabel lbNum = new JLabel("No. " + iSeat, SwingConstants.RIGHT);
	        panNum.add(lbNum);
	        JPanel panID = new JPanel();
	        panID.setPreferredSize(new Dimension(450, 50));
	        JLabel lbID1 = new JLabel(" ID ", SwingConstants.LEFT);
	        JLabel lbID2 = new JLabel(userID, SwingConstants.RIGHT);
	        BevelBorder border=new BevelBorder(BevelBorder.RAISED);
	        lbID1.setBorder(border);
	        panID.add(lbID1);
	        panID.add(lbID2);
	        c.add(panNum);
	        c.add(panID);
	        JPanel panTime = new JPanel();
	        panTime.setPreferredSize(new Dimension(450, 100));
	        panTime.setLayout(new GridLayout(2, 2));
	        JPanel panUsageFee = new JPanel();
	        JLabel lbUsageFee = new JLabel(" ����� ", SwingConstants.LEFT);
	        JLabel UsageFee = new JLabel(" ����� ", SwingConstants.RIGHT);
	        panUsageFee.add(lbUsageFee);
	        panUsageFee.add(UsageFee);
	        panTime.add(panUsageFee);
	        
	        JPanel panUsageTime = new JPanel();
	        JLabel lbUsageTime = new JLabel(" ���ð� ", SwingConstants.LEFT);
	        JLabel UsageTime = new JLabel(" ���ð� ", SwingConstants.RIGHT);
	        panUsageTime.add(lbUsageTime);
	        panUsageTime.add(UsageTime);
	        panTime.add(panUsageTime);
	        
	        JPanel panStartTime = new JPanel();
	        JLabel lbStartTime = new JLabel(" ���۽ð� ", SwingConstants.LEFT);
	        JLabel StartTime = new JLabel(" ���۽ð� ", SwingConstants.RIGHT);
	        panStartTime.add(lbStartTime);
	        panStartTime.add(StartTime);
	        panTime.add(panStartTime);
	        
	        JPanel panTimeRemaining = new JPanel();
	        JLabel lbTimeRemaining = new JLabel(" �����ð� ", SwingConstants.LEFT);
	        JLabel TimeRemaining = new JLabel(" �����ð� ", SwingConstants.RIGHT);

				int hour = pctime/60;
				int minute = pctime%60;
				String sHou, sMin;
				sHou = (hour<10 ?"0"+hour : hour+"");
				sMin = (minute<10 ? "0"+minute : minute+"");
				TimeRemaining.setText(sHou +":"+ sMin);
			
			int delay = 1000;
	        int period = 1000;
			timer.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run() {
					String[] strTime = TimeRemaining.getText().split(":");
					if(Integer.parseInt(strTime[0]) + Integer.parseInt(strTime[1]) == 0) {
						System.out.println("Ÿ�̸Ӱ� ������");
						timer.cancel();
						new login(iSeat);
						if(Integer.parseInt(strTime[0]) + Integer.parseInt(strTime[1]) == 0) {
							remain=0;
						}else {
							remain = Integer.parseInt(strTime[0])*60+ Integer.parseInt(strTime[1]);
						}
						System.out.println("windowClosing()");
						try {
				        	bw.write("-1//"+iSeat+"//"+userID+"\n");
				            bw.flush();
				            bw.close();
				            socket.close();
				        } catch (IOException e1) {
				            // TODO Auto-generated catch block
				            e1.printStackTrace();
				        }
						Connection conn = null;
						PreparedStatement pstm = null;
					
						try {
							String quary = "update MEMBER set pctime = ? where id = '"+userID+"'";
						
							conn = DBConnection.getConnection();
							pstm = conn.prepareStatement(quary);
							pstm.setInt(1, remain);
							int result = pstm.executeUpdate();		//Update�� ����
							if(result >= 1)
								System.out.println("���ڵ� ���� ����");
							else
								System.out.println("���ڵ� ���� ����");
						}catch(Exception ex) {
							ex.printStackTrace();
						}finally {
							try {
								pstm.close();
								conn.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						//���� ��ü ����
						 try{
							 File file1 = new File("chat"+userID+".txt");
						 //�Է� ��Ʈ�� ����
							 if(file1.exists() == true) {
								 file1.delete();
							 }
						 }catch(Exception e1) {
							 e1.printStackTrace();
						 }
							dispose();
					}else {
						if((int)Integer.parseInt(strTime[1]) != 0) {
							int minute = Integer.parseInt(strTime[1]) - 1;
							String sMin = (minute<10 ? "0"+minute : minute+"");
							TimeRemaining.setText(strTime[0] +":"+ sMin);
						}else {
							int hour = Integer.parseInt(strTime[0]) - 1;
							String sHou = (hour<10 ? "0"+hour : hour+"");
							TimeRemaining.setText(sHou +":59");
						}
					}
				}
			}, delay, period);
			panTimeRemaining.add(lbTimeRemaining);
	        panTimeRemaining.add(TimeRemaining);
	        panTime.add(panTimeRemaining);
	        c.add(panTime);
	        
	        JPanel panButton = new JPanel();
	        panButton.setPreferredSize(new Dimension(450, 150));
	        panButton.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
	        panButton.setLayout(new GridLayout(2, 2, 10, 10));
	        JButton btnFood = new JButton("�԰Ÿ� �ֹ�");
	        JButton btnMessage = new JButton("�޼��� ������");
	        JButton btnExit = new JButton("��� ����");
	        panButton.add(btnFood);
	        panButton.add(btnMessage);
	        panButton.add(new JPanel());
	        panButton.add(btnExit);
	        c.add(panButton);
	        
	        JPanel panAdvertise = new JPanel();
	        panAdvertise.setPreferredSize(new Dimension(450, 280));
	        panAdvertise.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
	        panAdvertise.setBackground(Color.WHITE);
	        c.add(panAdvertise);
	        
	        JPanel panOthers = new JPanel();
	        panOthers.setLayout(new GridLayout(1, 2, 10, 10));
	        JButton btnTermsOfUse = new JButton("�̿���");
	        JButton btnPrivacyPolicy = new JButton("��������ó����ħ");
	        panOthers.add(btnTermsOfUse);
	        panOthers.add(btnPrivacyPolicy);
	        c.add(panOthers);
	        

		    addWindowListener(new JFrameWindowClosingEventHandler(iSeat, userID, TimeRemaining, socket)); //�����̺�Ʈ
	        setUndecorated(true);
	        setSize(450, 700);
	        Dimension frameSize = getSize(); // ������ ������
	        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ����� ������
	        setLocation((screenSize.width - frameSize.width - 10), (screenSize.height - screenSize.height + 10)); // ȭ�� ���� ���
	        setVisible(true);
	        
	        btnMessage.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new sendingMessage(iSeat, userID, socket);
					
				}
			});
	        
	        btnFood.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new order(iSeat, userID, socket);
				}
			});
	        
	        btnExit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					timer.cancel();
					String[] strTime = TimeRemaining.getText().split(":");
					if(Integer.parseInt(strTime[0]) + Integer.parseInt(strTime[1]) == 0) {
						remain=0;
					}else {
						remain = Integer.parseInt(strTime[0])*60+ Integer.parseInt(strTime[1]);
					}
					System.out.println("windowClosing()");
					try {
			        	bw.write("-1//"+iSeat+"//"+userID+"\n");
			            bw.flush();
			            bw.close();
			            socket.close();
			        } catch (IOException e1) {
			            // TODO Auto-generated catch block
			            e1.printStackTrace();
			        }
					Connection conn = null;
					PreparedStatement pstm = null;
				
					try {
						String quary = "update MEMBER set pctime = ? where id = '"+userID+"'";
					
						conn = DBConnection.getConnection();
						pstm = conn.prepareStatement(quary);
						pstm.setInt(1, remain);
						int result = pstm.executeUpdate();		//Update�� ����
						if(result >= 1)
							System.out.println("���ڵ� ���� ����");
						else
							System.out.println("���ڵ� ���� ����");
					}catch(Exception ex) {
						ex.printStackTrace();
					}finally {
						try {
							pstm.close();
							conn.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					//���� ��ü ����
					 try{
						 File file1 = new File("chat"+userID+".txt");
					 //�Է� ��Ʈ�� ����
						 if(file1.exists() == true) {
							 file1.delete();
						 }
					 }catch(Exception e1) {
						 e1.printStackTrace();
					 }dispose();
				}
			});
	  }
}
