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
    String url = "jdbc:oracle:thin:@localhost:1521:xe"; // ����Ŭ ��Ʈ��ȣ1521/@���Ŀ��� IP�ּ�
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
        			System.out.println(rs.getInt("pctime")+"��");
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
    	System.out.println("��������");
    	ee.printStackTrace();
    }
    return result;
    }
    
    public int addOrder(String computer, String orderdate, String foodname) {
    	
    	int result = 1;
    	try {
    		Class.forName("oracle.jdbc.driver.OracleDriver");
			cnn = DriverManager.getConnection(url, dbId, dbPw); // ������ ������ �������ִ� ����̹��Ŵ����� ������
            stmt = cnn.createStatement();
            
            sql = "select foodid from food where foodname ='"+foodname+"'";
        	rs = stmt.executeQuery(sql);
        	int foodid;
        	if(rs.next() == true) {
        		foodid = rs.getInt("foodid");
                sql = "insert into foodorder(computer, foodid, orderdate) values (" + computer + "," + foodid + ",'"
                        + orderdate +"')";
        		stmt.executeUpdate(sql);
        		JOptionPane.showMessageDialog(null, "�ֹ��� �Ϸ�Ǿ����ϴ�.");
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
