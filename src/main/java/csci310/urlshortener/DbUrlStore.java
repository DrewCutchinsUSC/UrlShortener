package csci310.urlshortener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbUrlStore implements UrlStore{
	private static Connection connection;
	
	private static final int CODE_RADIX = 36;
	
	static {
		try {
			String url = "jdbc:sqlite:test.db";
	        connection = DriverManager.getConnection(url);
	            
	        System.out.println("Connection to SQLite has been established.");
	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }
		String createTable = "CREATE TABLE IF NOT EXISTS urls ("
				+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "url TEXT NOT NULL"
				+ ");";
		
		try {
			Statement createTableStatement = connection.createStatement();
			createTableStatement.executeUpdate(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	@Override
	public String storeUrl(String url) {
		try {
			PreparedStatement insertUrl = connection.prepareStatement("INSERT INTO urls(url) VALUES(?);", Statement.RETURN_GENERATED_KEYS);
			insertUrl.setString(1, url);
			
			insertUrl.executeUpdate();
			ResultSet generatedKeys = insertUrl.getGeneratedKeys();
			
			if(!generatedKeys.next())
				return null;
			
			int id = generatedKeys.getInt(1);
			
			String urlCode = Integer.toString(id, CODE_RADIX);
			
			return urlCode;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}


	@Override
	public String getUrl(String code) {
		int urlNumber = Integer.parseInt(code, CODE_RADIX);
		
		try {
			PreparedStatement getUrl = connection.prepareStatement("SELECT url FROM urls WHERE id = ?;", Statement.RETURN_GENERATED_KEYS);
			getUrl.setInt(1, urlNumber);
			
			ResultSet results = getUrl.executeQuery();
			
			if(results.next()) {
				return results.getString("url");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
