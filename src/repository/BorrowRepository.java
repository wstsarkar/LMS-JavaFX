package repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dto.BorrowDTO;
import util.DateUtils;

/**
 * 
 * @author Williyam
 * 
 */
public class BorrowRepository {

	private List<BorrowDTO> list(String query) {
		ResultSet rs = DataBaseHelper.executeSelectQuery(query);

		List<BorrowDTO> list = new ArrayList<BorrowDTO>();
		try {
			BorrowDTO dto;
			while (rs.next()) {
				dto = new BorrowDTO(rs.getString("title"), rs.getString("author"), rs.getInt("year"),
						rs.getInt("is_returned"), DateUtils.format(rs.getTimestamp("borrow_on")), DateUtils.returnDateFormat(rs.getTimestamp("returned_on")));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}


	public int borrowBook(int studentId, int bookId) {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO Borrow (student_id,book_id,is_returned,borrow_on,returned_on) ");
		query.append(" VALUES('" + studentId + "','" + bookId + "','0','"
				+ DateUtils.format(new Date()) + "',NULL)");

		int id = DataBaseHelper.executeQuery(query.toString());
		return id;
	}

	public int returnBook(int studentId, int bookId) {
		StringBuilder query = new StringBuilder();
		query.append("UPDATE Borrow SET is_returned='1',");
		query.append(" returned_on='" + DateUtils.format(new Date()) + "' ");
		query.append(" WHERE student_id = '" + studentId + "' ");
		query.append(" AND book_id = '" + bookId + "' ");
		query.append(" AND is_returned ='0';");

		int id = DataBaseHelper.executeQuery(query.toString());
		return id;
	}
	
	public List<BorrowDTO> getBorrowHistory(int studentId) {
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM Books book ");
		query.append(" INNER JOIN Borrow borrow ON book.id = borrow.book_id ");
		query.append(" AND borrow.student_id = '" + studentId + "'");
		query.append(" ORDER BY borrow.borrow_on DESC;");
		
		return list(query.toString());
	}
	
	


}
