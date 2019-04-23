package repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import been.Books;

/**
 * 
 * @author Williyam
 * 
 */
public class BookRepository {
	

	public BookRepository() {

	}
	

	private List<Books> list(String query){
		ResultSet rs = DataBaseHelper.executeSelectQuery(query);	

		List<Books> list = new ArrayList<Books>();
		try {
			Books book;
			while (rs.next()) {
				book = new Books(rs.getInt("id"), rs.getString("title"), rs.getString("author"),rs.getInt("year"), rs.getInt("pages"));
				list.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	private Books book(String query){
		ResultSet rs = DataBaseHelper.executeSelectQuery(query);	
		Books book = null;
		try {
			if (rs.next()) {
				book = new Books(rs.getInt("id"), rs.getString("title"), rs.getString("author"),rs.getInt("year"), rs.getInt("pages"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}

	
	public int insert(Books books){
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO Books (title,author,year,pages) ");
		query.append(" VALUES('"+books.getTitle()+"','"+books.getAuthor()+"','"+books.getYear()+"','"+books.getCount()+"')");
		
		int id =DataBaseHelper.executeQuery(query.toString());	
		return id;
	}


	public int update(Books books){
		StringBuilder query = new StringBuilder();
		query.append("UPDATE Books SET title='" + books.getTitle()+"',");
		query.append(" author='" + books.getAuthor()+"',");
		query.append(" year='" + books.getYear()+"',");
		query.append(" pages='" + books.getCount()+"' ");
		query.append(" WHERE id = '"+books.getId()+"' ;");
		
		int id =DataBaseHelper.executeQuery(query.toString());	
		return id;
	}
	public int delete(int id){
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM Books " );
		query.append("WHERE id = '"+id+"' ;");
		
		int rowEffected = DataBaseHelper.executeQuery(query.toString());	
		return rowEffected;
	}

	public Books getBook(int id){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM Books ");
		query.append(" WHERE id = '" + id + "';");
		
		Books book =book(query.toString());
		return book;
	}	
	public Books searchBook(String text){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM Books ");
		query.append(" WHERE title = '"+text+"' OR author='"+text+"' OR year ='"+text+"' OR pages = '"+text+"';");
		
		Books book =book(query.toString());
		return book;
	}	

	public List<Books> getBookList(){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM Books ");

		return list(query.toString());
	}	

	public List<Books> getAvailableBookList(int studentId){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT book.* FROM Books book ");
		query.append(" LEFT OUTER JOIN Borrow borrow ON book.id = borrow.book_id AND borrow.student_id = '" + studentId + "'");
		query.append(" AND borrow.is_returned = '0'");
		query.append(" WHERE borrow.id IS NULL ");
		query.append(" AND book.pages > 0 ;");

		return list(query.toString());
	}	

	public List<Books> getReturnableBookList(int studentId){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT book.* FROM Books book ");
		query.append(" INNER JOIN Borrow borrow ON book.id = borrow.book_id AND borrow.student_id = '" + studentId + "'");
		query.append(" AND borrow.is_returned = '0' ");
		query.append(" ORDER BY borrow.id DESC ");

		return list(query.toString());
	}	


	public List<Books> getAllBorrowedBookList(int studentId){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT book.* FROM Books book ");
		query.append(" INNER JOIN Borrow borrow ON book.id = borrow.book_id AND borrow.student_id = '" + studentId + "'");
		query.append(" AND borrow.is_returned = '1' ");
		query.append(" ORDER BY borrow.id DESC ");

		return list(query.toString());
	}	

}
