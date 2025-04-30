package hospitalManagementSystem;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;


@WebServlet("/doctorServlet")
public class doctorServlet extends HttpServlet {
	
	 private Connection conn;
	 
	
	public void init(ServletConfig config) throws ServletException {
		 try {
	         Class.forName("com.mysql.cj.jdbc.Driver");
	         conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitamManagementSystem", "root", "Nut@n1705");
	     } catch (Exception e) {
	         throw new ServletException("Database Connection Error", e);
	     }
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query ="select * from doctors";
		 List<Map<String, Object>> doctorList = new ArrayList<>();
		try {
	    	PreparedStatement pstm =conn.prepareStatement(query);
	    	ResultSet rs=pstm.executeQuery();
	    	
	    	while(rs.next()) {
	    		  Map<String, Object> doctor = new HashMap<>();
	                doctor.put("id", rs.getInt("id"));
	                doctor.put("name", rs.getString("name"));
	                doctor.put("specielisation", rs.getString("specification"));
	                doctorList.add(doctor); 
	    		}
	    	    PrintWriter out = response.getWriter();
	            Gson gson = new Gson();
	            String json = gson.toJson(doctorList);
	            out.print(json);
	            out.flush();
	    	
	    	}catch (SQLException e) {
				// TODO: handle exception
	    		e.printStackTrace();
			}
	}

	
	public boolean getDoctorById(int id,Connection conn) {
    	String query="select * from doctors where id=?";
    	try {
			PreparedStatement pstm=conn.prepareStatement(query);
			pstm.setInt(1, id);
			ResultSet rs=pstm.executeQuery();
			if(rs.next()) {
				
				return true;
			
			}else {
				
				return false;
			
			}
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return false;
    }
 

}
