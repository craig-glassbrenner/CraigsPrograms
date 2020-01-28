import java.awt.EventQueue;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NBA {

	public static void main(String[] args) {
		
		Database db = new Database();
		
		try {
			db.connect();
		} catch(SQLException e) {
			System.out.println("Unable to connect.");
			e.printStackTrace();
		}
		
		System.out.println("Connected.\n");
		
		Window1 window = new Window1(db);
		
//		try {
//			ResultSet results = db.teamAge();
//			
//			while(results.next()) {
//				String teamName = results.getString("TeamName");
//				String age = results.getString("PlayerAge");
//				System.out.println(teamName + " " + age);
//			}
//		} catch (SQLException e) {
//			System.out.println("Error when working with database...");
//			e.printStackTrace();
//		}

		
//		try {
//			db.disconnect();
//		} catch (SQLException e) {
//			System.out.println("Unable to disconnect.");
//			e.printStackTrace();
//		}
//		
//		System.out.println("\nDisconnected.");
	}

}
