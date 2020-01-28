import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * 
 * load SQL driver (JDBC/ODBC)
 * - add to build path
 * 
 * set up our database (script)
 * (-create database)
 * - create tables
 * - populate with starting data
 * 
 * connect to the database
 * 
 * insert/modify/delete data
 * 
 * query data
 * 
 * disconnect from the database
 * 
 */

public class Database {

	private String url = "jdbc:sqlite:/Users/asauppe/Documents/Employee.db";
	//private String url = "jdbc:mysql://localhost/Employee?user=example&password=examplepassword";
	
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
	
	public ResultSet employeeLookup(String ssn) throws SQLException {
		String query = "SELECT * FROM Employee WHERE SSN = ?";
		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, ssn);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	public int insertEmployee(Employee e) throws SQLException {
		String sql = "INSERT INTO Employee(SSN, Salary, FirstName, MiddleName, LastName) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = connection.prepareStatement(sql);
		stmt.setString(1, e.getSsn());
		stmt.setDouble(2, e.getSalary());
		stmt.setString(3, e.getFirstName());
		stmt.setString(4, e.getMiddleName());
		stmt.setString(5, e.getLastName());
		
		int numRowsAffected = stmt.executeUpdate();
		return numRowsAffected;
	}
	
}











