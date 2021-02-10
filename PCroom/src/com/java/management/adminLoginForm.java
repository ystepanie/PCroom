package com.java.management;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class adminLoginForm extends JFrame {
	
	private JTextField txtId;
	private JTextField txtPw;
	
	adminLoginForm() {
		JFrame f = new JFrame();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("°ü¸®ÀÚ¸ðµå ·Î±×ÀÎ");
		Container d = f.getContentPane();
		
		JPanel p1 = new JPanel();	
		p1.setLayout(null);
		d.add(p1, BorderLayout.CENTER);
		
		JLabel lblid = new JLabel("I D");
		lblid.setFont(new Font("±¼¸²", Font.BOLD, 22));
		lblid.setBounds(63, 58, 48, 43);
		p1.add(lblid);
		
		JLabel lblPw = new JLabel("P W");
		lblPw.setFont(new Font("±¼¸²", Font.BOLD, 22));
		lblPw.setBounds(54, 127, 57, 43);
		p1.add(lblPw);
		
		txtId = new JTextField();
		txtId.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		txtId.setBounds(150, 68, 193, 29);
		p1.add(txtId);
		txtId.setColumns(10);
		txtPw = new JTextField();
		txtPw.setFont(new Font("±¼¸²", Font.PLAIN, 17));
		txtPw.setColumns(10);
		txtPw.setBounds(150, 141, 193, 29);
		p1.add(txtPw);
		
		JButton btnYes = new JButton("È®ÀÎ");
		btnYes.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		btnYes.setBounds(54, 233, 121, 38);
		p1.add(btnYes);
		
		JButton btnCancel = new JButton("Ãë¼Ò");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancel.setFont(new Font("±¼¸²", Font.PLAIN, 16));
		btnCancel.setBounds(226, 233, 121, 38);
		p1.add(btnCancel);
		
		JLabel lblPc = new JLabel("Yuda PC °ü¸®ÀÚ¸ðµå");
		lblPc.setFont(new Font("±¼¸²", Font.BOLD, 14));
		lblPc.setBounds(128, 10, 179, 38);
		p1.add(lblPc);
		
		
		f.setSize(423, 377);
		f.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new adminLoginForm();
	}

}