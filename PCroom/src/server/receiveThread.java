package server;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.java.management.*;

import DBconn.DBConnection;

public class receiveThread extends Thread{

	private Socket socket;

	sendingMessage frameMessage;
	ArrayList<JPanel> comList;
	ArrayList<JLabel> idList;
	ArrayList<JLabel> timeList;
	ArrayList<JLabel> cleaningList;
	ArrayList<JLabel> foodList;
	ArrayList<JLabel> messageList;
	ArrayList<JLabel> minorList;
	Timer timer = new Timer();
	
	public receiveThread(ArrayList<JPanel> comList, ArrayList<JLabel> idList, ArrayList<JLabel> timeList, ArrayList<JLabel> cleaningList, ArrayList<JLabel> foodList, ArrayList<JLabel> messageList, ArrayList<JLabel> minorList) {
		this.comList = comList;
		this.idList = idList;
		this.timeList = timeList;
		this.cleaningList = cleaningList;
		this.foodList = foodList;
		this.messageList = messageList;
		this.minorList = minorList;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String receiveString;
			
			while(true) {
				receiveString = tmpbuf.readLine();
				if(receiveString == null) {
					System.out.println("상대방 연결이 끊겼습니다.");
					break;
				}else {
					System.out.println("클라이언트 : " + receiveString);
					String[] strings = receiveString.split("//");
					int function = Integer.parseInt(strings[0]);
					int seatNum = Integer.parseInt(strings[1]);
					String userID = strings[2];
					if(function == 0) {
						comList.get((seatNum-1)).setBackground(Color.yellow);
						idList.get((seatNum-1)).setText(userID);
					
						Connection conn = null;
						PreparedStatement pstm = null;
						ResultSet rs = null;
					
						try {
							String quary = "Select PCTIME, BIRTH from MEMBER where id = '" + userID + "'";
						
							conn = DBConnection.getConnection();
							pstm = conn.prepareStatement(quary);
							rs = pstm.executeQuery();
						
							int pctime = 0;
							int birth = 0;
						
							while(rs.next()) {
								birth = rs.getDate("birth").getYear();
							}
							System.out.println(Integer.parseInt(strings[3])+"분");
							int hour = Integer.parseInt(strings[3])/60;
							int minute = Integer.parseInt(strings[3])%60;
							String sHou, sMin;
							sHou = (hour<10 ?"0"+hour : hour+"");
							sMin = (minute<10 ? "0"+minute : minute+"");
							timeList.get((seatNum-1)).setText(sHou +":"+ sMin);
							System.out.println(sHou +":"+ sMin);
							System.out.println(birth);
							if(birth >= 101) {
								minorList.get((seatNum-1)).setVisible(true);
							}
						}catch(SQLException sqle) {
							System.out.println("SELECT문에서 예외 발생");
							sqle.printStackTrace();
						}finally {
							try {
								if (rs != null) {rs.close();}
								if (pstm != null) {pstm.close();}
								if (conn != null) {conn.close();}
							}catch(Exception e) {
							}
						}
					}else if(function == 1) {
						try {
							File f = new File("chat"+userID+".txt");
							if(f.exists() == false) {
								BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));
								String str = userID + " : " + strings[3] + "\r\n";
								buffWrite.write(str, 0, str.length());
								buffWrite.flush();
								buffWrite.close();
							}else {
							String fileText = ReadFileText(f);
							BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));
							String Text;
							Text = fileText + userID + " : " + strings[3] + "\r\n";
							buffWrite.write(Text, 0, Text.length());
							buffWrite.flush();
							buffWrite.close();
							if(frameMessage != null) {
								frameMessage.dispose();
								frameMessage = new sendingMessage(seatNum, userID, socket);
							}
							}
						}catch(Exception e) {
							e.printStackTrace();
						}
						messageList.get((seatNum-1)).setVisible(true);
						
					}
					else if(function == 2) {
						try{
			                //파일 객체 생성
			                File file = new File(seatNum+" 번 좌석 주문.txt");
			                 //입력 스트림 생성
			                if(file.exists() == false) {
								BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));
								buffWrite.write(strings[3], 0, strings[3].length());
								buffWrite.flush();
								buffWrite.close();
							}else {
								String fileText = ReadFileText(file);
								BufferedWriter buffWrite = new BufferedWriter(new FileWriter(file));
								String Text;
								Text = fileText + "\r\n" + strings[3];
								buffWrite.write(Text, 0, Text.length());
								buffWrite.flush();
								buffWrite.close();
							}
			                }catch (FileNotFoundException e1) {
			                    e1.getStackTrace();
			                }catch(IOException e2){
			                    e2.getStackTrace();
			                }
					foodList.get((seatNum-1)).setVisible(true);
					foodList.get((seatNum-1)).addMouseListener(new MouseListener() {
						
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
							new orderCheck(seatNum);
							foodList.get((seatNum-1)).setVisible(false);
						}
						});
					}else if(function == -1) {
						comList.get((seatNum-1)).setBackground(new Color(0xB2CCFF));
						idList.get((seatNum-1)).setText("");
						timeList.get((seatNum-1)).setText("");
						cleaningList.get((seatNum-1)).setVisible(true);
						cleaningList.get((seatNum-1)).addMouseListener(new MouseListener() {
							
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
								cleaningList.get((seatNum-1)).setVisible(false);
							}
						});
					}
			        if(function == 0) {
			        int delay = 1000;
			        int period = 1000;
					timer.scheduleAtFixedRate(new TimerTask() {
						
						@Override
						public void run() {
							String[] strTime = timeList.get((seatNum-1)).getText().split(":");
							if(Integer.parseInt(strTime[0]) + Integer.parseInt(strTime[1]) == 0) {
								timer.cancel();
							}else {
								if((int)Integer.parseInt(strTime[1]) != 0) {
									int minute = Integer.parseInt(strTime[1]) - 1;
									String sMin = (minute<10 ? "0"+minute : minute+"");
									timeList.get((seatNum-1)).setText(strTime[0] +":"+ sMin);
								}else {
									int hour = Integer.parseInt(strTime[0]) - 1;
									String sHou = (hour<10 ? "0"+hour : hour+"");
									timeList.get((seatNum-1)).setText(sHou +":59");
								}
							}
						}
					}, delay, period);
				}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	private String ReadFileText(File file) {
		String strText = "";
		int nBuffer;
		try {
			BufferedReader buffRead = new BufferedReader(new FileReader(file));
			while((nBuffer = buffRead.read()) != -1) {
				strText += (char)nBuffer;
			}
			buffRead.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return strText;
	}
}
