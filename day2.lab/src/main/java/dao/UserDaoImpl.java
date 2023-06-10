package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

import static utils.DBUtils.*;

import pojos.User;

public class UserDaoImpl implements UserDao {
	// fields
	private Connection cn;
	private PreparedStatement pst1;
	private PreparedStatement pst2;

	public UserDaoImpl() throws SQLException {
		// open conn
		cn = openConnection();
		pst1 = cn.prepareStatement("select * from users where email=? and password=?");
		pst2=cn.prepareStatement("insert into users values(default,?,?,?,?,?,?,?)");
		System.out.println("user dao created!");
	}

	@Override
	public User authenticateUser(String email, String password) throws SQLException {
		// set IN params
		pst1.setString(1, email);
		pst1.setString(2, password);
		try (ResultSet rst = pst1.executeQuery()) {
			if (rst.next()) // => success
				/*
				 * int id, String firstName, String lastName, String email, String password,
				 * Date dob, boolean votingStatus, String role
				 */
				return new User(rst.getInt(1), rst.getString(2), rst.getString(3), email, password, rst.getDate(6),
						rst.getBoolean(7), rst.getString(8));
		}
		return null;
	}

	public void cleanUp() throws SQLException {
		if (pst1 != null)
			pst1.close();
		closeConnection();
		System.out.println("user dao cleaned up!");
	}

	@Override
	public int newregisterUser(String fname, String lname, String email, String password, Date dob, boolean status,
			String role) throws SQLException {
		
		pst2.setString(1, fname);
		pst2.setString(2, lname);
		pst2.setString(3, email);
		pst2.setString(4, password);
		pst2.setDate(5, dob);
		pst2.setBoolean(6, status);
		pst2.setString(7, role);
		int insert=pst2.executeUpdate();
		if(insert>0)
		{
			System.out.println("Data Inserted successfully");
			return insert;
		
		}
		
		return 0;
	}

	@Override
	public LocalDate validatedate(LocalDate birthdate) throws DateTimeParseException{
		int age=Period.between(birthdate,LocalDate.now()).getYears();
		if(age > 21)
		return birthdate;
		else
			return null;
			
			
		
		
	}
}
