package repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import been.Student;

/**
 * 
 * @author Williyam
 * 
 */
public class StudentRepository {
	

	public StudentRepository() {

	}
	

	private List<Student> list(String query){
		ResultSet rs = DataBaseHelper.executeSelectQuery(query);	

		List<Student> list = new ArrayList<Student>();
		try {
			Student student;
			while (rs.next()) {
				student = new Student(rs.getInt("id"), rs.getString("student_id"), rs.getString("name"), rs.getString("email"));
				list.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	private Student student(String query){
		ResultSet rs = DataBaseHelper.executeSelectQuery(query);	
		Student users = null;
		try {
			if (rs.next()) {
				users = new Student(rs.getInt("id"), rs.getString("student_id"), rs.getString("name"), rs.getString("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	
	public int insert(Student student){
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO Student (student_id,email,name) ");
		query.append(" VALUES('"+student.getStudentId()+"','"+student.getEmail()+"','"+student.getName()+"')");
		
		int id =DataBaseHelper.executeQuery(query.toString());	
		return id;
	}


	public int update(Student student){
		StringBuilder query = new StringBuilder();
		query.append("UPDATE Student SET ");
		query.append(" email='" + student.getEmail()+"',");
		query.append(" name='" + student.getName()+"' ");
		query.append(" WHERE student_id = '"+student.getStudentId()+"' ;");
		
		int id =DataBaseHelper.executeQuery(query.toString());	
		return id;
	}
	
	public int delete(int id){
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM Student " );
		query.append("WHERE id = "+id+" ;");
		
		int rowEffected = DataBaseHelper.executeQuery(query.toString());	
		return rowEffected;
	}

	public Student getStudentBySID(String student_id ){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM Student ");
		query.append(" WHERE student_id = '" + student_id + "';");
		
		Student users =student(query.toString());
		return users;
	}	
	public Student getStudent(int id ){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM Student ");
		query.append(" WHERE d = '" + id + "';");
		
		Student users =student(query.toString());
		return users;
	}	

	public List<Student> getStudentList(){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM Student ");
		
		student(query.toString());
		return list(query.toString());
	}	
	

}
