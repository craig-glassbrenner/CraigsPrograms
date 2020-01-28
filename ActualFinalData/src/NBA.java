import java.sql.SQLException;


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
		
	}

}
