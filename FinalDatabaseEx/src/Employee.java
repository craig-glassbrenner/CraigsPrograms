
public class Employee {

	private String ssn;
	private double salary;
	private String firstName;
	private String middleName;
	private String lastName;
	
	public Employee(String ssn, double salary, String firstName, String middleName, String lastName) {
		super();
		this.ssn = ssn;
		this.salary = salary;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}
	
	@Override
	public String toString() {
		return "Employee [ssn=" + ssn + ", salary=" + salary + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + "]";
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	
	
	
}
