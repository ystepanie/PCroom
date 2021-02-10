package com.java.management;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import DBconn.DBConnection;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class memberSearch extends JFrame {
	private JTextField txtName;
	private JButton btnSearch;
	Connection conn;
	java.sql.Statement stmt;
	ResultSet rs = null;
	memberSearch() {
		setTitle("ȸ���˻�");
	      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	      Container c = getContentPane();
	      c.setLayout(null);
	      
	      JLabel lblName = new JLabel("\uC774 \uB984 :");
	      lblName.setFont(new Font("����", Font.PLAIN, 16));
	      lblName.setBounds(30, 81, 57, 15);
	      getContentPane().add(lblName);
	      
	      txtName = new JTextField();
	      txtName.setBounds(99, 79, 188, 21);
	      getContentPane().add(txtName);
	      txtName.setColumns(10);
	      
	      btnSearch = new JButton("\uD655 \uC778");
	      btnSearch.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		try {
	      			conn = DBConnection.getConnection();
	      			String query = "select id,name,birth,pctime from member where name like '%" + txtName.getText().trim()+"%'";
	      			stmt = conn.createStatement();
	      			rs = stmt.executeQuery(query);
	      			
	      			ResultSetMetaData resultSetMetaData = rs.getMetaData();
	      			Object [] tempObject = new Object[resultSetMetaData.getColumnCount()];
	      			memberView.model.setRowCount(0);
	      			
	      			while(rs.next()) {
	      				for(int i=0;i<resultSetMetaData.getColumnCount();i++) {
	      					tempObject[i] = rs.getString(i+1);
	      				}
	      				memberView.model.addRow(tempObject);
	      			}
	      			if( memberView.model.getRowCount() > 0 )
	    			{
	    				memberView.tbl.setRowSelectionInterval(0, 0); //ù°�ٿ� ��Ŀ��
	    			} else if(memberView.model.getRowCount() == 0) {
	    				JOptionPane aa = new JOptionPane();
		      			aa.showMessageDialog(null, "�˻��� �̸��� �����ϴ�.", "�޼���", JOptionPane.ERROR_MESSAGE);
	    			}
	      			JOptionPane aa = new JOptionPane();
	      			aa.showMessageDialog(null, "�˻��� �Ϸ�Ǿ����ϴ�.");
	      			dispose();
	      		} catch (Exception e1) { 
	      			e1.printStackTrace();
	      		} finally {
	      			try
	      			{
	      				if( conn != null )
	      				{
	      					conn.close();
	      				}
	      				 
	      				if( stmt != null )
	      				{
	      					stmt.close();
	      				}
	      				 
	      				if( rs != null )
	      				{
	      					rs.close();
	      				}
	      			}
	      			catch (SQLException e2)
	      			{
	      				System.out.println("[�ݱ� ����]\n" +  e2.getStackTrace());
	      			}
	      		}
				
	      	}
	      });
	      btnSearch.setFont(new Font("����", Font.PLAIN, 16));
	      btnSearch.setBounds(201, 166, 86, 36);
	      getContentPane().add(btnSearch);
	      setSize(new Dimension(355, 329));
	      setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new memberSearch();
	}
}
