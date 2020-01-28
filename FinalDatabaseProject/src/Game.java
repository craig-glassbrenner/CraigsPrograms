
public class Game {
	
	private double overUnder;
	private double spread;
	private String location;
	private String date;
	private String gameType;
	private String homeTeam;
	private String awayTeam;
	private String favorite;
	private int finalScore;
	private String cover;
	
	public Game(double ou, double s, String loc, String d, String gT, String hT, String aT, String fav, int score, String cov) {
		super();
		overUnder = ou;
		spread = s;
		location = loc;
		date = d;
		gameType = gT;
		homeTeam = hT;
		awayTeam = aT;
		favorite = fav;
		finalScore = score;
		cover = cov;
	}
	
//	public String toString() {
//		return "Game [OU =" + overUnder + ", spread=" + 
//	}
	
	public double getOU() {
		return overUnder;
	}
	public void setOU(double ou) {
		this.overUnder = ou;
	}
	public double getSpread() {
		return spread;
	}
	public void setSpread(double s) {
		this.spread = s;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String l) {
		this.location = l;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String d) {
		this.date = d;
	}
	public String getType() {
		return gameType;
	}
	public void setType(String t) {
		this.gameType = t;
	}
	public String getHome() {
		return homeTeam;
	}
	public void setHome(String h) {
		this.homeTeam = h;
	}
	public String getAway() {
		return awayTeam;
	}
	public void setAway(String a) {
		this.awayTeam = a;
	}
	public String getFav() {
		return favorite;
	}
	public void setFav(String f) {
		this.favorite = f;
	}
	public int getFinal() {
		return finalScore;
	}
	public void setFinal(int f) {
		this.finalScore = f;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String c) {
		this.cover = c;
	}
}









