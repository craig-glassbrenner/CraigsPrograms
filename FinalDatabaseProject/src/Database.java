import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

	private String url = "jdbc:sqlite:/Users/craigglassbrenner/Desktop/CS364FinalProject/NBADatabase.db";
	private Connection connection;
	
	public Database() {}
	
	public void connect() throws SQLException {
		connection = DriverManager.getConnection(url);
	}
	
	public void disconnect() throws SQLException {
		connection.close();
	}
	
	public ResultSet runQuery(String query) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	public ResultSet teamAge() throws SQLException {
		String query = "SELECT TeamName, sum(Age) AS PlayerAge FROM Player GROUP BY TeamName ORDER BY PlayerAge;";
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
}
