package model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.bean.Message;
import model.bean.User;


public class MessageDAO {
	public static void insert(Message message) throws ClassNotFoundException, SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = ConnectionFactory.openConnection();
            String sql = "insert into Message (child_id, parent_id, content, create_date, update_date) "
                    + "values (?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, message.getChildId());
            pstmt.setInt(2, message.getParentId());
            pstmt.setString(3, message.getContent());
            Timestamp now = new Timestamp(new java.util.Date().getTime());
            pstmt.setTimestamp(4, now);
            pstmt.setTimestamp(5, now);
            
            pstmt.executeUpdate();
        } finally {
            try {
                pstmt.close();
                con.close();
            } catch (Exception e) {
            }
        }
    }
	
	public static ArrayList<Message> getAllMessageOfConversation(int childId, int parentId)
            throws ClassNotFoundException, SQLException {
    	Connection con = null;
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Message> list = new ArrayList<Message>();
        try {
            con = ConnectionFactory.openConnection();
            String sql = "select id, child_id, parent_id, content, create_date, update_date "
            		+ "from Message where child_id = ? and parent_id = ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, childId);
            pstmt.setInt(2, parentId);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String content = rs.getString("content");
                java.util.Date createDate = rs.getDate("create_date");
                java.util.Date updateDate = rs.getDate("update_date");
                
                Message entity = new Message();
                entity.setId(new Integer(id));
                entity.setChildId(childId);
                entity.setParentId(parentId);
                entity.setContent(content);
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

    public static ArrayList<Message> getAllMessageOfUser(User user)
            throws ClassNotFoundException, SQLException {
    	Connection con = null;
    	PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<Message> list = new ArrayList<Message>();
        try {
            con = ConnectionFactory.openConnection();
            String sql = "select id, child_id, parent_id, content, create_date, update_date "
            		+ "from Message where child_id = (select id from user where email = ? and password = ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPassword());
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                int childId = rs.getInt("child_id");
                int parentId = rs.getInt("parent_id");
                String content = rs.getString("content");
                java.util.Date createDate = rs.getDate("create_date");
                java.util.Date updateDate = rs.getDate("update_date");
                
                Message entity = new Message();
                entity.setId(new Integer(id));
                entity.setChildId(childId);
                entity.setParentId(parentId);
                entity.setContent(content);
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
    
    public static void delete(int id)
            throws ClassNotFoundException, SQLException {
    	Connection con = null;
    	PreparedStatement pstmt = null;
        try {
            con = ConnectionFactory.openConnection();
            String sql = "delete from Message where id = ?";
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
}
