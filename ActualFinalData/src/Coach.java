
public class Coach {
	private String FirstName;
	private String LastName;
	private String jobTitle;
	private String Team;
	
	public Coach(String f, String l, String j, String t) {
		super();
		FirstName = f;
		LastName = l;
		jobTitle = j;
		Team = t;
	}
	
	public String getFirst() {
		return FirstName;
	}
	public void setFirst(String f) {
		this.FirstName = f;
	}
	public String getLast() {
		return LastName;
	}
	public void setLast(String l) {
		this.LastName = l;
	}
	public String getTitle() {
		return jobTitle;
	}
	public void setTitle(String t) {
		this.jobTitle = t;
	}
	public String getTeam() {
		return Team;
	}
	public void setTeam(String t) {
		this.Team = t;
	}

}
