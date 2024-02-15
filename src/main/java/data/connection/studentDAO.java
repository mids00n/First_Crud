package data.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.postgresql.ds.PGSimpleDataSource;

public class studentDAO {
	 private static final String JDBC_URL = "jdbc:postgresql://localhost:5433/popa";
	    private static final String USERNAME = "mibar";
	    private static final String PASSWORD = "1111";
	    private static final Logger logger = LoggerFactory.getLogger(studentDAO.class);
	  
	    public DataSource createDataSource() {
	    	final String url =
                "jdbc:postgresql://localhost:5433/popa?user=mibar&password=1111";
	    	
	    	 final PGSimpleDataSource dataSource = new PGSimpleDataSource();
	    	dataSource.setUrl(url);
	    	return dataSource;
	    }		
	  //Get
	    public List<Student> readStudents() {
	        List<Student> students = new ArrayList<>();

	        final String url =
	                "jdbc:postgresql://localhost:5433/popa?user=mibar&password=1111";
		    	
		    	 final PGSimpleDataSource dataSource = new PGSimpleDataSource();
		    	dataSource.setUrl(url);
	        
	        try (Connection connection = dataSource.getConnection()) {
	            String sql = "SELECT * FROM students"; 
	            try (PreparedStatement statement = connection.prepareStatement(sql);
	                 ResultSet resultSet = statement.executeQuery()) {

	                while (resultSet.next()) {

	                    int id = resultSet.getInt("id");
	                    String firstName = resultSet.getString("firstname");
	                    String middleName = resultSet.getString("middlename");
	                    String lastName = resultSet.getString("lastname");

	                    Student student = new Student(id, firstName, middleName, lastName);
	                    students.add(student);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }

	        return students;
	    }
	    //Post
	    public boolean createStudent(Student newStudent) {
	    	final String url =
	                "jdbc:postgresql://localhost:5433/popa?user=mibar&password=1111";
		    	
		    	 final PGSimpleDataSource dataSource = new PGSimpleDataSource();
		    	dataSource.setUrl(url);
	    	
	        String sql = "INSERT INTO students (firstname, middlename, lastname) VALUES (?, ?, ?)";
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setString(1, newStudent.getFirstName());
	            statement.setString(2, newStudent.getMiddleName());
	            statement.setString(3, newStudent.getLastName());

	            int rowsAffected = statement.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }

	        return false;
	    }
	    
	    //Put
	    public boolean updateStudent(Student updatedStudent) {
	    	final String url =
	                "jdbc:postgresql://localhost:5433/popa?user=mibar&password=1111";
		    	
		    	 final PGSimpleDataSource dataSource = new PGSimpleDataSource();
		    	dataSource.setUrl(url);
	    	
	        String sql = "UPDATE  students SET firstname=?, middlename=?, lastname=? WHERE id=?";
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setString(1, updatedStudent.getFirstName());
	            statement.setString(2, updatedStudent.getMiddleName());
	            statement.setString(3, updatedStudent.getLastName());
	            statement.setLong(4, updatedStudent.getId());

	            int rowsAffected = statement.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace(); 
	        }

	        return false;
	    }
	//Delete 
	    public boolean deleteStudent(long studentId) {
	    	final String url =
	                "jdbc:postgresql://localhost:5433/popa?user=mibar&password=1111";
		    	
		    	 final PGSimpleDataSource dataSource = new PGSimpleDataSource();
		    	dataSource.setUrl(url);

	    	
	        String sql = "DELETE FROM students WHERE id=?";
	        try (Connection connection = dataSource.getConnection();
	             PreparedStatement statement = connection.prepareStatement(sql)) {

	            statement.setLong(1, studentId);

	            int rowsAffected = statement.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	        	 logger.error("Failed to delete student with id {}", studentId, e);
	        
	        }

	        return false;
	    }
}
	