
public class Team {
	private String name;
	private String city;
	private double OUWinTotal;
	private int chipOdds;
	private String division;
	private int divisionOdds;
	private String conference;
	private int conferenceOdds;
	private int playoffOdds;
	
	public Team(String n, String c, double win, int chip, String d, int dOdds, 
			String con, int conOdds, int playoff) {
		super();
		name = n;
		city = c;
		OUWinTotal = win;
		chipOdds = chip;
		division = d;
		divisionOdds = dOdds;
		conference = con;
		conferenceOdds = conOdds;
		playoffOdds = playoff;
	}
	
	public String getName() {
		return name;
	}
	public String getCity() {
		return city;
	}
	public double getWinTotal() {
		return OUWinTotal;
	}
	public int getChipOdds() {
		return chipOdds;
	}
	public String getDiv() {
		return division;
	}
	public int getDivOdds() {
		return divisionOdds;
	}
	public String getCon() {
		return conference;
	}
	public int getConOdds() {
		return conferenceOdds;
	}
	public int getPlayoff() {
		return playoffOdds;
	}
	public void setName(String n) {
		this.name = n;
	}
	public void setCity(String c) {
		this.city = c;
	}
	public void setWinTotal(double win) {
		this.OUWinTotal = win;
	}
	public void setChipOdds(int odds) {
		this.chipOdds = odds;
	}
	public void setDiv(String d) {
		this.division = d;
	}
	public void setDivOdds(int divOdd) {
		this.divisionOdds = divOdd;
	}
	public void setCon(String c) {
		this.conference = c;
	}
	public void setConOdds(int conOdd) {
		this.conferenceOdds = conOdd;
	}
	public void setPlayoff(int playOdd) {
		this.playoffOdds = playOdd;
	}
}









