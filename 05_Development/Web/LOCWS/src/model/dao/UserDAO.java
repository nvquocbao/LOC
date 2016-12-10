package model.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.bean.User;


public class UserDAO {
	public static void insert(User user) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionFactory.openConnection();
            String sql = "insert into User (email, password, name, birthday, avatar_path, address, type, create_date, update_date) "
                    + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setDate(4, new Date(user.getBirthday().getTime()));
            pstmt.setString(5, user.getAvatarPath());
            pstmt.setString(6, user.getAddress());
            pstmt.setBoolean(7, user.getType());
            Timestamp now = new Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(8, now);
            pstmt.setTimestamp(9, now);
            
            pstmt.executeUpdate();
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (Exception e) {
            }
        }
    }
	
	public static void update(User user) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionFactory.openConnection();
            String sql = "update User set SET email=?, password=?, name=?, birthday=?, "
            		+ "avatar_path=?, address=?, type=?, update_date=? where id = ?";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setDate(4, new Date(user.getBirthday().getTime()));
            pstmt.setString(5, user.getAvatarPath());
            pstmt.setString(6, user.getAddress());
            pstmt.setBoolean(7, user.getType());
            Timestamp now = new Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(8, now);
            pstmt.setInt(9, user.getId());
            
            pstmt.executeUpdate();
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (Exception e) {
            }
        }
    }
	
	public static ArrayList<User> getAll()
            throws ClassNotFoundException, SQLException {
        Connection con = null;
        ResultSet rs = null;
        ArrayList<User> list = new ArrayList<User>();
        try {
            con = ConnectionFactory.openConnection();
            String sql = "select id, email, password, name, birthday, avatar_path, address, type, create_date, update_date from User";
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
            	String id = rs.getString("id");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String name = rs.getString("name");
                java.util.Date birthday = rs.getDate("birthday");
                String avatarPath = rs.getString("avatar_path");
                String address = rs.getString("address");
                Boolean type = rs.getBoolean("type");
                java.util.Date createDate = rs.getDate("create_date");
                java.util.Date updateDate = rs.getDate("update_date");
                
                User entity = new User();
                entity.setId(new Integer(id));
                entity.setEmail(email);
                entity.setPassword(password);
                entity.setName(name);
                entity.setBirthday(birthday);
                entity.setAvatarPath(avatarPath);
                entity.setAddress(address);
                entity.setType(type);
                entity.setCreateDate(createDate);
                entity.setUpdateDate(updateDate);
                
                list.add(entity);
            }
            return list;
        } finally {
            try {
                rs.close();
                con.close();
            } catch (Exception e) {
            }
        }
    }

    public static User getUserById(int id)
            throws ClassNotFoundException, SQLException {
    	Connection con = null;
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.openConnection();
            String sql = "select id, email, password, name, birthday, avatar_path, address, type, create_date, update_date "
            		+ "from User where id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("password");
                String name = rs.getString("name");
                java.util.Date birthday = rs.getDate("birthday");
                String avatarPath = rs.getString("avatar_path");
                String address = rs.getString("address");
                Boolean type = rs.getBoolean("type");
                java.util.Date createDate = rs.getDate("create_date");
                java.util.Date updateDate = rs.getDate("update_date");
                
                User entity = new User();
                entity.setId(new Integer(id));
                entity.setEmail(email);
                entity.setPassword(password);
                entity.setName(name);
                entity.setBirthday(birthday);
                entity.setAvatarPath(avatarPath);
                entity.setAddress(address);
                entity.setType(type);
                entity.setCreateDate(createDate);
                entity.setUpdateDate(updateDate);
                
                return entity;
            }
            
        } finally {
            try {
                rs.close();
                con.close();
            } catch (Exception e) {
            	
            	
            }
        }
        return null;
    }
    
    public static User login(String email, String password)
            throws ClassNotFoundException, SQLException {
    	Connection con = null;
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.openConnection();
            String sql = "select id, email, name, birthday, avatar_path, address, type, create_date, update_date "
            		+ "from User where email = ? and password = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                java.util.Date birthday = rs.getDate("birthday");
                String avatarPath = rs.getString("avatar_path");
                String address = rs.getString("address");
                Boolean type = rs.getBoolean("type");
                java.util.Date createDate = rs.getDate("create_date");
                java.util.Date updateDate = rs.getDate("update_date");
                
                User entity = new User();
                entity.setId(new Integer(id));
                entity.setEmail(email);
                entity.setName(name);
                entity.setBirthday(birthday);
                entity.setAvatarPath(avatarPath);
                entity.setAddress(address);
                entity.setType(type);
                entity.setCreateDate(createDate);
                entity.setUpdateDate(updateDate);
                
                return entity;
            }
            
        } finally {
            try {
                rs.close();
                con.close();
            } catch (Exception e) {
            	
            	
            }
        }
        return null;
    }

    public static void delete(int id)
            throws ClassNotFoundException, SQLException {
    	Connection con = null;
    	PreparedStatement pstmt = null;
        try {
            con = ConnectionFactory.openConnection();
            String sql = "delete from User where id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            pstmt.executeUpdate();
            
        } finally {
            try {
                con.close();
            } catch (Exception e) {
         	
            	
            }
        }
    }
    
	public static int getNumberOfUsers() throws ClassNotFoundException, SQLException {
		Connection con = null;
        ResultSet rs = null;
        try {
            con = ConnectionFactory.openConnection();
            String sql = "select count(id) from User";
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
            int size = 0;
            if (rs.next()) {
            	size = rs.getInt(1);
            }
            return size;
        } finally {
            try {
                rs.close();
                con.close();
            } catch (Exception e) {
            }
        }
	}
}
