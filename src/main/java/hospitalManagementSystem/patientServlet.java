package hospitalManagementSystem;

import jakarta.servlet.RequestDispatcher;
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

@WebServlet("/patientServlet")
public class patientServlet extends HttpServlet {
    private Connection conn;

    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitamManagementSystem", "root", "Nut@n1705");
        } catch (Exception e) {
            throw new ServletException("Database Connection Error", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String query = "SELECT * FROM patients";
        List<Map<String, Object>> patientList = new ArrayList<>();

        try (PreparedStatement pstm = conn.prepareStatement(query);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> patient = new HashMap<>();
                patient.put("id", rs.getInt("id"));
                patient.put("name", rs.getString("name"));
                patient.put("age", rs.getInt("age"));
                patient.put("gender", rs.getString("gender"));
                patientList.add(patient);  // ✅ Add patient object to the list
            }

            
            
            PrintWriter out = response.getWriter();
            Gson gson = new Gson();
            String json = gson.toJson(patientList);
            out.print(json);
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("patientName");
        int age = Integer.parseInt(request.getParameter("patientAge"));
        String gender = request.getParameter("gender");

        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement pstm = conn.prepareStatement(query);
            pstm.setString(1, name);
            pstm.setInt(2, age);
            pstm.setString(3, gender);

            int count = pstm.executeUpdate();
            
            if(count>0) {
            	request.setAttribute("msg", "patient addesd successfully ");
    			RequestDispatcher rd=request.getRequestDispatcher("/hospitalResponse.jsp");
    			rd.forward(request, response);
            }else {
            	request.setAttribute("msg", "Failed to add patient");
    			RequestDispatcher rd=request.getRequestDispatcher("/hospitalResponse.jsp");
    			rd.forward(request, response);
            }
            response.setContentType("text/plain");
            response.getWriter().write(count > 0 ? "Patient added successfully" : "Failed to add patient");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public boolean getPatientById(int id,Connection conn) {
    	String query="select * from patients where id=?";
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

