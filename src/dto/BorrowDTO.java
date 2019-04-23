package dto;


/**
 * 
 * @author Williyam
 * 
 */
public class BorrowDTO {
    private String title;
    private String author;
    private int year;
    
    private int isReturned;
    private String borrowOn;
    private String returnedOn;
    
    
    
	public BorrowDTO(String title, String author, int year, int isReturned, String borrowOn, String returnedOn) {
		super();
		this.title = title;
		this.author = author;
		this.year = year;
		this.isReturned = isReturned;
		this.borrowOn = borrowOn;
		this.returnedOn = returnedOn;
	}
	
	public String getTitle() {
		return title;
	}
	public String getAuthor() {
		return author;
	}
	public int getYear() {
		return year;
	}
	public int getIsReturned() {
		return isReturned;
	}
	public String getBorrowOn() {
		return borrowOn;
	}
	public String getReturnedOn() {
		return returnedOn;
	}
    
    
    
    
}
