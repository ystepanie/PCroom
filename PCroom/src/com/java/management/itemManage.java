package com.java.management;

import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import DBconn.DBConnection;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class itemManage extends JFrame {
	static JTable tblItem;
	static String colName[] = {"ID", "음식명", "가격", "재고"};
	static DefaultTableModel model = new DefaultTableModel(colName, 0) {
		   public boolean isCellEditable(int arg0, int arg1) {
			   if(arg1==0)
				   return false;
			   else
				   return true;
		   }
	   };
	String sql = "select foodid, foodname, foodprice, foodamount from food";
	Connection conn;
	PreparedStatement pr = null;
	ResultSet rs = null;
	JScrollPane scrollPane;

	itemManage() {
			setTitle("상품관리");
	      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	      Container c = getContentPane();
	      c.setLayout(new BorderLayout(0, 0));
	      
	      JPanel panel = new JPanel();
	      panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
	      c.add(panel, BorderLayout.SOUTH);
	      
	      JButton btnCancel = new JButton("\uD655\uC778");
	      btnCancel.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		model.setNumRows(0);
	      		dispose();
	      	}
	      });
	      btnCancel.setBackground(Color.LIGHT_GRAY);
	      btnCancel.setFont(new Font("굴림", Font.PLAIN, 16));
	      panel.add(btnCancel);
	      
	      JButton btnChange = new JButton("\uC7AC\uACE0\uC218\uC815");
	      btnChange.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		System.out.println(e.getActionCommand());
	      		DefaultTableModel model2 = (DefaultTableModel)tblItem.getModel();
	      		int row = tblItem.getSelectedRow();
	      		if(row <0) return;
	      		
	      		String query = "update food set foodname=?, foodprice=?, foodamount=? where foodid=?";
	      		
	      		try {
	      			conn = DBConnection.getConnection();
	      			pr = conn.prepareStatement(query);
	      			
	      			pr.setString(1, (String)model2.getValueAt(row, 1));
	      			pr.setString(2, (String)model2.getValueAt(row, 2));
	      			pr.setString(3, (String)model2.getValueAt(row, 3));
	      			pr.setString(4, (String)model2.getValueAt(row, 0));
	      			
	      			int cnt = pr.executeUpdate();
	      			
	      			JOptionPane aa = new JOptionPane();
	      			aa.showMessageDialog(null, "수정이 완료되었습니다.");
	      		} catch(Exception ee) {
	      			ee.printStackTrace();
	      		} finally {
	      			DBclose();
	      		}
	      		model2.setRowCount(0);
	      		select();
	      	}
	      });
	      btnChange.setBackground(Color.LIGHT_GRAY);
	      btnChange.setFont(new Font("굴림", Font.PLAIN, 16));
	      panel.add(btnChange);
	      
	      JButton btnSearch = new JButton("\uBB3C\uD488\uAC80\uC0C9");
	      btnSearch.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      		new itemSearch();
	      	}
	      });
	      btnSearch.setBackground(Color.LIGHT_GRAY);
	      btnSearch.setFont(new Font("굴림", Font.PLAIN, 16));
	      panel.add(btnSearch);
	      
	      tblItem = new JTable(model);
	      scrollPane = new JScrollPane(tblItem);
	      scrollPane.setSize(400,200);
	      c.add(scrollPane, BorderLayout.CENTER);
	      
	      addWindowListener(new WindowListener() {
	  		@Override
	  		public void windowClosing(WindowEvent e) {
	  			model.setRowCount(0);
	  		}
	  		@Override
	  		public void windowActivated(WindowEvent e) {}
	  		@Override
	  		public void windowClosed(WindowEvent e) {}
	  		@Override
	  		public void windowDeactivated(WindowEvent e) {}
	  		@Override
	  		public void windowDeiconified(WindowEvent e) {}
	  		@Override
	  		public void windowIconified(WindowEvent e) {}
	  		@Override
	  		public void windowOpened(WindowEvent e) {}
	  	});
	      setSize(740,433);
	      select();
	      setVisible(true);
	}
	
	public void DBclose() {
		   try {
	           rs.close();
	           pr.close();
	           conn.close();
	        } catch (Exception e2) {
	           
	        }
	   }
	
	 private void select() {
		   try {
		         conn = DBConnection.getConnection();
		         pr = conn.prepareStatement(sql);
		         rs = pr.executeQuery();
		         
		         while(rs.next()) {
		            model.addRow(new Object[] {rs.getString("foodid"),rs.getString("foodname"),rs.getString("foodprice"),rs.getString("foodamount")});
		         }
		      } catch (Exception e) {
		         e.printStackTrace();
		      } finally {
		    	  DBclose();
		      }
	   }
}
