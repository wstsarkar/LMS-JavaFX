package repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import been.Users;

/**
 * 
 * @author Williyam
 * 
 */
public class UserRepository {
	

	public UserRepository() {

	}
	

	private List<Users> list(String query){
		ResultSet rs = DataBaseHelper.executeSelectQuery(query);	

		List<Users> list = new ArrayList<Users>();
		try {
			Users users;
			while (rs.next()) {
				users = new Users(rs.getInt("id"), rs.getString("username"), rs.getString("email"),rs.getString("password"), rs.getString("name"));
				list.add(users);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	private Users user(String query){
		ResultSet rs = DataBaseHelper.executeSelectQuery(query);	
		Users users = null;
		try {
			if (rs.next()) {
				users = new Users(rs.getInt("id"), rs.getString("username"), rs.getString("email"),rs.getString("password"), rs.getString("name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	
	public int insert(Users user){
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO Users (username,email,password,name) ");
		query.append(" VALUES('"+user.getUsername()+"','"+user.getEmail()+"','"+user.getPassword()+"','"+user.getName()+"')");
		
		int id =DataBaseHelper.executeQuery(query.toString());	
		return id;
	}


	public int update(Users user){
		StringBuilder query = new StringBuilder();
		query.append("UPDATE Users SET username='" + user.getUsername()+"',");
		query.append(" email='" + user.getEmail()+"',");
		query.append(" password='" + user.getPassword()+"',");
		query.append(" name='" + user.getName()+"',");
		query.append(" WHERE id = "+user.getId()+" ;");
		
		int id =DataBaseHelper.executeQuery(query.toString());	
		return id;
	}
	public int delete(int id){
		StringBuilder query = new StringBuilder();
		query.append("DELETE FROM Users " );
		query.append("WHERE id = "+id+" ;");
		
		int rowEffected = DataBaseHelper.executeQuery(query.toString());	
		return rowEffected;
	}

	public Users getUser(String userName, String password){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM Users ");
		query.append(" WHERE username = '" + userName + "'");
		query.append(" AND password = '" + password + "';");
		
		Users users =user(query.toString());
		return users;
	}	
	public Users getUser(String userName ){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM Users ");
		query.append(" WHERE username = '" + userName + "';");
		
		Users users =user(query.toString());
		return users;
	}	

	public List<Users> getUserList(){
		StringBuilder query = new StringBuilder();
		query.append(" SELECT * FROM Users ");
		
		user(query.toString());
		return list(query.toString());
	}	
	

}
