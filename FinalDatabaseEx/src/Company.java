import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Company {

	public static void main(String[] args) {

		Database db = new Database();
		
		try {
			db.connect();
		} catch (SQLException e) {
			System.out.println("Unable connect.");
			e.printStackTrace();
		}
		
		System.out.println("Connected!\n");
		
//		try {
//			ResultSet results = db.employeeLookup("987-65-4321");
//			Employee emp;
//			
//			while(results.next()) {
//				String ssn = results.getString("SSN");
//				double salary = results.getDouble("Salary");
//				String firstName = results.getString("FirstName");
//				String middleName = results.getString("MiddleName");
//				String lastName = results.getString("LastName");
//				
//				emp = new Employee(ssn, salary, firstName, middleName, lastName);
//				System.out.println(emp);
//			}
			
			
//			Employee emp = new Employee("123-12-1111", 70000, "Betsy", "Elizabeth", "Thompson");
//			int numRowsAffected = db.insertEmployee(emp);
//			System.out.println(numRowsAffected + " rows affected");
			
			
//			String query = "SELECT SSN, Salary, FirstName, MiddleName, LastName FROM Employee";
//			ResultSet results = db.runQuery(query);
//			
//			ArrayList<Employee> employees = new ArrayList<>();
//			
//			while(results.next()) {
//				//String ssn = results.getString(1);
//				String ssn = results.getString("SSN");
//				double salary = results.getDouble("Salary");
//				String firstName = results.getString("FirstName");
//				String middleName = results.getString("MiddleName");
//				String lastName = results.getString("LastName");
//				
//				Employee empObj = new Employee(ssn, salary, firstName, middleName, lastName);
//				employees.add(empObj);
//			}
//			
//			for(Employee e : employees) {
//				System.out.println(e);
//			}
			
//		} catch (SQLException e) {
//			System.out.println("Error when working with the database...");
//			e.printStackTrace();
//		}
		
		try {
			db.disconnect();
		} catch (SQLException e) {
			System.out.println("Unable to disconnect.");
			e.printStackTrace();
		}
		
		System.out.println("\nDisconnected!");
		
	}

}
