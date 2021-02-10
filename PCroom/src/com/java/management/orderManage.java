package com.java.management;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import DBconn.DBConnection;
import DBconn.DBLogin;
import client.receiveThread;
import client.sendThread;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

public class orderManage extends JFrame {
   
	private Socket socket;
    private BufferedWriter bw;
    OutputStream os = null;
    OutputStreamWriter osw =null;
	
   JButton btnOrder;
   JPanel p2,p1;
   private JTable tblMenu;
   ImageIcon[] drink = {
         new ImageIcon("pcfood/drink1.png"),
         new ImageIcon("pcfood/drink2.png"),
         new ImageIcon("pcfood/drink3.png"),
   };
   ImageIcon[] snack = {
      new ImageIcon("pcfood/snack1.png"),
      new ImageIcon("pcfood/snack2.png"),
      new ImageIcon("pcfood/snack3.png"),
   };
   ImageIcon[] ramyun = {
         new ImageIcon("pcfood/ramyun1.png"),
         new ImageIcon("pcfood/ramyun2.png"),
         new ImageIcon("pcfood/ramyun3.png"),
   };
   JLabel lblDrink1 = new JLabel(drink[0]);
   JLabel lblDrink2 = new JLabel(drink[1]);
   JLabel lblDrink3 = new JLabel(drink[2]);
   JLabel lblSnack1 = new JLabel(snack[0]);
   JLabel lblSnack2 = new JLabel(snack[1]);
   JLabel lblSnack3 = new JLabel(snack[2]);
   JLabel lblRamyun1 = new JLabel(ramyun[0]);
   JLabel lblRamyun2 = new JLabel(ramyun[1]);
   JLabel lblRamyun3 = new JLabel(ramyun[2]);
   JButton btnPlus1 = new JButton("+");
   JButton btnMinus1 = new JButton("-");
   JButton btnMinus2 = new JButton("-");
   JButton btnPlus2 = new JButton("+");
   JButton btnMinus3 = new JButton("-");
   JButton btnPlus3 = new JButton("+");
   JButton btnPlus4 = new JButton("+");
   JButton btnMinus4 = new JButton("-");
   JButton btnMinus5 = new JButton("-");
   JButton btnPlus5 = new JButton("+");
   JButton btnMinus6 = new JButton("-");
   JButton btnPlus6 = new JButton("+");
   JButton btnPlus7 = new JButton("+");
   JButton btnMinus7 = new JButton("-");
   JButton btnMinus8 = new JButton("-");
   JButton btnPlus8 = new JButton("+");
   JButton btnMinus9 = new JButton("-");
   JButton btnPlus9 = new JButton("+");
   JLabel lblNumber1 = new JLabel();
   JLabel lblNumber2 = new JLabel();
   JLabel lblNumber3 = new JLabel();
   JLabel lblNumber4 = new JLabel();
   JLabel lblNumber5 = new JLabel();
   JLabel lblNumber6 = new JLabel();
   JLabel lblNumber7 = new JLabel();
   JLabel lblNumber8 = new JLabel();
   JLabel lblNumber9 = new JLabel();
   int a,b,c = 0;
   int a1,b1,c1 = 0;
   int a2,b2,c2 = 0;
   int sum = 0;
   
   JLabel lblNumber1_price = new JLabel();
   JLabel lblNumber2_price = new JLabel();
   JLabel lblNumber3_price = new JLabel();
   JLabel lblNumber4_price = new JLabel();
   JLabel lblNumber5_price = new JLabel();
   JLabel lblNumber6_price = new JLabel();
   JLabel lblNumber7_price = new JLabel();
   JLabel lblNumber8_price = new JLabel();
   JLabel lblNumber9_price = new JLabel();
   
   String header[] = {"상품이름", "수량", "가격"};
   DefaultTableModel model = new DefaultTableModel(header, 0) {
	   @Override

		public boolean isCellEditable(int row, int column) {  //수정, 입력 불가

			return false;

		}
   };
   
   Connection conn = null;
   PreparedStatement pstmt = null;
   ResultSet rs = null;
   Statement stmt = null;
   String ordername = null;
   int orderprice = 0;
   
   
   public orderManage() {
      JFrame f = new JFrame();
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.setTitle("주문창");
      f.setLocation(1300, 0);
      Container d = f.getContentPane();
      f.getContentPane().setLayout(null);
      p1 = new JPanel();
      p1.setBounds(0, 0, 612, 956);
      p1.setLayout(null);
      d.add(p1);
      
      btnOrder = new JButton("\uC8FC\uBB38\uBC1B\uAE30");
      btnOrder.setBounds(364, 146, 216, 85);
      btnOrder.setPreferredSize(new Dimension(250, 100));
      p2 = new JPanel();
      p2.setBounds(2, 716, 606, 239);
      p1.add(p2);
      p2.setLayout(null);
      p2.add(btnOrder);
      
      
   
      
      
      JLabel lblPrice = new JLabel("총액");
      lblPrice.setBounds(365, 86, 57, 29);
      p2.add(lblPrice);
      lblPrice.setFont(new Font("굴림", Font.BOLD, 24));
      
      JLabel lblwon = new JLabel(sum+"원", SwingConstants.RIGHT);
      lblwon.setBounds(491, 90, 100, 24);
      p2.add(lblwon);
      lblwon.setFont(new Font("굴림", Font.PLAIN, 20));
      
      
      tblMenu = new JTable(model);
      JScrollPane jScrollPane = new JScrollPane(tblMenu);
      jScrollPane.setBounds(12, 50, 344, 179);
      p2.add(jScrollPane);
      
      JComboBox cboxOrderway = new JComboBox(new DefaultComboBoxModel(new String[] {"1\uBC88", "2\uBC88", "3\uBC88", "4\uBC88", "5\uBC88", "6\uBC88", "7\uBC88", "8\uBC88", "9\uBC88", "10\uBC88", "11\uBC88", "12\uBC88", "13\uBC88", "14\uBC88", "15\uBC88"}));
      cboxOrderway.setToolTipText("");
      cboxOrderway.setFont(new Font("굴림", Font.PLAIN, 12));
      cboxOrderway.setBounds(366, 47, 216, 29);
      p2.add(cboxOrderway);
      
      JLabel lblOrderway = new JLabel("\uC790\uB9AC\uBC88\uD638");
      lblOrderway.setFont(new Font("굴림", Font.BOLD, 18));
      lblOrderway.setBounds(366, 14, 124, 29);
      p2.add(lblOrderway);
      
      JLabel lblMenu = new JLabel("고른 메뉴");
      lblMenu.setFont(new Font("굴림", Font.BOLD, 18));
      lblMenu.setBounds(12, 10, 124, 29);
      p2.add(lblMenu);
      
      JPanel p3 = new JPanel();
      p3.setLayout(null);
      p3.setBounds(12, 10, 170, 485);
      p1.add(p3);
      
      JButton btnDrink = new JButton("음료수");
      btnDrink.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            lblDrink1.setVisible(true);
            lblDrink2.setVisible(true);
            lblDrink3.setVisible(true);
            btnPlus1.setVisible(true);
            btnMinus1.setVisible(false);
            btnPlus2.setVisible(true);
            btnMinus2.setVisible(false);
            btnPlus3.setVisible(true);
            btnMinus3.setVisible(false);
            lblNumber1.setVisible(true);
            lblNumber2.setVisible(true);
            lblNumber3.setVisible(true);
            lblNumber1_price.setVisible(true);
            lblNumber2_price.setVisible(true);
            lblNumber3_price.setVisible(true);
            
            lblSnack1.setVisible(false);
            lblSnack2.setVisible(false);
            lblSnack3.setVisible(false);
            btnPlus4.setVisible(false);
            btnMinus4.setVisible(false);
            btnPlus5.setVisible(false);
            btnMinus5.setVisible(false);
            btnPlus6.setVisible(false);
            btnMinus6.setVisible(false);
            lblNumber4.setVisible(false);
            lblNumber5.setVisible(false);
            lblNumber6.setVisible(false);
            lblNumber4_price.setVisible(false);
            lblNumber5_price.setVisible(false);
            lblNumber6_price.setVisible(false);
            
            lblRamyun1.setVisible(false);
            lblRamyun2.setVisible(false);
            lblRamyun3.setVisible(false);
            btnPlus7.setVisible(false);
            btnMinus7.setVisible(false);
            btnPlus8.setVisible(false);
            btnMinus8.setVisible(false);
            btnPlus9.setVisible(false);
            btnMinus9.setVisible(false);
            lblNumber7.setVisible(false);
            lblNumber8.setVisible(false);
            lblNumber9.setVisible(false);
            lblNumber7_price.setVisible(false);
            lblNumber8_price.setVisible(false);
            lblNumber9_price.setVisible(false);
         }      
      });
      
      
      JButton btnSnack = new JButton("과자");
      btnSnack.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            lblDrink1.setVisible(false);
            lblDrink2.setVisible(false);
            lblDrink3.setVisible(false);
            btnPlus1.setVisible(false);
            btnMinus1.setVisible(false);
            btnPlus2.setVisible(false);
            btnMinus2.setVisible(false);
            btnPlus3.setVisible(false);
            btnMinus3.setVisible(false);
            lblNumber1.setVisible(false);
            lblNumber2.setVisible(false);
            lblNumber3.setVisible(false);
            lblNumber1_price.setVisible(false);
            lblNumber2_price.setVisible(false);
            lblNumber3_price.setVisible(false);
            
            lblSnack1.setVisible(true);
            lblSnack2.setVisible(true);
            lblSnack3.setVisible(true);
            btnPlus4.setVisible(true);
            btnMinus4.setVisible(false);
            btnPlus5.setVisible(true);
            btnMinus5.setVisible(false);
            btnPlus6.setVisible(true);
            btnMinus6.setVisible(false);
            lblNumber4.setVisible(true);
            lblNumber5.setVisible(true);
            lblNumber6.setVisible(true);
            lblNumber4_price.setVisible(true);
            lblNumber5_price.setVisible(true);
            lblNumber6_price.setVisible(true);
            
            lblRamyun1.setVisible(false);
            lblRamyun2.setVisible(false);
            lblRamyun3.setVisible(false);
            btnPlus7.setVisible(false);
            btnMinus7.setVisible(false);
            btnPlus8.setVisible(false);
            btnMinus8.setVisible(false);
            btnPlus9.setVisible(false);
            btnMinus9.setVisible(false);
            lblNumber7.setVisible(false);
            lblNumber8.setVisible(false);
            lblNumber9.setVisible(false);
            lblNumber7_price.setVisible(false);
            lblNumber8_price.setVisible(false);
            lblNumber9_price.setVisible(false);
         }
      });
      
      
      JButton btnRamyun = new JButton("라면");
      btnRamyun.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            lblDrink1.setVisible(false);
            lblDrink2.setVisible(false);
            lblDrink3.setVisible(false);
            btnPlus1.setVisible(false);
            btnMinus1.setVisible(false);
            btnPlus2.setVisible(false);
            btnMinus2.setVisible(false);
            btnPlus3.setVisible(false);
            btnMinus3.setVisible(false);
            lblNumber1.setVisible(false);
            lblNumber2.setVisible(false);
            lblNumber3.setVisible(false);
            lblNumber1_price.setVisible(false);
            lblNumber2_price.setVisible(false);
            lblNumber3_price.setVisible(false);
            
            
            lblSnack1.setVisible(false);
            lblSnack2.setVisible(false);
            lblSnack3.setVisible(false);
            btnPlus4.setVisible(false);
            btnMinus4.setVisible(false);
            btnPlus5.setVisible(false);
            btnMinus5.setVisible(false);
            btnPlus6.setVisible(false);
            btnMinus6.setVisible(false);
            lblNumber4.setVisible(false);
            lblNumber5.setVisible(false);
            lblNumber6.setVisible(false);
            lblNumber4_price.setVisible(false);
            lblNumber5_price.setVisible(false);
            lblNumber6_price.setVisible(false);
            
            lblRamyun1.setVisible(true);
            lblRamyun2.setVisible(true);
            lblRamyun3.setVisible(true);
            btnPlus7.setVisible(true);
            btnMinus7.setVisible(false);
            btnPlus8.setVisible(true);
            btnMinus8.setVisible(false);
            btnPlus9.setVisible(true);
            btnMinus9.setVisible(false);
            lblNumber7.setVisible(true);
            lblNumber8.setVisible(true);
            lblNumber9.setVisible(true);
            lblNumber7_price.setVisible(true);
            lblNumber8_price.setVisible(true);
            lblNumber9_price.setVisible(true);
         }
      });
      

      btnDrink.setFont(new Font("굴림", Font.PLAIN, 38));
      btnDrink.setBounds(0, 0, 168, 76);
      p3.add(btnDrink);
      
      btnSnack.setFont(new Font("굴림", Font.PLAIN, 38));
      btnSnack.setBounds(0, 72, 168, 76);
      p3.add(btnSnack);
      
      btnRamyun.setFont(new Font("굴림", Font.PLAIN, 38));
      btnRamyun.setBounds(0, 144, 168, 76);
      p3.add(btnRamyun);
      
      JPanel p4 = new JPanel();
      Dimension size = new Dimension();
      size.setSize(500,1000);
      p4.setPreferredSize(size);
      
      JScrollPane scrollPane = new JScrollPane(p4, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
      lblDrink1.setBounds(30, 31, 160, 133);
      lblDrink1.setBackground(Color.GRAY);
      lblSnack1.setBounds(30, 31, 160, 133);
      lblSnack1.setBackground(Color.GRAY);
      lblRamyun1.setBounds(30, 31, 160, 133);
      lblRamyun1.setBackground(Color.GRAY);
      lblNumber1_price = new JLabel("1500원");
      lblNumber1_price.setBounds(81, 200, 100, 100);
      lblNumber2_price = new JLabel("1500원");
      lblNumber2_price.setBounds(81, 460, 100, 100);
      lblNumber3_price = new JLabel("2000원");
      lblNumber3_price.setBounds(81, 720, 100, 100);
      lblNumber4_price = new JLabel("1200원");
      lblNumber4_price.setBounds(81, 200, 100, 100);
      lblNumber5_price = new JLabel("1300원");
      lblNumber5_price.setBounds(81, 460, 100, 100);
      lblNumber6_price = new JLabel("1100원");
      lblNumber6_price.setBounds(81, 720, 100, 100);
      lblNumber7_price = new JLabel("2300원");
      lblNumber7_price.setBounds(81, 200, 100, 100);
      lblNumber8_price = new JLabel("2500원");
      lblNumber8_price.setBounds(81, 460, 100, 100);
      lblNumber9_price = new JLabel("1700원");
      lblNumber9_price.setBounds(81, 720, 100, 100);
      p4.setLayout(null);
      
      p4.add(lblNumber1_price);
      p4.add(lblNumber2_price);
      p4.add(lblNumber3_price);
      p4.add(lblNumber4_price);
      p4.add(lblNumber5_price);
      p4.add(lblNumber6_price);
      p4.add(lblNumber7_price);
      p4.add(lblNumber8_price);
      p4.add(lblNumber9_price);
      
      p4.add(lblDrink1);
      p4.add(lblSnack1);
      p4.add(lblRamyun1);
      lblDrink2.setBounds(30, 283, 160, 133);
      
      lblDrink2.setBackground(Color.GRAY);
      lblSnack2.setBounds(30, 283, 160, 133);
      lblSnack2.setBackground(Color.GRAY);
      lblRamyun2.setBounds(30, 283, 160, 133);
      lblRamyun2.setBackground(Color.GRAY);
      
      p4.add(lblDrink2);
      p4.add(lblSnack2);
      p4.add(lblRamyun2);
      lblDrink3.setBounds(30, 535, 160, 133);
      
      lblDrink3.setBackground(Color.GRAY);
      lblSnack3.setBounds(30, 535, 160, 133);
      lblSnack3.setBackground(Color.GRAY);
      lblRamyun3.setBounds(30, 535, 160, 133);
      lblRamyun3.setBackground(Color.GRAY);
      
      p4.add(lblDrink3);
      p4.add(lblSnack3);
      p4.add(lblRamyun3);
      btnPlus1.setBounds(140, 174, 50, 50);
      
      btnPlus1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            
            for(int i=0;i<1;i++) {
               sum += 1500;
               lblwon.setText(sum+"원");
               a++;
               lblNumber1.setText(String.valueOf(a));
               if(a==0) {btnPlus1.setVisible(true);
               btnMinus1.setVisible(false);
               } else { btnPlus1.setVisible(true);
               btnMinus1.setVisible(true);
               if(a==1) {
               String inputStr[] = new String[] {"파워에이드", Integer.toString(a), Integer.toString(a*1500)};
               model.addRow(inputStr);
               }else {
                  for(int j = 0; j < model.getRowCount(); j++) {
                     String colName = model.getValueAt(j, 0).toString();
                     if(colName.equals("파워에이드")) {
                        model.setValueAt(Integer.toString(a), j, 1);
                        model.setValueAt(Integer.toString(a*1500), j, 2);
                        break;
                     }
                  }
               }
               }
               
            }
            
         }
      });
      p4.add(btnPlus1);
      btnPlus4.setBounds(140, 174, 50, 50);
      
      btnPlus4.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            
            for(int i=0;i<1;i++) {
               sum += 1200;
               lblwon.setText(sum+"원");
               a1++;
               lblNumber4.setText(String.valueOf(a1));
               if(a1==0) {btnPlus4.setVisible(true);
               btnMinus4.setVisible(false);
               } else { btnPlus4.setVisible(true);
               btnMinus4.setVisible(true);
               if(a1==1) {
                  String inputStr[] = new String[] {"포테토칩", Integer.toString(a1), Integer.toString(a1*1200)};
                  model.addRow(inputStr);
                  }else {
                     for(int j = 0; j < model.getRowCount(); j++) {
                        String colName = model.getValueAt(j, 0).toString();
                        if(colName.equals("포테토칩")) {
                           model.setValueAt(Integer.toString(a1), j, 1);
                           model.setValueAt(Integer.toString(a1*1200), j, 2);
                           break;
                        }
                     }
                  }
               }
               
            }
            
         }
      });
      p4.add(btnPlus4);
      btnPlus7.setBounds(140, 174, 50, 50);
      
      btnPlus7.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            
            for(int i=0;i<1;i++) {
               sum+=2300;
               lblwon.setText(sum+"원");
               a2++;
               lblNumber7.setText(String.valueOf(a2));
               if(a2==0) {btnPlus7.setVisible(true);
               btnMinus7.setVisible(false);
               } else { btnPlus7.setVisible(true);
               btnMinus7.setVisible(true);
               if(a2==1) {
                  String inputStr[] = new String[] {"신라면", Integer.toString(a2), Integer.toString(a2*2300)};
                  model.addRow(inputStr);
                  }else {
                     for(int j = 0; j < model.getRowCount(); j++) {
                        String colName = model.getValueAt(j, 0).toString();
                        if(colName.equals("신라면")) {
                           model.setValueAt(Integer.toString(a2), j, 1);
                           model.setValueAt(Integer.toString(a2*2300), j, 2);
                           break;
                        }
                     }
                  }
               }
               
            }
            
         }
      });
      p4.add(btnPlus7);
      btnMinus4.setBounds(30, 174, 50, 50);
      
      btnMinus4.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            
            for(int i=0;i<1;i++) {
               sum -= 1200;
               lblwon.setText(sum+"원");
               a1--;
               lblNumber4.setText(String.valueOf(a1));
               for(int j = 0; j < model.getRowCount(); j++) {
                  String colName = model.getValueAt(j, 0).toString();
                  if(colName.equals("포테토칩")) {
                     if(a1==0) {
                        btnPlus4.setVisible(true);
                        btnMinus4.setVisible(false);
                        model.removeRow(j);
                     } else {
                        btnPlus4.setVisible(true);
                        btnMinus4.setVisible(true);
                     model.setValueAt(Integer.toString(a1), j, 1);
                     model.setValueAt(Integer.toString(a1*1200), j, 2);
                     }
                     break;
                  }
               }
            }
         }
      });
      btnMinus1.setBounds(30, 174, 50, 50);
      p4.add(btnMinus1);
      
      btnMinus1.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            
            for(int i=0;i<1;i++) {
               sum -= 1500;
               lblwon.setText(sum+"원");
               a--;
               lblNumber1.setText(String.valueOf(a));
               for(int j = 0; j < model.getRowCount(); j++) {
                  String colName = model.getValueAt(j, 0).toString();
                  if(colName.equals("파워에이드")) {
                     if(a==0) {btnPlus1.setVisible(true);
                        btnMinus1.setVisible(false);
                        model.removeRow(j);
                     } else {
                        btnPlus1.setVisible(true);
                        btnMinus1.setVisible(true);
                        model.setValueAt(Integer.toString(a), j, 1);
                        model.setValueAt(Integer.toString(a*1500), j, 2);
                     }
                     break;
                  }
               }
            }
         }
      });
      btnMinus1.setVisible(false);
      p4.add(btnMinus4);
      btnMinus7.setBounds(30, 174, 50, 50);
      
      btnMinus7.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent arg0) {
            
            for(int i=0;i<1;i++) {
               sum -= 2300;
               lblwon.setText(sum+"원");
               a2--;
               lblNumber7.setText(String.valueOf(a2));
               for(int j = 0; j < model.getRowCount(); j++) {
                  String colName = model.getValueAt(j, 0).toString();
                  if(colName.equals("신라면")) {
                     if(a2==0) {
                        btnPlus7.setVisible(true);
                        btnMinus7.setVisible(false);
                        model.removeRow(j);
                        } else {
                           btnPlus7.setVisible(true);
                           btnMinus7.setVisible(true);
                     model.setValueAt(Integer.toString(a2), j, 1);
                     model.setValueAt(Integer.toString(a2*2300), j, 2);
                           }
                     break;
                  }
               }
            }
         }
      });
      p4.add(btnMinus7);
      btnMinus2.setBounds(30, 430, 50, 50);
      
      btnMinus2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum -= 1500;
               lblwon.setText(sum+"원");
               b--;
               lblNumber2.setText(String.valueOf(b));
               for(int j = 0; j < model.getRowCount(); j++) {
                  String colName = model.getValueAt(j, 0).toString();
                  if(colName.equals("게토레이")) {
                     if(b==0) {
                        btnPlus2.setVisible(true);
                        btnMinus2.setVisible(false);
                        model.removeRow(j);
                        } else {
                           btnPlus2.setVisible(true);
                           btnMinus2.setVisible(true);
                     model.setValueAt(Integer.toString(b), j, 1);
                     model.setValueAt(Integer.toString(b*1500), j, 2);
                           }
                     break;
                  }
               }
            }
         }
      });
      p4.add(btnMinus2);
      btnMinus5.setBounds(30, 430, 50, 50);
      
      btnMinus5.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum -= 1300;
               lblwon.setText(sum+"원");
               b1--;
               lblNumber5.setText(String.valueOf(b1));
               for(int j = 0; j < model.getRowCount(); j++) {
                  String colName = model.getValueAt(j, 0).toString();
                  if(colName.equals("썬")) {
                     if(b1==0) {
                        btnPlus5.setVisible(true);
                        btnMinus5.setVisible(false);
                        model.removeRow(j);
                        } else {
                           btnPlus5.setVisible(true);
                           btnMinus5.setVisible(true);
                     model.setValueAt(Integer.toString(b1), j, 1);
                     model.setValueAt(Integer.toString(b1*1300), j, 2);
                           }
                     break;
                  }
               }
            }
         }
      });
      p4.add(btnMinus5);
      btnMinus8.setBounds(30, 430, 50, 50);
      
      btnMinus8.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum -= 2500;
               lblwon.setText(sum+"원");
               b2--;
               lblNumber8.setText(String.valueOf(b2));
               for(int j = 0; j < model.getRowCount(); j++) {
                  String colName = model.getValueAt(j, 0).toString();
                  if(colName.equals("짜파게티")) {
                     if(b2==0) {
                        btnPlus8.setVisible(true);
                        btnMinus8.setVisible(false);
                        model.removeRow(j);
                        } else {
                           btnPlus8.setVisible(true);
                           btnMinus8.setVisible(true);
                     model.setValueAt(Integer.toString(b2), j, 1);
                     model.setValueAt(Integer.toString(b2*2500), j, 2);
                           }
                     break;
                  }
               }
            }
         }
      });
      p4.add(btnMinus8);
      btnPlus2.setBounds(140, 430, 50, 50);
      
      btnPlus2.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum += 1500;
               lblwon.setText(sum+"원");
               b++;
               lblNumber2.setText(String.valueOf(b));
               if(b==0) {btnPlus2.setVisible(true);
               btnMinus2.setVisible(false);
               } else { btnPlus2.setVisible(true);
               btnMinus2.setVisible(true);
               if(b==1) {
                  String inputStr[] = new String[] {"게토레이", Integer.toString(b), Integer.toString(b*1500)};
                  model.addRow(inputStr);
                  }else {
                     for(int j = 0; j < model.getRowCount(); j++) {
                        String colName = model.getValueAt(j, 0).toString();
                        if(colName.equals("게토레이")) {
                           model.setValueAt(Integer.toString(b), j, 1);
                           model.setValueAt(Integer.toString(b*1500), j, 2);
                           break;
                        }
                     }
                  }
               }
               
            }
         }
      });
      p4.add(btnPlus2);
      btnPlus5.setBounds(140, 430, 50, 50);
      
      btnPlus5.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum += 1300;
               lblwon.setText(sum+"원");
               b1++;
               lblNumber5.setText(String.valueOf(b1));
               if(b1==0) {btnPlus5.setVisible(true);
               btnMinus5.setVisible(false);
               } else { btnPlus5.setVisible(true);
               btnMinus5.setVisible(true);
               if(b1==1) {
                  String inputStr[] = new String[] {"썬", Integer.toString(b1), Integer.toString(b1*1300)};
                  model.addRow(inputStr);
                  }else {
                     for(int j = 0; j < model.getRowCount(); j++) {
                        String colName = model.getValueAt(j, 0).toString();
                        if(colName.equals("썬")) {
                           model.setValueAt(Integer.toString(b1), j, 1);
                           model.setValueAt(Integer.toString(b1*1300), j, 2);
                           break;
                        }
                     }
                  }
               }
            }
         }
      });
      p4.add(btnPlus5);
      btnPlus8.setBounds(140, 430, 50, 50);
      
      btnPlus8.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum += 2500;
               lblwon.setText(sum+"원");
               b2++;
               lblNumber8.setText(String.valueOf(b2));
               if(b2==0) {btnPlus8.setVisible(true);
               btnMinus8.setVisible(false);
               } else { btnPlus8.setVisible(true);
               btnMinus8.setVisible(true);
               if(b2==1) {
                  String inputStr[] = new String[] {"짜파게티", Integer.toString(b2), Integer.toString(b2*2500)};
                  model.addRow(inputStr);
                  }else {
                     for(int j = 0; j < model.getRowCount(); j++) {
                        String colName = model.getValueAt(j, 0).toString();
                        if(colName.equals("짜파게티")) {
                           model.setValueAt(Integer.toString(b2), j, 1);
                           model.setValueAt(Integer.toString(b2*2500), j, 2);
                           break;
                        }
                     }
                  }
               }
            }
         }
      });
      p4.add(btnPlus8);
      btnMinus3.setBounds(30, 686, 50, 50);
      
      btnMinus3.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum -= 2000;
               lblwon.setText(sum+"원");
               c--;
               lblNumber3.setText(String.valueOf(c));
               for(int j = 0; j < model.getRowCount(); j++) {
                  String colName = model.getValueAt(j, 0).toString();
                  if(colName.equals("코카콜라")) {
                     if(c==0) {
                        btnPlus3.setVisible(true);
                        btnMinus3.setVisible(false);
                        model.removeRow(j);
                        } else {
                           btnPlus3.setVisible(true);
                           btnMinus3.setVisible(true);
                     model.setValueAt(Integer.toString(c), j, 1);
                     model.setValueAt(Integer.toString(c*2000), j, 2);
                           }
                     break;
                  }
               }
            }
         }
      });
      p4.add(btnMinus3);
      btnMinus6.setBounds(30, 686, 50, 50);
      
      btnMinus6.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum -= 1100;
               lblwon.setText(sum+"원");
               c1--;
               lblNumber6.setText(String.valueOf(c1));
               for(int j = 0; j < model.getRowCount(); j++) {
                  String colName = model.getValueAt(j, 0).toString();
                  if(colName.equals("오징어땅콩")) {
                     if(c1==0) {
                        btnPlus6.setVisible(true);
                        btnMinus6.setVisible(false);
                        model.removeRow(j);
                        } else {
                           btnPlus6.setVisible(true);
                           btnMinus6.setVisible(true);
                     model.setValueAt(Integer.toString(c1), j, 1);
                     model.setValueAt(Integer.toString(c1*1100), j, 2);
                           }
                     break;
                  }
               }
            }
         }
      });
      p4.add(btnMinus6);
      btnMinus9.setBounds(30, 686, 50, 50);
      
      btnMinus9.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum -= 1700;
               lblwon.setText(sum+"원");
               c2--;
               lblNumber9.setText(String.valueOf(c2));
               for(int j = 0; j < model.getRowCount(); j++) {
                  String colName = model.getValueAt(j, 0).toString();
                  if(colName.equals("불닭볶음면")) {
                     if(c2==0) {
                        btnPlus9.setVisible(true);
                        btnMinus9.setVisible(false);
                        model.removeRow(j);
                        } else {
                           btnPlus9.setVisible(true);
                           btnMinus9.setVisible(true);
                     model.setValueAt(Integer.toString(c2), j, 1);
                     model.setValueAt(Integer.toString(c2*1700), j, 2);
                           }
                     break;
                  }
               }
            }
         }
      });
      p4.add(btnMinus9);
      btnPlus3.setBounds(140, 686, 50, 50);
      
      btnPlus3.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum += 2000;
               lblwon.setText(sum+"원");
               c++;
               lblNumber3.setText(String.valueOf(c));
               if(c==0) {btnPlus3.setVisible(true);
               btnMinus3.setVisible(false);
               } else { btnPlus3.setVisible(true);
               btnMinus3.setVisible(true);
               if(c==1) {
                  String inputStr[] = new String[] {"코카콜라", Integer.toString(c), Integer.toString(c*2000)};
                  model.addRow(inputStr);
                  }else {
                     for(int j = 0; j < model.getRowCount(); j++) {
                        String colName = model.getValueAt(j, 0).toString();
                        if(colName.equals("코카콜라")) {
                           model.setValueAt(Integer.toString(c), j, 1);
                           model.setValueAt(Integer.toString(c*2000), j, 2);
                           break;
                        }
                     }
                  }
               }
            }
         }
      });
      p4.add(btnPlus3);
      btnPlus6.setBounds(140, 686, 50, 50);
      
      btnPlus6.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum += 1100;
               lblwon.setText(sum+"원");
               c1++;
               lblNumber6.setText(String.valueOf(c1));
               if(c1==0) {btnPlus6.setVisible(true);
               btnMinus6.setVisible(false);
               } else { btnPlus6.setVisible(true);
               btnMinus6.setVisible(true);
               if(c1==1) {
                  String inputStr[] = new String[] {"오징어땅콩", Integer.toString(c1), Integer.toString(c1*1100)};
                  model.addRow(inputStr);
                  }else {
                     for(int j = 0; j < model.getRowCount(); j++) {
                        String colName = model.getValueAt(j, 0).toString();
                        if(colName.equals("오징어땅콩")) {
                           model.setValueAt(Integer.toString(c1), j, 1);
                           model.setValueAt(Integer.toString(c1*1100), j, 2);
                           break;
                        }
                     }
                  }
               }
            }
         }
      });
      p4.add(btnPlus6);
      btnPlus9.setBounds(140, 686, 50, 50);
      
      btnPlus9.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            for(int i=0;i<1;i++) {
               sum += 1700;
               lblwon.setText(sum+"원");
               c2++;
               lblNumber9.setText(String.valueOf(c2));
               if(c2==0) {btnPlus9.setVisible(true);
               btnMinus9.setVisible(false);
               } else { btnPlus9.setVisible(true);
               btnMinus9.setVisible(true);
               if(c2==1) {
                  String inputStr[] = new String[] {"불닭볶음면", Integer.toString(c2), Integer.toString(c2*1700)};
                  model.addRow(inputStr);
                  }else {
                     for(int j = 0; j < model.getRowCount(); j++) {
                        String colName = model.getValueAt(j, 0).toString();
                        if(colName.equals("불닭볶음면")) {
                           model.setValueAt(Integer.toString(c2), j, 1);
                           model.setValueAt(Integer.toString(c2*1700), j, 2);
                           break;
                        }
                     }
                  }
               }
            }
         }
      });
      p4.add(btnPlus9);
      lblNumber1.setBounds(104, 174, 24, 50);
      
      lblNumber1.setFont(new Font("굴림", Font.PLAIN, 16));
      
      p4.add(lblNumber1);
      lblNumber2.setBounds(104, 435, 24, 50);
      lblNumber2.setFont(new Font("굴림", Font.PLAIN, 16));
      
      p4.add(lblNumber2);
      lblNumber3.setBounds(104, 696, 31, 50);
      lblNumber3.setFont(new Font("굴림", Font.PLAIN, 16));
      
      p4.add(lblNumber3);
      lblNumber4.setBounds(104, 174, 24, 50);
      
      lblNumber4.setFont(new Font("굴림", Font.PLAIN, 16));
      
      p4.add(lblNumber4);
      lblNumber5.setBounds(104, 435, 24, 50);
      lblNumber5.setFont(new Font("굴림", Font.PLAIN, 16));
      
      p4.add(lblNumber5);
      lblNumber6.setBounds(104, 696, 31, 50);
      lblNumber6.setFont(new Font("굴림", Font.PLAIN, 16));
      
      p4.add(lblNumber6);
      lblNumber7.setBounds(104, 174, 24, 50);
      
      lblNumber7.setFont(new Font("굴림", Font.PLAIN, 16));
      
      p4.add(lblNumber7);
      lblNumber8.setBounds(104, 435, 24, 50);
      lblNumber8.setFont(new Font("굴림", Font.PLAIN, 16));
      
      p4.add(lblNumber8);
      lblNumber9.setBounds(104, 696, 31, 50);
      lblNumber9.setFont(new Font("굴림", Font.PLAIN, 16));
      
      p4.add(lblNumber9);
      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
      scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      scrollPane.setBounds(193, 10, 415, 618);
      
      lblDrink1.setVisible(false);
      lblDrink2.setVisible(false);
      lblDrink3.setVisible(false);
      btnPlus1.setVisible(false);
      btnPlus2.setVisible(false);
      btnMinus2.setVisible(false);
      btnPlus3.setVisible(false);
      btnMinus3.setVisible(false);
      lblNumber1.setVisible(false);
      lblNumber2.setVisible(false);
      lblNumber3.setVisible(false);
      lblNumber1_price.setVisible(false);
      lblNumber2_price.setVisible(false);
      lblNumber3_price.setVisible(false);
      
      lblSnack1.setVisible(false);
      lblSnack2.setVisible(false);
      lblSnack3.setVisible(false);
      btnPlus4.setVisible(false);
      btnMinus4.setVisible(false);
      btnPlus5.setVisible(false);
      btnMinus5.setVisible(false);
      btnPlus6.setVisible(false);
      btnMinus6.setVisible(false);
      lblNumber4.setVisible(false);
      lblNumber5.setVisible(false);
      lblNumber6.setVisible(false);
      lblNumber4_price.setVisible(false);
      lblNumber5_price.setVisible(false);
      lblNumber6_price.setVisible(false);
      
      lblRamyun1.setVisible(false);
      lblRamyun2.setVisible(false);
      lblRamyun3.setVisible(false);
      btnPlus7.setVisible(false);
      btnMinus7.setVisible(false);
      btnPlus8.setVisible(false);
      btnMinus8.setVisible(false);
      btnPlus9.setVisible(false);
      btnMinus9.setVisible(false);
      lblNumber7.setVisible(false);
      lblNumber8.setVisible(false);
      lblNumber9.setVisible(false);
      lblNumber7_price.setVisible(false);
      lblNumber8_price.setVisible(false);
      lblNumber9_price.setVisible(false);
      
      p1.add(scrollPane);
      
      
      f.setSize(626,994);
      f.setVisible(true);
      

      btnOrder.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		for(int i=0;i<model.getRowCount();i++) {
  				try {
  				String query1 = "select * from food where foodname='"+model.getValueAt(i,0)+"'";
      			conn = DBConnection.getConnection();
				 pstmt = conn.prepareStatement(query1);
				 rs = pstmt.executeQuery();
		      	 while(rs.next()) {
		      	 ordername = (String)model.getValueAt(i, 0);
		      	 orderprice = Integer.parseInt((String) model.getValueAt(i, 2)); 
		      	 int cc = Integer.parseInt(rs.getString("foodamount"))-Integer.parseInt((String)model.getValueAt(i, 1));
		      	 System.out.println(cc);
		      	 String query2 = "update food set foodamount=? where foodname=?";
		      	 pstmt = conn.prepareStatement(query2);
		      	 pstmt.setLong(1, cc);
		      	 pstmt.setString(2, (String)model.getValueAt(i, 0));

		      	int cnt = pstmt.executeUpdate(); 	
		 	   System.out.println(orderprice);
			   System.out.println(ordername);
		      	
		      	Calendar date = Calendar.getInstance();
		      	SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		      	String time = sim.format(date.getTime());
		      	System.out.println(time);
		      	String query3 = "insert into foodorder values ('"+cboxOrderway.getSelectedItem().toString()+"','"+ordername+"','"+sim.format(date.getTime())+"','"+orderprice+"')";
		      	
		      	stmt = conn.createStatement();
		      	stmt.executeUpdate(query3);
        		
		      	f.dispose();

		   		 }
  				}
				 catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
			    	  DBclose();
				}
  				
      		} 
      		JOptionPane.showMessageDialog(null, "주문이 완료되었습니다.");
			f.dispose();
			new orderManage();
      		
  		
			
      	}
      });
   }
	public void DBclose() {
		   try {
	           rs.close();
	           stmt.close();
	           pstmt.close();
	           conn.close();
	        } catch (Exception e2) {
	           
	        }
	   }
  public static void main(String[] args) {
	new orderManage();
}
}