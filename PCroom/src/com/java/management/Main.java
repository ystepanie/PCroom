package com.java.management;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import DBconn.DBConnection;
import server.receiveThread;
import server.sendThread;


public class Main extends JFrame{

	int clickedSeat = -1;
   private LineBorder lineSeat = new LineBorder(Color.BLUE, 5, true);
   public static String strReservate = "";
   private BufferedWriter bw;
   Socket socket = null;
   public static ArrayList<Socket> sockeResList = new ArrayList<Socket>();
   public static ArrayList<Socket> socketLogList = new ArrayList<Socket>();
   static sendingMessage frameMessage= null;
   Main(){
      setTitle("PC����");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c= getContentPane();
        c.setLayout(new BorderLayout());
        JPanel north = new JPanel();
        JPanel center = new JPanel(null);
        JPanel east = new JPanel();
        north.setBackground(Color.GRAY);
        center.setBackground(Color.LIGHT_GRAY);
        center.setLayout(new GridLayout(5, 3, 150, 10));
        Color eastColor = new Color(0xCEE3F6);
        east.setBackground(eastColor);
        east.setLayout(new GridLayout(2, 1, 10, 10));
        
        c.add(north, BorderLayout.NORTH);

        JButton membership = new JButton("ȸ������");
        membership.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new memberView();
        	}
        });
        JButton revenue = new JButton("�������");
        revenue.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new moneyManage();
			}
		});
        JButton items = new JButton("��ǰ����");
        items.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new itemManage();
        	}
        });
        JButton help = new JButton("����");
        north.add(membership);
        north.add(revenue);
        north.add(items);
        north.add(help);
        
        c.add(center, BorderLayout.CENTER);
        JPanel[] computer = new JPanel[15];
        JPanel[] comNorth = new JPanel[15];
        JPanel[] comEast = new JPanel[15];
        JPanel[] comCenter = new JPanel[15];
        JLabel[] seatNum = new JLabel[15];
        JLabel[] userID = new JLabel[15];
        JLabel[] time = new JLabel[15];
        JLabel[] lblCleaning = new JLabel[15];
        JLabel[] lblFood = new JLabel[15];
        JLabel[] lblMessage = new JLabel[15];
        JLabel[] lblMinor = new JLabel[15];
        Color comColor = new Color(0xB2CCFF);
        ImageIcon cleaning = new ImageIcon("images/cleaning.png");
        ImageIcon food = new ImageIcon("images/food.png");
        ImageIcon message = new ImageIcon("images/message.png");
        ImageIcon minor = new ImageIcon("images/minor.png");
        Image imgCleaning = cleaning.getImage();
        Image imgFood = food.getImage();
        Image imgMessage = message.getImage();
        Image imgMinor = minor.getImage();
        imgCleaning = imgCleaning.getScaledInstance(35, 35, Image.SCALE_SMOOTH );
        imgFood = imgFood.getScaledInstance(35, 35, Image.SCALE_SMOOTH );
        imgMessage = imgMessage.getScaledInstance(35, 35, Image.SCALE_SMOOTH );
        imgMinor = imgMinor.getScaledInstance(35, 35, Image.SCALE_SMOOTH );
        cleaning = new ImageIcon(imgCleaning);
        food = new ImageIcon(imgFood);
        message = new ImageIcon(imgMessage);
        minor = new ImageIcon(imgMinor);
        
        for(int i = 0; i < 15; i++) {
           center.add(computer[i] = new JPanel());
           computer[i].setLayout(new BorderLayout());
           computer[i].setBackground(comColor);
           computer[i].add(comNorth[i] = new JPanel(), BorderLayout.NORTH);
           computer[i].add(comEast[i] = new JPanel(), BorderLayout.EAST);
           computer[i].add(comCenter[i] = new JPanel(), BorderLayout.CENTER);
           comNorth[i].setOpaque( false );
           comEast[i].setOpaque( false );
           comCenter[i].setOpaque( false );
           comCenter[i].setLayout(new GridLayout(2, 1));
           comNorth[i].add(seatNum[i] = new JLabel("NO."+Integer.toString(i+1)));
           comCenter[i].add(userID[i] = new JLabel(""));
           comCenter[i].add(time[i] = new JLabel(""));
           comEast[i].setLayout(new GridLayout(4, 1));
           comEast[i].add(lblCleaning[i] = new JLabel(cleaning));
           comEast[i].add(lblFood[i] = new JLabel(food));
           comEast[i].add(lblMessage[i] = new JLabel(message));
           comEast[i].add(lblMinor[i] = new JLabel(minor));
           lblCleaning[i].setVisible(false);
           lblFood[i].setVisible(false);
           lblMessage[i].setVisible(false);
           final int num = i;
           lblMessage[i].addMouseListener(new MouseListener() {
	
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
				e.getSource().toString();
				System.out.println(e);
				lblMessage[num].setVisible(false);
				if(frameMessage != null) {
                    frameMessage.dispose();
				}
                frameMessage = new sendingMessage(Integer.parseInt(seatNum[num].getText().substring(3)), userID[num].getText(), socket);
			}
		});
           lblMinor[i].setVisible(false);
           int click = i;
           computer[i].addMouseListener(new MouseListener() {
			
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
				if(computer[click].getBackground() == Color.blue) {
					if(clickedSeat != -1)
						computer[clickedSeat].setBorder(null);
					int result = JOptionPane.showConfirmDialog(null, "��������Ͻðڽ��ϰ�?","�������", JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION) {
						clickedSeat = click;
						computer[clickedSeat].setBackground(comColor);
						userID[clickedSeat].setText("");
						String[] splitReservate = strReservate.split("/");
						strReservate = "";
						for(int i=0;i<splitReservate.length;i++) {
							if(!splitReservate[i].equals(Integer.toString(clickedSeat+1))) {
								strReservate += splitReservate[i]+"/";
							}
						}System.out.println(strReservate);
						 OutputStream out = null;
						try {
				            /*out = socket.getOutputStream();
				            bw = new BufferedWriter(new OutputStreamWriter(out));
				            if(!strReservate.equals("") || strReservate != null) bw.write(strReservate);
				            else bw.write("���");
				            bw.flush();*/
				            for (int i=0; i< sockeResList.size(); i++){
				                
				                Socket client = (Socket) sockeResList.get(i);
				                out = client.getOutputStream();
				                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
				                if(!strReservate.equals("") || strReservate != null) pw.write(strReservate);
					            else pw.write("���");
				                pw.flush();
				            }
				        } catch (Exception e1) {
				            e1.printStackTrace();
				        }
						clickedSeat = -1;
					}
				}else {
				if(clickedSeat != -1)
					computer[clickedSeat].setBorder(null);
				if(clickedSeat == click) {
					computer[clickedSeat].setBorder(null);
					clickedSeat = -1;
				}else {				
				clickedSeat = click;
				computer[clickedSeat].setBorder(lineSeat);}
				}
			}
		});
        }
        
        c.add(east, BorderLayout.EAST);
        JPanel eNorth = new JPanel();
        JPanel eCenter = new JPanel();
        eNorth.setBackground(eastColor);
        eCenter.setBackground(eastColor);
        east.add(eNorth);
        east.add(eCenter);
        
        JLabel cntUser = new JLabel("00");
        JLabel cntComputer = new JLabel(" / 15");
        eNorth.add(cntUser);
        eNorth.add(cntComputer);
        
        JPanel vtcP = new JPanel();
        vtcP.setLayout(new GridLayout(6, 1, 10, 30));
        vtcP.setAlignmentY(CENTER_ALIGNMENT);
        vtcP.setBackground(eastColor);
        eCenter.add(vtcP);
        JButton btnExit = new JButton("PC����");
        JButton btnMessage = new JButton("�޼���");
        JButton btnAddTime = new JButton("�ð��߰�");
        JButton btnOrder = new JButton("��ǰ�ֹ�");
        JButton btnReservate = new JButton("�����ϱ�");
        JButton btnMemberInfo = new JButton("ȸ������");        
        vtcP.add(btnExit);
        vtcP.add(btnMessage);
        vtcP.add(btnAddTime);
        vtcP.add(btnOrder);
        vtcP.add(btnReservate);
        vtcP.add(btnMemberInfo);
        
        btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(clickedSeat != -1) {
					if(userID[clickedSeat].getText().equals("")){
						JOptionPane aa = new JOptionPane();
		      			aa.showMessageDialog(null, "������ ����ڰ� ���� �¼��Դϴ�.");
					}else {
					}
				}else {
					JOptionPane aa = new JOptionPane();
	      			aa.showMessageDialog(null, "�¼��� �������ּ���.");
				}
			}
		});
        
        btnMessage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(clickedSeat != -1) {
					if(userID[clickedSeat].getText().equals("")){
						JOptionPane aa = new JOptionPane();
		      			aa.showMessageDialog(null, "������ ����ڰ� ���� �¼��Դϴ�.");
					}else {
						sendingMessage frameMessage = new sendingMessage(clickedSeat, userID[clickedSeat].getText(), socket);
					}
				}else {
					JOptionPane aa = new JOptionPane();
	      			aa.showMessageDialog(null, "�¼��� �������ּ���.");
				}
			}
		});
        
		PopupMenu pm = new PopupMenu();
		MenuItem pm_item1 = new MenuItem("60��(01:00) | 1000��");
		MenuItem pm_item2 = new MenuItem("120��(02:00) | 2000��");
		MenuItem pm_item3 = new MenuItem("180��(03:00) | 3000��");
		MenuItem pm_item4 = new MenuItem("240��(04:00) | 4000��");
		MenuItem pm_item5 = new MenuItem("300��(05:00) | 5000��");
		MenuItem pm_item6 = new MenuItem("360��(06:00) | 6000��");
		MenuItem pm_item7 = new MenuItem("420��(07:00) | 7000��");
		MenuItem pm_item8 = new MenuItem("480��(08:00) | 8000��");
		MenuItem pm_item9 = new MenuItem("540��(09:00) | 9000��");
		MenuItem pm_item10 = new MenuItem("600��(10:00) | 10000��");
        pm.add(pm_item1);
        pm.add(pm_item2);
        pm.add(pm_item3);
        pm.add(pm_item4);
        pm.add(pm_item5);
        pm.add(pm_item6);
        pm.add(pm_item7);
        pm.add(pm_item8);
        pm.add(pm_item9);
        pm.add(pm_item10);
        add(pm);
        btnAddTime.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(clickedSeat != -1) {
					if(userID[clickedSeat].getText().equals("")){
						JOptionPane aa = new JOptionPane();
		      			aa.showMessageDialog(null, "������ ����ڰ� ���� �¼��Դϴ�.");
					}else {
						pm.show(btnAddTime, btnAddTime.getX(), btnAddTime.getY());
					}
				}else {
					JOptionPane aa = new JOptionPane();
	      			aa.showMessageDialog(null, "�¼��� �������ּ���.");
				}
			}
		});
        
        pm_item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstm = null;
				ResultSet rs = null;
				try {
					String query = "update member set pctime=pctime+60 where id=?";
					conn = DBConnection.getConnection();
					pstm = conn.prepareStatement(query);
           		 	pstm.setString(1, userID[clickedSeat].getText());
           		 	pstm.executeUpdate();
           		 
           		 	JOptionPane aa = new JOptionPane();
           		 	aa.showMessageDialog(null, userID[clickedSeat].getText()+"�Կ��� 60�� �߰��� �Ϸ�Ǿ����ϴ�.");
           		 
           		 	String[] strTime = time[clickedSeat].getText().split(":");
           		 	int pctime = Integer.parseInt(strTime[0])*60 + Integer.parseInt(strTime[1]);
           		 	int hour = pctime/60;
					int minute = pctime%60;
					String sHou, sMin;
					sHou = (hour<10 ?"0"+hour : hour+"");
					sMin = (minute<10 ? "0"+minute : minute+"");
           		 	time[clickedSeat].setText(sHou +":"+ sMin);
				}catch(SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
        pm_item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstm = null;
				ResultSet rs = null;
				try {
					String query = "update member set pctime=pctime+120 where id=?";
					conn = DBConnection.getConnection();
					pstm = conn.prepareStatement(query);
           		 	pstm.setString(1, userID[clickedSeat].getText());
           		 	pstm.executeUpdate();
           		 
           		 	JOptionPane aa = new JOptionPane();
           		 	aa.showMessageDialog(null, userID[clickedSeat].getText()+"�Կ��� 120�� �߰��� �Ϸ�Ǿ����ϴ�.");
           		 	
           		 	String[] strTime = time[clickedSeat].getText().split(":");
           		 	int pctime = Integer.parseInt(strTime[0])*60 + Integer.parseInt(strTime[1]);
           		 	int hour = pctime/60;
					int minute = pctime%60;
					String sHou, sMin;
					sHou = (hour<10 ?"0"+hour : hour+"");
					sMin = (minute<10 ? "0"+minute : minute+"");
           		 	time[clickedSeat].setText(sHou +":"+ sMin);
				}catch(SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
        pm_item3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstm = null;
				ResultSet rs = null;
				try {
					String query = "update member set pctime=pctime+180 where id=?";
					conn = DBConnection.getConnection();
					pstm = conn.prepareStatement(query);
           		 	pstm.setString(1, userID[clickedSeat].getText());
           		 	pstm.executeUpdate();
           		 
           		 	JOptionPane aa = new JOptionPane();
           		 	aa.showMessageDialog(null, userID[clickedSeat].getText()+"�Կ��� 180�� �߰��� �Ϸ�Ǿ����ϴ�.");
				}catch(SQLException sqle) {
					System.out.println("SELECT������ ���� �߻�");
					sqle.printStackTrace();
				}
			}
		});
        pm_item4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstm = null;
				ResultSet rs = null;
				try {
					String query = "update member set pctime=pctime+240 where id=?";
					conn = DBConnection.getConnection();
					pstm = conn.prepareStatement(query);
           		 	pstm.setString(1, userID[clickedSeat].getText());
           		 	pstm.executeUpdate();
           		 
           		 	JOptionPane aa = new JOptionPane();
           		 	aa.showMessageDialog(null, userID[clickedSeat].getText()+"�Կ��� 240�� �߰��� �Ϸ�Ǿ����ϴ�.");
           		 	
           		 	String[] strTime = time[clickedSeat].getText().split(":");
           		 	int pctime = Integer.parseInt(strTime[0])*60 + Integer.parseInt(strTime[1]);
           		 	int hour = pctime/60;
					int minute = pctime%60;
					String sHou, sMin;
					sHou = (hour<10 ?"0"+hour : hour+"");
					sMin = (minute<10 ? "0"+minute : minute+"");
           		 	time[clickedSeat].setText(sHou +":"+ sMin);
				}catch(SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
        pm_item5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstm = null;
				ResultSet rs = null;
				try {
					String query = "update member set pctime=pctime+300 where id=?";
					conn = DBConnection.getConnection();
					pstm = conn.prepareStatement(query);
           		 	pstm.setString(1, userID[clickedSeat].getText());
           		 	pstm.executeUpdate();
           		 
           		 	JOptionPane aa = new JOptionPane();
           		 	aa.showMessageDialog(null, userID[clickedSeat].getText()+"�Կ��� 300�� �߰��� �Ϸ�Ǿ����ϴ�.");
           		 	
           		 	String[] strTime = time[clickedSeat].getText().split(":");
           		 	int pctime = Integer.parseInt(strTime[0])*60 + Integer.parseInt(strTime[1]);
           		 	int hour = pctime/60;
					int minute = pctime%60;
					String sHou, sMin;
					sHou = (hour<10 ?"0"+hour : hour+"");
					sMin = (minute<10 ? "0"+minute : minute+"");
           		 	time[clickedSeat].setText(sHou +":"+ sMin);
				}catch(SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
        pm_item6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstm = null;
				ResultSet rs = null;
				try {
					String query = "update member set pctime=pctime+360 where id=?";
					conn = DBConnection.getConnection();
					pstm = conn.prepareStatement(query);
           		 	pstm.setString(1, userID[clickedSeat].getText());
           		 	pstm.executeUpdate();
           		 
           		 	JOptionPane aa = new JOptionPane();
           		 	aa.showMessageDialog(null, userID[clickedSeat].getText()+"�Կ��� 360�� �߰��� �Ϸ�Ǿ����ϴ�.");
           		 	
           		 	String[] strTime = time[clickedSeat].getText().split(":");
           		 	int pctime = Integer.parseInt(strTime[0])*60 + Integer.parseInt(strTime[1]);
           		 	int hour = pctime/60;
					int minute = pctime%60;
					String sHou, sMin;
					sHou = (hour<10 ?"0"+hour : hour+"");
					sMin = (minute<10 ? "0"+minute : minute+"");
           		 	time[clickedSeat].setText(sHou +":"+ sMin);
				}catch(SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
        pm_item7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstm = null;
				ResultSet rs = null;
				try {
					String query = "update member set pctime=pctime+420 where id=?";
					conn = DBConnection.getConnection();
					pstm = conn.prepareStatement(query);
           		 	pstm.setString(1, userID[clickedSeat].getText());
           		 	pstm.executeUpdate();
           		 
           		 	JOptionPane aa = new JOptionPane();
           		 	aa.showMessageDialog(null, userID[clickedSeat].getText()+"�Կ��� 420�� �߰��� �Ϸ�Ǿ����ϴ�.");
           		 	
           		 	String[] strTime = time[clickedSeat].getText().split(":");
           		 	int pctime = Integer.parseInt(strTime[0])*60 + Integer.parseInt(strTime[1]);
           		 	int hour = pctime/60;
					int minute = pctime%60;
					String sHou, sMin;
					sHou = (hour<10 ?"0"+hour : hour+"");
					sMin = (minute<10 ? "0"+minute : minute+"");
           		 	time[clickedSeat].setText(sHou +":"+ sMin);
				}catch(SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
        pm_item8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstm = null;
				ResultSet rs = null;
				try {
					String query = "update member set pctime=pctime+480 where id=?";
					conn = DBConnection.getConnection();
					pstm = conn.prepareStatement(query);
           		 	pstm.setString(1, userID[clickedSeat].getText());
           		 	pstm.executeUpdate();
           		 
           		 	JOptionPane aa = new JOptionPane();
           		 	aa.showMessageDialog(null, userID[clickedSeat].getText()+"�Կ��� 480�� �߰��� �Ϸ�Ǿ����ϴ�.");
           		 	
           		 	String[] strTime = time[clickedSeat].getText().split(":");
           		 	int pctime = Integer.parseInt(strTime[0])*60 + Integer.parseInt(strTime[1]);
           		 	int hour = pctime/60;
					int minute = pctime%60;
					String sHou, sMin;
					sHou = (hour<10 ?"0"+hour : hour+"");
					sMin = (minute<10 ? "0"+minute : minute+"");
           		 	time[clickedSeat].setText(sHou +":"+ sMin);
				}catch(SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
        pm_item9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstm = null;
				ResultSet rs = null;
				try {
					String query = "update member set pctime=pctime+540 where id=?";
					conn = DBConnection.getConnection();
					pstm = conn.prepareStatement(query);
           		 	pstm.setString(1, userID[clickedSeat].getText());
           		 	pstm.executeUpdate();
           		 
           		 	JOptionPane aa = new JOptionPane();
           		 	aa.showMessageDialog(null, userID[clickedSeat].getText()+"�Կ��� 540�� �߰��� �Ϸ�Ǿ����ϴ�.");
           		 	
           		 	String[] strTime = time[clickedSeat].getText().split(":");
           		 	int pctime = Integer.parseInt(strTime[0])*60 + Integer.parseInt(strTime[1]);
           		 	int hour = pctime/60;
					int minute = pctime%60;
					String sHou, sMin;
					sHou = (hour<10 ?"0"+hour : hour+"");
					sMin = (minute<10 ? "0"+minute : minute+"");
           		 	time[clickedSeat].setText(sHou +":"+ sMin);
				}catch(SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
        pm_item10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				PreparedStatement pstm = null;
				ResultSet rs = null;
				try {
					String query = "update member set pctime=pctime+600 where id=?";
					conn = DBConnection.getConnection();
					pstm = conn.prepareStatement(query);
           		 	pstm.setString(1, userID[clickedSeat].getText());
           		 	pstm.executeUpdate();
           		 
           		 	JOptionPane aa = new JOptionPane();
           		 	aa.showMessageDialog(null, userID[clickedSeat].getText()+"�Կ��� 600�� �߰��� �Ϸ�Ǿ����ϴ�.");
           		 	
           		 	String[] strTime = time[clickedSeat].getText().split(":");
           		 	int pctime = Integer.parseInt(strTime[0])*60 + Integer.parseInt(strTime[1]);
           		 	int hour = pctime/60;
					int minute = pctime%60;
					String sHou, sMin;
					sHou = (hour<10 ?"0"+hour : hour+"");
					sMin = (minute<10 ? "0"+minute : minute+"");
           		 	time[clickedSeat].setText(sHou +":"+ sMin);
				}catch(SQLException sqle) {
					sqle.printStackTrace();
				}
			}
		});
        
        btnOrder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new orderManage();
			}
		});

        btnReservate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(clickedSeat != -1) {
					if(userID[clickedSeat].getText().equals("")){
						computer[clickedSeat].setBackground(Color.blue);
						userID[clickedSeat].setText("�����¼�");
						strReservate += (clickedSeat+1) + "/";
						System.out.println(strReservate);
						OutputStream out = null;
						try {
				            /*out = socket.getOutputStream();
				            bw = new BufferedWriter(new OutputStreamWriter(out));
				            if(!strReservate.equals("") || strReservate != null) {
				            bw.write(strReservate);
				            bw.flush();
				            System.out.println("������� ����Ϸ�");}*/
				            for (int i=0; i< sockeResList.size(); i++){
				                Socket client = (Socket) sockeResList.get(i);
				                out = client.getOutputStream();
				                PrintWriter pw = new PrintWriter(new OutputStreamWriter(out), true);
				                if(!strReservate.equals("") || strReservate != null) {
						            pw.write(strReservate);
						            pw.flush();}
				            }
				        } catch (Exception e1) {
				            e1.printStackTrace();
				        }
					}else {
						JOptionPane aa = new JOptionPane();
		      			aa.showMessageDialog(null, "�̹� ������� �¼��Դϴ�.");
					}
				}else {
					JOptionPane aa = new JOptionPane();
	      			aa.showMessageDialog(null, "�¼��� �������ּ���.");
				}
			}
		});

        btnMemberInfo.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
               // TODO Auto-generated method stub
               if(clickedSeat != -1) {
                  if(userID[clickedSeat].getText().equals("")){
                     JOptionPane aa = new JOptionPane();
                        aa.showMessageDialog(null, "������ ����ڰ� ���� �¼��Դϴ�.");
                  }else {
                     Connection conn = null;
                     PreparedStatement pr = null;
                     ResultSet rs = null;
                     String query = "select * from member where id='"+userID[clickedSeat].getText()+"'";
                     try {
                        conn = DBConnection.getConnection();
                           pr = conn.prepareStatement(query);
                           rs = pr.executeQuery();
                           SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
                           String con_message = null;
                           while(rs.next()) {
                        	   con_message = "ID : "+rs.getString("id")+"\n�̸� : "+rs.getString("name")+"\n��й�ȣ : "+rs.getString("password")+"\n������� : "+formatter.format(rs.getDate("birth"))+"\n�޸� : "+rs.getString("memo")+"\n���Գ�¥ : "+formatter.format(rs.getDate("joindate"))+"\n";
                           }
                           JOptionPane.showMessageDialog(null,con_message,userID[clickedSeat].getText()+"���� ����", 1);
                         }catch(Exception e1){
                        e1.printStackTrace();
                     }
                     
                  }
               }else {
                  JOptionPane aa = new JOptionPane();
                     aa.showMessageDialog(null, "�¼��� �������ּ���.");
               }
            }
         });

        setSize(1300, 1000);
        setVisible(true);
        
        try {
			ServerSocket s_socket = new ServerSocket(3333);
        	while(true) {
        		socket = s_socket.accept();
        		System.out.println("���Ϲ޾���");
        		ArrayList<JPanel> comList = new ArrayList<JPanel>();
        		ArrayList<JLabel> idList = new ArrayList<JLabel>();
        		ArrayList<JLabel> timeList = new ArrayList<JLabel>();
			ArrayList<JLabel> cleaningList = new ArrayList<JLabel>();
			ArrayList<JLabel> foodList = new ArrayList<JLabel>();
			ArrayList<JLabel> messageList = new ArrayList<JLabel>();
			ArrayList<JLabel> minorList = new ArrayList<JLabel>();
			for(int i = 0; i < 15; i++) {
				comList.add(computer[i]);
				idList.add(userID[i]);
				timeList.add(time[i]);
				cleaningList.add(lblCleaning[i]);
				foodList.add(lblFood[i]);
				messageList.add(lblMessage[i]);
				minorList.add(lblMinor[i]);
			}
			receiveThread rec_thread = new receiveThread(comList, idList, timeList, cleaningList, foodList, messageList, minorList);
			rec_thread.setSocket(socket);
			rec_thread.start();
        	}
        }catch(IOException e) {
			e.printStackTrace();
		}
   }
   
   public static void main(String[] args) {
      // TODO Auto-generated method stub
			new Main();
   }

}