package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import pojos.User;

public interface UserDao {
//add a method for user authentication(login)
	User authenticateUser(String email, String password) throws SQLException;
	int newregisterUser(String fname,String lname,String email,String password,Date dob,boolean status,String role) throws SQLException;
	LocalDate validatedate(LocalDate birthdate);
}
