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


@WebServlet("/managementServlet")
public class managementServlet extends HttpServlet {
	 Connection conn=null; 
	
	public void init() throws ServletException {
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitamManagementSystem", "root", "Nut@n1705");
        } catch (Exception e) {
            throw new ServletException("Database Connection Error", e);
        }
		
	}
       
  
    public managementServlet() {
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	

		patientServlet ps=new patientServlet();
		doctorServlet ds=new doctorServlet();
		
		int patientId=Integer.parseInt(request.getParameter("patientId"));
		int doctorId=Integer.parseInt(request.getParameter("doctorId"));
		String appointmentDate=request.getParameter("appointmentDate");
		
		
          if(ps.getPatientById(patientId,conn)&&ds.getDoctorById(doctorId,conn)) {
			
			if(isDoctorAvailable(doctorId,appointmentDate,conn)) {
				try {
					String query ="INSERT INTO  apointments(patient_id,doctor_id,apointment_date) VALUES(?,?,?)";
					PreparedStatement pstm=conn.prepareStatement(query);
					pstm.setInt(1, patientId);
					pstm.setInt(2, doctorId);
					pstm.setString(3, appointmentDate);
					int count=pstm.executeUpdate();
					if(count>0) {
						request.setAttribute("msg", "Appointment Booked Successfully");
						RequestDispatcher rd=request.getRequestDispatcher("/hospitalResponse.jsp");
						rd.forward(request, response);
						
					}else {
						request.setAttribute("msg", "Appointment Booking failed");
						RequestDispatcher rd=request.getRequestDispatcher("/hospitalResponse.jsp");
						rd.forward(request, response);
						System.out.println("Appointment Booking failed");
					}
				} catch (SQLException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}else {
				request.setAttribute("msg", "Doctor is not available");
				RequestDispatcher rd=request.getRequestDispatcher("/hospitalResponse.jsp");
				rd.forward(request, response);
				System.out.println("Doctor is not available");
				
			}
		}else {
			request.setAttribute("msg", "Either doctor or patient doesnt exist!! ");
			RequestDispatcher rd=request.getRequestDispatcher("/hospitalResponse.jsp");
			rd.forward(request, response);
			System.out.println("Either doctor or patient doesnt exist!! ");
		}
		
		 
	}

	
	
	
	public static boolean isDoctorAvailable(int doctorId, String appointmentDate, Connection conn) {
	    String query = "SELECT COUNT(0) FROM apointments where doctor_id=? AND apointment_date=?";
	    try {
	        PreparedStatement pstm = conn.prepareStatement(query);
	        pstm.setInt(1, doctorId);
	        pstm.setString(2, appointmentDate);

	        ResultSet rs = pstm.executeQuery();
	       
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count == 0; 
	        
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}


}
