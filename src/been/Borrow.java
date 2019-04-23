package been;

import java.util.Date;

/**
 * 
 * @author Williyam
 * 
 */
public class Borrow {

	private int id;
    private int studentId;
    private int bookId;
    private int isReturned;
    private Date borrowOn;
    private Date returnedOn;
	
    public Borrow(int id, int studentId, int bookId, int isReturned, Date borrowOn, Date returnedOn) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.bookId = bookId;
		this.isReturned = isReturned;
		this.borrowOn = borrowOn;
		this.returnedOn = returnedOn;
	}
    public Borrow(int id, int studentId, int bookId, int isReturned) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.bookId = bookId;
		this.isReturned = isReturned;
		this.borrowOn = new Date();
		this.returnedOn = new Date();
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

	public int getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getBookId() {
		return bookId;
	}

	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getIsReturned() {
		return isReturned;
	}

	/**
	 * @param isReturned the isReturned to set
	 */
	public void setIsReturned(int isReturned) {
		this.isReturned = isReturned;
	}

	public Date getBorrowOn() {
		return borrowOn;
	}

	/**
	 * @param borrowOn the borrowOn to set
	 */
	public void setBorrowOn(Date borrowOn) {
		this.borrowOn = borrowOn;
	}

	public Date getReturnedOn() {
		return returnedOn;
	}

	/**
	 * @param returnedOn the returnedOn to set
	 */
	public void setReturnedOn(Date returnedOn) {
		this.returnedOn = returnedOn;
	}
    	
}
