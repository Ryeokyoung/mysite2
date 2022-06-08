package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private String id = "webdb";
	private String pw = "1234";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	
	
	
	private void getConnection() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
			
		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	
	private void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	
	public int insert(UserVo userVo) {
		

		
		int count = 0;
		getConnection();
		
		try {
			String query = "insert into users\nvalues(seq_users_no.nextval, ?, ?, ?, ?) ";
			
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			
			
			count = pstmt.executeUpdate();
			
			// System.out.println("[" + count + "건 추가되었습니다.]");
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		close();
		return count;
		
	}
	
	
	public UserVo getUser(UserVo userVo) {
		UserVo user = null;
		getConnection();
		
		try {
			String query = "select no, name from users\nwhere id = ? and password = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getId());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int no = rs.getInt(1);
				String name = rs.getString(2);
				
				user = new UserVo(no, name);
			}
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		close();
		if (user == null) System.out.println("[비밀번호가 일치하지 않습니다]");
		return user;
	}
	
	
	public UserVo getUser(int no) {
		UserVo user = null;
		getConnection();
		
		try {
			String query = "select no, id, password, name, gender from users\nwhere no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				no = rs.getInt(1);
				id = rs.getString(2);
				String pw = rs.getString(3);
				String name = rs.getString(4);
				String gender = rs.getString(5);
				
				user = new UserVo(id, pw, name, gender);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		close();
		return user;
	}
	
	
	public void update(UserVo userVo) {
		int count = -1;
		getConnection();
		
		try {
			String query = "update users\nset password= ?, name= ?, gender= ?\nwhere no= ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		close();
		if (count != -1) System.out.println("[" + count + "건 수정되었습니다]");
	}


	
		
	}




	

	

		
