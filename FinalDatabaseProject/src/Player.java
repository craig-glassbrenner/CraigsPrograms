
public class Player {
	private String FirstName;
	private String LastName;
	private int Age;
	private String height;
	private int weight;
	private String position;
	private String team;
	
	public Player(String f, String l, int a, String h, int w, String p, String t) {
		super();
		FirstName = f;
		LastName = l;
		Age = a;
		height = h;
		weight = w;
		position = p;
		team = t;
	}
	
	public String getFirst() {
		return FirstName;
	}
	public String getLast( ) {
		return LastName;
	}
	public int getAge( ) {
		return Age;
	}
	public String getHeight() {
		return height;
	}
	public int getWeight() {
		return weight;
	}
	public String getPosition() {
		return position;
	}
	public String getTeam() {
		return team;
	}
	public void setFirst(String f) {
		this.FirstName = f;
	}
	public void setLast(String l) {
		this.LastName = l;
	}
	public void setAge(int a) {
		this.Age = a;
	}
	public void setHeight(String h) {
		this.height = h;
	}
	public void setWeight(int w) {
		this.weight = w;
	}
	public void setPosition(String p) {
		this.position = p;
	}
	public void setTeam(String t) {
		this.team = t;
	}
}








