package DBconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DBLogin {
	String id = null;
    String pw = null;
    String name = null;
    String birth = null;
 
    Statement stmt = null;
    PreparedStatement psmt = null;
    ResultSet rs = null;
    String url = "jdbc:oracle:thin:@localhost:1521:xe"; // 오라클 포트번호1521/@이후에는 IP주소
    String driver = "oracle.jdbc.driver.OracleDriver";
    String sql = null;
    Connection cnn = null;
    String dbId = "chanjin";
    String dbPw = "1234";
    
    public DBLogin() {}

    public int checkIDPW(String id, String pw) {
    	
    	this.id = id;
    	this.pw = pw;
    	int result = 1;
    try {
        Class.forName(driver);
        cnn = DriverManager.getConnection(url, dbId, dbPw);
        stmt = cnn.createStatement();
        
        sql = "select * from member where id = '"+id+"'";
        rs = stmt.executeQuery(sql);
        if(rs.next() == false || (id.isEmpty()) == true) {
        	result = 1;
        } else {
        	sql = "select * from (select * from member where id='"+id+"')";
        	rs = stmt.executeQuery(sql);
        	while (rs.next() == true) {
        		if(rs.getString("password").equals(pw)) {
        			System.out.println(rs.getInt("pctime")+"분");
        			if(rs.getInt("pctime") <= 0)
        				result= 2;
        			else
        				result = Integer.parseInt((Integer.toString(rs.getInt("pctime"))+"0"));
        		} else {
        			result = 1;
        		}
        	}
        }dbClose();
    } catch (Exception ee) {
    	System.out.println("문제있음");
    	ee.printStackTrace();
    }
    return result;
    }
    
    public int addOrder(String computer, String orderdate, String foodname) {
    	
    	int result = 1;
    	try {
    		Class.forName("oracle.jdbc.driver.OracleDriver");
			cnn = DriverManager.getConnection(url, dbId, dbPw); // 연결할 정보를 가지고있는 드라이버매니저를 던진다
            stmt = cnn.createStatement();
            
            sql = "select foodid from food where foodname ='"+foodname+"'";
        	rs = stmt.executeQuery(sql);
        	int foodid;
        	if(rs.next() == true) {
        		foodid = rs.getInt("foodid");
                sql = "insert into foodorder(computer, foodid, orderdate) values (" + computer + "," + foodid + ",'"
                        + orderdate +"')";
        		stmt.executeUpdate(sql);
        		JOptionPane.showMessageDialog(null, "주문이 완료되었습니다.");
        	}
    		dbClose();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return result;
    }
    public void dbClose() {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (cnn != null)
                cnn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
	}
}
