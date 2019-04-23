package been;

/**
 * 
 * @author Williyam
 * 
 */
public class Student {

	private int id;
    private String studentId;
    private String name;
    private String email;
    
	public Student(int id, String studentId, String name, String email) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.name = name;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	
}
