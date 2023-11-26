import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.google.gson.Gson;

import data.connection.Student;
import data.connection.studentDAO;

@WebServlet("/StudentCrud")
public class StudentCrud extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Gson gson;
    public StudentCrud() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        studentDAO dao = new studentDAO();
        gson = new Gson();
        DataSource dataSource = dao.createDataSource();
        try (Connection connection = dataSource.getConnection();
             PrintWriter out = response.getWriter()) {

           // out.println("Connected to the database!");
        
            List<Student> students = dao.readStudents();
            String jsonStudents = gson.toJson(students);
            out.println(jsonStudents);
            
        } catch (SQLException e) {
            response.getWriter().println("Failed to connect to the database.");
            e.printStackTrace();
        }
    }
    //Post
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	  studentDAO dao = new studentDAO();
          gson = new Gson();
    	
        
        String firstName = request.getParameter("firstname");
        String middleName = request.getParameter("middlename");
        String lastName = request.getParameter("lastname");

        
        Student newStudent = new Student();
        newStudent.setFirstName(firstName);
        newStudent.setMiddleName(middleName);
        newStudent.setLastName(lastName);

        
        boolean success = dao.createStudent(newStudent);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if (success) {
            out.println("Student added successfully");
        } else {
            out.println("Failed to add student");
        }
    }

    //Put 
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	  studentDAO dao = new studentDAO();
          gson = new Gson();
    	
    	
        
        long studentId = Long.parseLong(request.getParameter("id"));
        String firstName = request.getParameter("firstname");
        String middleName = request.getParameter("middlename");
        String lastName = request.getParameter("lastname");

        
        Student updatedStudent = new Student();
        updatedStudent.setId(studentId);
        updatedStudent.setFirstName(firstName);
        updatedStudent.setMiddleName(middleName);
        updatedStudent.setLastName(lastName);

        
        boolean success = dao.updateStudent(updatedStudent);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if (success) {
            out.println("Student updated successfully");
        } else {
            out.println("Failed to update student");
        }
    }
    //Delete
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	studentDAO dao = new studentDAO();
        gson = new Gson();
  	
    	
        
        long studentId = Long.parseLong(request.getParameter("id"));

        
        boolean success = dao.deleteStudent(studentId);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        if (success) {
            out.println("Student deleted successfully");
        } else {
            out.println("Failed to delete student");
        }
    }


}
