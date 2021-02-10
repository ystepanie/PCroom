package DBconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection dbConn;
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			String user = "chanjin";
			String pw = "1234";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, pw);
			
			System.out.println("Database�� ����Ǿ����ϴ�");
		}catch(ClassNotFoundException cnfe) {
			System.out.println("DB ����̹� �ε� ���� : " + cnfe.toString());
		}catch(SQLException sqle) {
			System.out.println("DB ���ӽ��� : " + sqle.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}