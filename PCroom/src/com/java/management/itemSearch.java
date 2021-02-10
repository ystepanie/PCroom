package com.java.management;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import DBconn.DBConnection;

public class itemSearch extends JFrame {
	private JTextField txtName;
	private JButton btnSearch;
	Connection conn;
	java.sql.Statement stmt;
	ResultSet rs = null;
	itemSearch() {
			setTitle("재고검색");
	      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	      Container c = getContentPane();
	      c.setLayout(null);
	      
	      JLabel lblName = new JLabel("\uC74C\uC2DD\uC774\uB984 :");
	      lblName.setFont(new Font("굴림", Font.PLAIN, 16));
	      lblName.setBounds(12, 81, 75, 15);
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
	      			String query = "select foodid,foodname,foodprice,foodamount from food where foodname like '%" + txtName.getText().trim()+"%'";
	      			stmt = conn.createStatement();
	      			rs = stmt.executeQuery(query);
	      			
	      			ResultSetMetaData resultSetMetaData = rs.getMetaData();
	      			Object [] tempObject = new Object[resultSetMetaData.getColumnCount()];
	      			itemManage.model.setRowCount(0);
	      			
	      			while(rs.next()) {
	      				for(int i=0;i<resultSetMetaData.getColumnCount();i++) {
	      					tempObject[i] = rs.getString(i+1);
	      				}
	      				itemManage.model.addRow(tempObject);
	      			}
	      			if( itemManage.model.getRowCount() > 0 )
	    			{
	    				itemManage.tblItem.setRowSelectionInterval(0, 0); //첫째줄에 포커싱
	    			} else if(itemManage.model.getRowCount() == 0) {
	    				JOptionPane aa = new JOptionPane();
		      			aa.showMessageDialog(null, "검색된 이름이 없습니다.", "메세지", JOptionPane.ERROR_MESSAGE);
	    			}
	      			JOptionPane aa = new JOptionPane();
	      			aa.showMessageDialog(null, "검색이 완료되었습니다.");
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
	      				System.out.println("[닫기 오류]\n" +  e2.getStackTrace());
	      			}
	      		}
				
	      	}
	      });
	      btnSearch.setFont(new Font("굴림", Font.PLAIN, 16));
	      btnSearch.setBounds(201, 166, 86, 36);
	      getContentPane().add(btnSearch);
	      setSize(new Dimension(355, 329));
	      setVisible(true);
	      
	}
	public static void main(String[] args) {
		new itemSearch();
	}
}
