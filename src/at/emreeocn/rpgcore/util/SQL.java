package at.emreeocn.rpgcore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL {

	private Connection con;
	private String database;
	private String host;
	private String username;
	private String password;
	private int port;
	
	public SQL(String database, String host, String username, String password, int port) {
		 this.database = database;
		 this.host = host;
		 this.username = username;
		 this.password = password;
		 this.port = port;
	}
	
	public void connect() throws SQLException {
		if(con != null && !con.isClosed()) {
			return;
		}
		
		con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		
		try {
			if (con.isClosed()) {
				connect();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			ps = con.prepareStatement(query);
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return ps;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getDatabase() {
		return database;
	}
	
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public Connection getCon() {
		return con;
	}
	
	public void setCon(Connection con) {
		this.con = con;
	}
	
}
