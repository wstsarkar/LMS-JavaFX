package been;

public class Books {

	private int id;
    private String title;
    private String author;
    private int year;
    private int count;

    public Books(int Id, String Title, String Author, int Year, int Count){
        this.id = Id;
    	this.title=Title;
        this.author = Author;
        this.year=Year;
        this.count=Count;
    }

    public int getId() {
    	return id;
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

    public int getCount() {
        return count;
    }

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
    
    
    
    
}
