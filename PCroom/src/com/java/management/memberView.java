package com.java.management;

import java.awt.Container;
import java.awt.Frame;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DBconn.DBConnection;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

import javax.naming.ReferralException;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class memberView extends JFrame {

   JScrollPane scroll;
   PreparedStatement pr = null;
   ResultSet rs = null;
   String sql = "select id, name, birth, pctime from member";
   static String colName[] = {"아이디", "이름", "생년월일", "남은시간"};
   static DefaultTableModel model = new DefaultTableModel(colName, 0) {
	   public boolean isCellEditable(int arg0, int arg1) {
		   if(arg1==0)
			   return false;
		   else
			   return true;
	   }
   };
   static JTable tbl;
   Connection conn;
   
   public memberView() {
      
      setTitle("회원관리");
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      Container c = getContentPane();
      
      JPanel btnPanel = new JPanel();
      getContentPane().add(btnPanel, BorderLayout.SOUTH);
      
      
      JButton btnOk = new JButton("\uD655 \uC778");
      btnOk.setFont(new Font("굴림", Font.PLAIN, 16));
      btnOk.setBackground(Color.LIGHT_GRAY);
      btnOk.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		model.setNumRows(0);
      		dispose();
      	}
      });
      btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
      btnPanel.add(btnOk);
      
      JButton btnEdit = new JButton("\uC218 \uC815");
      btnEdit.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		System.out.println(e.getActionCommand());
      		DefaultTableModel model2 = (DefaultTableModel)tbl.getModel();
      		int row = tbl.getSelectedRow();
      		if(row <0) return;
      		String query = "update member set name=?, birth=?, pctime=? where id=?";
      		SimpleDateFormat transFormat = new SimpleDateFormat("yy-MM-dd");
      		try {
      			conn = DBConnection.getConnection();
      			pr = conn.prepareStatement(query);
      			
      			pr.setString(1, (String)model2.getValueAt(row, 1));
      			pr.setDate(2, Date.valueOf((String)model2.getValueAt(row, 2)));
      			pr.setInt(3, (int)model2.getValueAt(row, 3));
      			pr.setString(4, (String)model2.getValueAt(row, 0));
      			pr.executeUpdate();
      			
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
      btnEdit.setFont(new Font("굴림", Font.PLAIN, 16));
      btnEdit.setBackground(Color.LIGHT_GRAY);
      btnPanel.add(btnEdit);
      
      JButton btnSearch = new JButton("\uAC80 \uC0C9");
      btnSearch.setBackground(Color.LIGHT_GRAY);
      btnSearch.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent arg0) {
      		new memberSearch();
      	}
      });
      btnSearch.setFont(new Font("굴림", Font.PLAIN, 16));
      btnPanel.add(btnSearch);
      
      tbl = new JTable(model);
      tbl.addMouseListener(new tblMouseListener());
      scroll = new JScrollPane(tbl);
      getContentPane().add(scroll, BorderLayout.CENTER);
      scroll.setSize(400,200);
      
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
   private class tblMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		JTable tbl = (JTable)e.getSource();
		int row = tbl.getSelectedRow();
		int col = tbl.getSelectedColumn();
		
		System.out.println(colName[col]+" : "+model.getValueAt(row, col));
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	   
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
	         SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd", Locale.KOREA );
	         while(rs.next()) {
	            model.addRow(new Object[] {rs.getString("id"),rs.getString("name"), formatter.format(rs.getDate("birth")),rs.getInt("pctime")});
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	    	  DBclose();
	      }
   }
   
}
   
