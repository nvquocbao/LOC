package model.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection openConnection() throws ClassNotFoundException,
			SQLException {		
		
		Connection conn = null;
        try {
//            String driver = "com.mysql.jdbc.Driver";
//            String url = "jdbc:mysql://localhost/";
//            String username = "root";
//            String password = "";
//            String dbName = "chrismaspresent?characterEncoding=utf8";
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://loc-internet.mysql.japan.rds.aliyuncs.com/";
            String username = "loc_user";
            String password = "loc@1210";
            String dbName = "loc_db?characterEncoding=utf8";
            Class.forName(driver).newInstance();
            conn = (Connection) DriverManager.getConnection(url + dbName, username, password);
            System.out.println("Connection successfully");
        } catch (Exception ex) {
            System.err.println("Connection fail");
            ex.printStackTrace();
        }
        return conn;
	}
}
