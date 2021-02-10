package com.java.client;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class memberForm extends JFrame {
	private JTextField txtName;
	private JTextField txtId;
	private JTextField txtPw;
	private JTextField txtPwcheck;
	private JTextField txt;

	public memberForm() {
		JFrame f = new JFrame();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("È¸¿ø°¡ÀÔ");
		Container d = f.getContentPane();
		
		JPanel p1 = new JPanel();
		d.add(p1, BorderLayout.CENTER);
		p1.setLayout(null);
		
		JLabel lblName = new JLabel("ÀÌ¸§");
		lblName.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		lblName.setBounds(40, 97, 57, 15);
		p1.add(lblName);
		
		JLabel lblMember = new JLabel("È¸ ¿ø °¡ ÀÔ");
		lblMember.setFont(new Font("±¼¸²", Font.BOLD, 20));
		lblMember.setBounds(145, 21, 147, 38);
		p1.add(lblMember);
		
		JButton btnYes = new JButton("È®ÀÎ");
		btnYes.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		btnYes.setBounds(60, 444, 97, 38);
		p1.add(btnYes);
		
		JButton btnCancel = new JButton("Ãë¼Ò");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.setVisible(false);
			}
		});
		btnCancel.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		btnCancel.setBounds(235, 444, 97, 38);
		p1.add(btnCancel);
		
		JLabel lblId = new JLabel("¾ÆÀÌµð");
		lblId.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		lblId.setBounds(40, 152, 57, 15);
		p1.add(lblId);
		
		JLabel lblPw = new JLabel("ºñ¹Ð¹øÈ£");
		lblPw.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		lblPw.setBounds(40, 210, 57, 15);
		p1.add(lblPw);
		
		JLabel lblPwcheck = new JLabel("ºñ¹Ð¹øÈ£È®ÀÎ");
		lblPwcheck.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		lblPwcheck.setBounds(40, 278, 97, 15);
		p1.add(lblPwcheck);
		
		JLabel lblBirth = new JLabel("»ý³â¿ùÀÏ");
		lblBirth.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		lblBirth.setBounds(38, 342, 57, 15);
		p1.add(lblBirth);
		
		txtName = new JTextField();
		txtName.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		txtName.setBounds(171, 94, 161, 21);
		p1.add(txtName);
		txtName.setColumns(10);
		
		txtId = new JTextField();
		txtId.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		txtId.setColumns(10);
		txtId.setBounds(170, 155, 161, 21);
		p1.add(txtId);
		
		txtPw = new JTextField();
		txtPw.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		txtPw.setColumns(10);
		txtPw.setBounds(170, 214, 161, 21);
		p1.add(txtPw);
		
		txtPwcheck = new JTextField();
		txtPwcheck.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		txtPwcheck.setColumns(10);
		txtPwcheck.setBounds(169, 277, 161, 21);
		p1.add(txtPwcheck);
		
		txt = new JTextField();
		txt.setFont(new Font("±¼¸²", Font.PLAIN, 15));
		txt.setColumns(10);
		txt.setBounds(169, 343, 161, 21);
		p1.add(txt);
		f.setTitle("È¸¿ø°¡ÀÔ");
		
		
		
		f.setUndecorated(true);
		f.setSize(444, 613);
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new memberForm();
	}

}