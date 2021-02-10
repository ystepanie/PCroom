package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;

import DBconn.DBConnection;

public class JFrameWindowClosingEventHandler extends WindowAdapter {
	
	Socket socket;
	int iSeat;
	String userID;
	JLabel TimeRemaining;
	int pctime;
	private BufferedWriter bw;
	
	public JFrameWindowClosingEventHandler(int iSeat, String userID, JLabel TimeRemaining, Socket socket) {
		this.socket = socket;
		this.iSeat = iSeat;
		this.userID = userID;
		this.TimeRemaining = TimeRemaining;
		try {
            OutputStream out = socket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void windowClosing(WindowEvent e) { 
		String[] strTime = TimeRemaining.getText().split(":");
		if(Integer.parseInt(strTime[0]) + Integer.parseInt(strTime[1]) == 0) {
			pctime=0;
		}else {
			pctime = Integer.parseInt(strTime[0])*60+ Integer.parseInt(strTime[1]);
		}
		System.out.println("windowClosing()");
		try {
        	bw.write("-1//"+iSeat+"//"+userID+"\n");
            bw.flush();
            bw.close();
            socket.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
		Connection conn = null;
		PreparedStatement pstm = null;
	
		try {
			String quary = "update MEMBER set pctime = ? where id = '"+userID+"'";
		
			conn = DBConnection.getConnection();
			pstm = conn.prepareStatement(quary);
			pstm.setInt(1, pctime);
			int result = pstm.executeUpdate();		//Update문 실행
			if(result >= 1)
				System.out.println("레코드 수정 성공");
			else
				System.out.println("레코드 수정 실패");
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try {
				pstm.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		//파일 객체 생성
		 try{
			 File file1 = new File("chat"+userID+".txt");
		 //입력 스트림 생성
			 if(file1.exists() == true) {
				 file1.delete();
			 }
		 }catch(Exception e1) {
			 e1.printStackTrace();
		 }
			JFrame frame = (JFrame)e.getWindow();
			frame.dispose();
	}
}