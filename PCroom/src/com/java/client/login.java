package com.java.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DBconn.DBLogin;
import client.sendThread;


public class login extends JFrame{
	private JTextField txtId;
	private JTextField txtPw;
	public login(int iSeat){
		setTitle("PC����");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Container c= getContentPane();
        c.setLayout(new BorderLayout());
        
        ImageIcon icon = new ImageIcon("images/diva.png");
        JPanel panImg = new JPanel() {
        	public void paintComponent(Graphics g) {
        		Dimension d = getSize();
                g.drawImage(icon.getImage(), 0, 0, d.width, d.height, null);
        		setOpaque(false);
        		super.paintComponent(g);
        	}
        };
        c.add(panImg, BorderLayout.CENTER);
        
        JPanel panSouth = new JPanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panSouth.setPreferredSize(new Dimension(screenSize.width, 300));
        panSouth.setLayout(new GridLayout(1, 3, 10, 10));
        panSouth.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
        c.add(panSouth, BorderLayout.SOUTH);
        
        JPanel panLogo = new JPanel();
        JPanel panNotice = new JPanel();
        JPanel panLogin = new JPanel();
        panLogin.setLayout(new BorderLayout());
        panSouth.add(panLogo);
        panSouth.add(panNotice);
        panSouth.add(panLogin);
        
        JLabel lblLogo = new JLabel(new ImageIcon("images/logo.png"));
        panLogo.add(lblLogo);
        
        JLabel seatNum = new JLabel("NO."+iSeat);
        seatNum.setFont(new Font("�������", Font.BOLD, 100));
        panNotice.add(seatNum);

        JPanel p1 = new JPanel();	
		p1.setLayout(null);
		panLogin.add(p1, BorderLayout.CENTER);
		
		JLabel lblid = new JLabel("I D");
		lblid.setFont(new Font("����", Font.BOLD, 22));
		lblid.setBounds(63, 58, 48, 43);
		p1.add(lblid);
		
		JLabel lblPw = new JLabel("P W");
		lblPw.setFont(new Font("����", Font.BOLD, 22));
		lblPw.setBounds(54, 127, 57, 43);
		p1.add(lblPw);
		
		txtId = new JTextField();
		txtId.setFont(new Font("����", Font.PLAIN, 17));
		txtId.setBounds(150, 68, 193, 29);
		p1.add(txtId);
		txtId.setColumns(10);
		
		txtPw = new JTextField();
		txtPw.setFont(new Font("����", Font.PLAIN, 17));
		txtPw.setColumns(10);
		txtPw.setBounds(150, 141, 193, 29);
		p1.add(txtPw);
		
		JButton btnYes = new JButton("Ȯ��");
		btnYes.setFont(new Font("����", Font.PLAIN, 16));
		btnYes.setBounds(54, 233, 121, 38);
		p1.add(btnYes);
		
		JButton btnMember = new JButton("ȸ������");
		btnMember.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new memberForm();
			}
		});
		btnMember.setFont(new Font("����", Font.PLAIN, 16));
		btnMember.setBounds(226, 233, 121, 38);
		p1.add(btnMember);
		
		JLabel lblPc = new JLabel("Yuda PC Room");
		lblPc.setFont(new Font("����", Font.BOLD, 14));
		lblPc.setBounds(143, 10, 147, 38);
		p1.add(lblPc);
		
		btnYes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText();
				String pw = txtPw.getText();
				try {
					DBLogin logdb = new DBLogin();
					int result = logdb.checkIDPW(id, pw);
					
					if(result%10 == 0) {
						Socket socket = null;
						try {
						socket = new Socket("192.168.0.3", 3333);
						
						sendThread send_thread = new sendThread();
						send_thread.setSocket(socket);
						send_thread.setFunction(0);
						send_thread.setSeatNum(iSeat);
						send_thread.setContent(Integer.toString((int)result/10));
						send_thread.setUserID(txtId.getText());
						
						send_thread.start();
						} catch(IOException exception) {
							exception.printStackTrace();
						}
						new Main(iSeat, txtId.getText(), socket, (int)result/10);
						dispose();
					} else if(result==1){
						JOptionPane aa = new JOptionPane();
						aa.showMessageDialog(null, "���̵�, ��й�ȣ�� Ȯ�����ּ���.", "�޼���", JOptionPane.ERROR_MESSAGE);
					}else {
						JOptionPane aa = new JOptionPane();
						aa.showMessageDialog(null, "�ð��������ּ���.", "�޼���", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					System.out.println("���� ���̵�");
				}
				return;
		}
	});
		
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setVisible(true);
	}
}
