package csci310.urlshortener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUrlStore implements UrlStore{
	private static Connection connection;
	private final static int RADIX = 36;
	
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
		
		Statement createTableStatement;
		try {
			createTableStatement = connection.createStatement();
			createTableStatement.executeUpdate(createTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String storeUrl(String url) {
		try {
			PreparedStatement insertUrl = connection.prepareStatement("INSERT INTO urls(url) VALUES (?);", Statement.RETURN_GENERATED_KEYS);
			insertUrl.setString(1, url);
			
			insertUrl.executeUpdate();
			
			ResultSet generatedKeys = insertUrl.getGeneratedKeys();
			
			if(!generatedKeys.next())
				return null;
			
			
			int id = generatedKeys.getInt(1);
			
			String urlCode = Integer.toString(id, RADIX);
			
			return urlCode;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String getUrl(String code) {
		int urlId = Integer.parseInt(code, RADIX);
		
		try {
			PreparedStatement selectUrl = connection.prepareStatement("SELECT url FROM urls WHERE id = ?;");
			
			selectUrl.setInt(1, urlId);
			
			ResultSet results = selectUrl.executeQuery();
			
			if(results.next()) {
				return results.getString("url");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
