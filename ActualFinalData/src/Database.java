import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;


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
	
	public void figureOutQuery(String team, String opponent, String homeOrAway, String fav) throws SQLException {
		
		ResultSet results = null;
		JFrame display = new JFrame();
		JTextArea toShow = new JTextArea();
		JScrollPane scr = new JScrollPane();
		
		display.setBounds(200, 200, 550, 400);
		display.getContentPane().add(toShow);
		
		scr.setBounds(150, 0, 150, 139);
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scr.setViewportView(toShow);
		display.getContentPane().add(scr);
		
		if(opponent.compareTo("") == 0 && homeOrAway.compareTo("") == 0 && fav.compareTo("") == 0) {
			results = bareMinimum(team);
		} else if(opponent.compareTo("") != 0 && homeOrAway.compareTo("") == 0 && fav.compareTo("") == 0) {
			results = teamAndOpponent(team, opponent);
		} else if(opponent.compareTo("") != 0 && homeOrAway.compareTo("") != 0 && fav.compareTo("") == 0) {
			results = homeOrAwaySpecified(team, opponent, homeOrAway);
		} else if(opponent.compareTo("") == 0 && homeOrAway.compareTo("") != 0 && fav.compareTo("") == 0) {
			results = homeOrAwayNoOpponent(team, homeOrAway);
		} else if(opponent.compareTo("") != 0 && homeOrAway.compareTo("") != 0 && fav.compareTo("") != 0) {
			results = allSpecified(team, opponent, homeOrAway, fav);
		} else if(opponent.compareTo("") == 0 && homeOrAway.compareTo("") != 0 && fav.compareTo("") != 0) {
			results = allButOpponentSpec(team, homeOrAway, fav);
		} else if(opponent.compareTo("") == 0 && homeOrAway.compareTo("") == 0 && fav.compareTo("") != 0) {
			results = noOppOrHomeSpec(team, fav);
		}
		
		while(results.next()) {
			double overUnder = results.getDouble("OU");
			double spread = results.getDouble("Spread");
			String location = results.getString("Location");
			String date = results.getString("Date");
			String gameType = results.getString("GameType");
			String homeTeam = results.getString("HomeTeam");
			String awayTeam = results.getString("AwayTeam");
			String favorite = results.getString("Favorite");
			int score = results.getInt("FinalScore");
			String cover = results.getString("Cover");
			int gameID = results.getInt("GameID");
			
			Game g = new Game(overUnder, spread, location, date, gameType, homeTeam, awayTeam, favorite, score, cover, gameID);
			toShow.append(g.toString());
		}
		
		display.setVisible(true);
		
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
	
	public ResultSet divisonVSSpread() throws SQLException {
		String query = "SELECT DivisonName, count(DivisonName)*100 / numGames AS percent\n" + 
				"FROM Game JOIN TEAM ON Game.HomeTeam = Team.Name NATURAL JOIN (SELECT DivisonName, count(DivisonName) AS numGames\n" + 
				"FROM Team JOIN Game on Team.Name = Game.HomeTeam\n" + 
				"GROUP BY DivisonName)\n" + 
				"WHERE Cover = 'YES'\n" + 
				"GROUP BY DivisonName;";
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	public ResultSet bestvsOU() throws SQLException {
		String query = "SELECT Favorite, sum(FinalScore - OU) AS ActualVsPredict FROM Game GROUP BY Favorite HAVING ActualVsPredict >= 10 ORDER BY ActualVsPredict DESC;";
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	public ResultSet coverPercentage() throws SQLException {
		String query = "SELECT Favorite, (count(Cover)*100 / numTimesFav) AS percentCover " +
			    			"FROM Game NATURAL JOIN (SELECT Favorite, count(Favorite) AS numTimesFav " +
			    										"FROM Game " +
			    										"GROUP BY Favorite) " +
			    "WHERE Cover = 'YES' " +
			    "GROUP BY Favorite ORDER BY percentCover DESC;";
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	public ResultSet bareMinimum(String teamName) throws SQLException {
		String query = "Select * FROM Game WHERE HomeTeam LIKE '" + teamName + "' OR AwayTeam LIKE '" + teamName + "'";
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	public ResultSet teamAndOpponent(String teamName, String opponentName) throws SQLException {
		String query = "SELECT * FROM Game WHERE (HomeTeam LIKE '" + teamName + "' AND AwayTeam LIKE '" + opponentName + "') OR (HomeTeam LIKE '" +
						opponentName + "' AND AwayTeam LIKE '" + teamName + "');";
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	public ResultSet homeOrAwaySpecified(String teamName, String opponentName, String homeOrAway) throws SQLException {
		String query;
		if(homeOrAway.compareTo("Home") == 0) {
			query = "SELECT * FROM Game WHERE HomeTeam LIKE '" + teamName + "' AND AwayTeam LIKE '" + opponentName + "';";
		} else {
			query = "SELECT * FROM Game WHERE HomeTeam LIKE '" + opponentName + "' AND AwayTeam LIKE '" + teamName + "';";
		}
		
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	public ResultSet homeOrAwayNoOpponent(String teamName, String homeOrAway) throws SQLException {
		String query;
		if(homeOrAway.compareTo("Home") == 0) {
			query = "SELECT * FROM Game WHERE HomeTeam LIKE '" + teamName + "';";
		} else {
			query = "SELECT * FROM Game WHERE AwayTeam LIKE '" + teamName + "';";
		}
		
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	public ResultSet allSpecified(String teamName, String opponent, String homeOrAway, String favorite) throws SQLException {
		String query;
		if(homeOrAway.compareTo("Home") == 0 && favorite.compareTo("YES") == 0) {
			query = "SELECT * FROM Game WHERE HomeTeam LIKE '" + teamName + "' AND AwayTeam LIKE '" + opponent + 
					"' AND Favorite LIKE '" + teamName + "';"; 
		} else if(homeOrAway.compareTo("Home") == 0 && favorite.compareTo("NO") == 0) {
			query = "SELECT * FROM Game WHERE HomeTeam LIKE '" + teamName + "' AND AwayTeam LIKE '" + opponent +
					"' AND Favorite LIKE '" + opponent + "';";
		} else if(homeOrAway.compareTo("Away") == 0 && favorite.compareTo("YES") == 0) {
			query = "SELECT * FROM Game WHERE HomeTeam LIKE '" + opponent + "' AND AwayTeam LIKE '" + teamName + 
					"' AND Favorite LIKE '" + teamName + "';";
		} else {
			query = "SELECT * FROM Game WHERE HomeTeam LIKE '" + opponent + "' AND AwayTeam LIKE '" + teamName + 
					"' AND Favorite LIKE '" + opponent + "';";
		}
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	public ResultSet allButOpponentSpec(String teamName, String homeOrAway, String favorite) throws SQLException {
		String query;
		if(homeOrAway.compareTo("Home") == 0 && favorite.compareTo("YES") == 0) {
			query = "SELECT * FROM Game WHERE HomeTeam LIKE '" + teamName + "' AND Favorite LIKE '" + teamName + "';"; 
		} else if(homeOrAway.compareTo("Home") == 0 && favorite.compareTo("NO") == 0) {
			query = "SELECT * FROM Game WHERE HomeTeam LIKE '" + teamName + "' AND Favorite != '" + teamName + "';";
		} else if(homeOrAway.compareTo("Away") == 0 && favorite.compareTo("YES") == 0) {
			query = "SELECT * FROM Game WHERE AwayTeam LIKE '" + teamName + "' AND Favorite LIKE '" + teamName + "';";
		} else {
			query = "SELECT * FROM Game WHERE AwayTeam LIKE '" + teamName + "' AND Favorite != '" + teamName + "';";
		}
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	public ResultSet noOppOrHomeSpec(String teamName, String favorite) throws SQLException {
		String query;
		if(favorite.compareTo("YES") == 0) {
			query = "SELECT * FROM Game WHERE Favorite = '" + teamName + "';";
		} else {
			query = "SELECT * FROM Game WHERE Favorite != '" + teamName + "' AND (HomeTeam = '" + teamName + 
					"' OR AwayTeam = '" + teamName + "');";
		}
		
		PreparedStatement stmt = connection.prepareStatement(query);
		ResultSet results = stmt.executeQuery();
		
		return results;
	}
	
	
	
}
