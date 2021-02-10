package com.java.management;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DBconn.DBConnection;

import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class moneyManage extends JFrame {
	   static JTable tblmoney;
	   static String colName1[]= {"좌석번호","상품이름","주문한 시간","가격"};
	   static String colName2[]= {"사용자아이디", "사용금액", "사용 날짜"};
	   static DefaultTableModel model1 = new DefaultTableModel(colName1, 0);
	   static DefaultTableModel model2 = new DefaultTableModel(colName2, 0);
	   String sql = "select computer,foodname,orderdate,foodprice from foodorder";
	   String sql2 = "delete from foodorder";
	   String query = "select id, usagefee, usedate from computerhistory";
	   String query2 = "delete from computerhistory";
	   JTextField txtToday;
	   Connection conn = null;
	   Statement stmt = null;
	   PreparedStatement pr = null;
	   ResultSet rs = null;
	   JLabel lblWon;
	   int aa=0; 
	   public moneyManage() {
	      getContentPane().setLayout(new BorderLayout());
	      setTitle("매출관리");
	      
	      GridLayout grid = new GridLayout(1, 2, 5, 5);
	      JPanel panNorth = new JPanel(grid);
	      JPanel panCenter = new JPanel();
	      JPanel panEast = new JPanel();
	      getContentPane().add(panNorth, BorderLayout.NORTH);
	      getContentPane().add(panCenter, BorderLayout.CENTER);
	      getContentPane().add(panEast, BorderLayout.EAST);
	      
	      JButton btnOrder = new JButton("주문내역");
	      btnOrder.setBackground(Color.gray);
	      JButton btnCom = new JButton("컴퓨터사용내역");
	      btnCom.setBackground(Color.white);
	      
	      panNorth.add(btnOrder);
	      panNorth.add(btnCom);
	      
	      tblmoney = new JTable(model1);
	      tblmoney.setBounds(0, 254, 681, -253);
	      
	      JScrollPane scrollPane = new JScrollPane(tblmoney);
	      scrollPane.setBounds(0, 0, 681, 257);
	      panCenter.add(scrollPane);
	      
	      txtToday = new JTextField();
	      txtToday.setFont(new Font("굴림", Font.PLAIN, 16));
	      txtToday.setBounds(123, 308, 149, 34);
	      panEast.add(txtToday);
	      txtToday.setColumns(10);
	      
	      JLabel lblToday = new JLabel("\uC624\uB298\uB0A0\uC9DC :");
	      lblToday.setFont(new Font("굴림", Font.PLAIN, 16));
	      lblToday.setBounds(12, 316, 99, 15);
	      panEast.add(lblToday);
	      JButton btnSave = new JButton("\uD30C\uC77C\uB85C \uC800\uC7A5\uD558\uAE30");
	      
	      btnSave.setFont(new Font("굴림", Font.PLAIN, 13));
	      btnSave.setBounds(487, 304, 143, 42);
	      panEast.add(btnSave);
	      
	      JLabel lblSum = new JLabel("\uCD1D\uC561 :");
	      lblSum.setFont(new Font("굴림", Font.PLAIN, 16));
	      lblSum.setBounds(290, 318, 57, 15);
	      panEast.add(lblSum);
	      
	      lblWon = new JLabel("");
	      lblWon.setFont(new Font("굴림", Font.PLAIN, 16));
	      lblWon.setBounds(342, 316, 133, 23);
	      panEast.add(lblWon);
	      
	      selectOrder();
	      setSize(692,418);
	      setVisible(true);
	      btnSave.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            BufferedOutputStream bs = null;
	            try {
	               if(btnOrder.getBackground() == Color.gray) {               
	            bs = new BufferedOutputStream(new FileOutputStream("C:/주문매출기록표/"+txtToday.getText().toString()+".txt"));
	            String seatnum = null, foodname = null, orderdate = null, foodprice = null, messagesum = null;
	            for(int i=0;i<model1.getRowCount();i++) {
	                  seatnum = (String)model1.getValueAt(i, 0)+"\t\t";
	                  foodname = (String)model1.getValueAt(i, 1)+"\t\t";
	                  orderdate = (String)model1.getValueAt(i, 2)+"\t\t";
	                  foodprice = (String)model1.getValueAt(i, 3)+"\t\t";
	               messagesum = seatnum+foodname+orderdate+foodprice+"\n";
	               System.out.print(messagesum);
	               bs.write(messagesum.getBytes());
	               }}else {
	                  bs = new BufferedOutputStream(new FileOutputStream("C:/컴퓨터이용매출기록표/"+txtToday.getText().toString()+".txt"));
	                  String id = null, usefee = null, usedate = null, messagesum=null;
	                  for(int i=0;i<model2.getRowCount();i++) {
	                        id = (String)model2.getValueAt(i, 0)+"\t\t";
	                        usefee = (String)model2.getValueAt(i, 1)+"\t\t";
	                        usedate = (String)model2.getValueAt(i, 2)+"\t\t";
	                     messagesum = id+usefee+usedate+"\n";
	                     System.out.print(messagesum);
	                     bs.write(messagesum.getBytes());
	                     }
	               }
	            String chong = "총액 : "+Integer.toString(aa)+" 원";
	            bs.write(chong.getBytes());
	            conn = DBConnection.getConnection();
	            stmt = conn.createStatement();
	            int result = stmt.executeUpdate(sql2);
	            
	            if(result > 0) {System.out.println("db 값을 삭제하고 파일을 만들었습니다.");}
	            else {System.out.println("db 값을 삭제하지 못했습니다.");}
	            
	            JOptionPane.showMessageDialog(null, "금일 매출이 저장되었습니다.");
	            dispose();
	            } catch(Exception e2) {
	               e2.printStackTrace();
	            } finally {
	               try {
	                  bs.close();
	               } catch (IOException e1) {
	                  // TODO Auto-generated catch block
	                  e1.printStackTrace();
	               }
	            }
	            
	         }
	      });
	      btnOrder.addActionListener(new ActionListener() {
	         
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            panCenter.removeAll();
	            tblmoney = new JTable(model1);
	            JScrollPane scrollPane = new JScrollPane(tblmoney);
	            scrollPane.setBounds(0, 0, 681, 257);
	            panCenter.add(scrollPane);
	            selectOrder();
	            btnOrder.setBackground(Color.gray);
	            btnCom.setBackground(Color.white);
	         }
	      });
	      btnCom.addActionListener(new ActionListener() {
	         
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            // TODO Auto-generated method stub
	            panCenter.removeAll();
	            tblmoney = new JTable(model2);
	            JScrollPane scrollPane = new JScrollPane(tblmoney);
	            scrollPane.setBounds(0, 0, 681, 257);
	            panCenter.add(scrollPane);
	            selectCom();
	            btnOrder.setBackground(Color.white);
	            btnCom.setBackground(Color.gray);
	         }
	      });
	   }
	   
	   private void selectOrder() {
	         try {

	               conn = DBConnection.getConnection();
	               pr = conn.prepareStatement(sql);
	               rs = pr.executeQuery();
	               while(rs.next()) {
	                  model1.addRow(new Object[] {rs.getString("computer"),rs.getString("foodname"),rs.getString("orderdate"),rs.getInt("foodprice")});
	               }
	               for(int i=0;i<model1.getRowCount();i++) {
	                  aa += Integer.parseInt((String)model1.getValueAt(i, 3));
	               }
	               lblWon.setText(aa+" 원");
	               
	            } catch (Exception e) {
	               e.printStackTrace();
	            } finally {
	               DBclose();
	            }
	      }
	   private void selectCom() {
	         try {

	               conn = DBConnection.getConnection();
	               pr = conn.prepareStatement(query);
	               rs = pr.executeQuery();
	               while(rs.next()) {
	                  model2.addRow(new Object[] {rs.getString("ID"),rs.getInt("USAGEFEE"),rs.getString("USEDATE")});
	               }
	               for(int i=0;i<model2.getRowCount();i++) {
	                  aa += Integer.parseInt((String)model2.getValueAt(i, 3));
	               }
	               lblWon.setText(aa+" 원");
	               
	            } catch (Exception e) {
	               e.printStackTrace();
	            } finally {
	               DBclose();
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

	   public static void main(String[] args) {
	      // TODO Auto-generated method stub
	      new moneyManage();
	   }
	}