import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class Login extends HttpServlet {
    static final String JDBC_DRIVER = "org.postgresql.Driver";  
      static final String DB_URL="jdbc:postgresql://localhost:5432/EMP";

      //  Database credentials
      static final String USER = "postgres";
      static final String PASS = "postgres";
	  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
 
		// Set response content type
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<p>hello</p>");
         try {
			// Register JDBC driver
			//Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName(JDBC_DRIVER);
         
			// Open a connection
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
	        System.out.println("Opened database successfully"+request.getParameter("username") );
			// Execute SQL query 
			PreparedStatement st = conn
                   .prepareStatement("select * from students  where username=?");
			st.setString(1, request.getParameter("username"));

			ResultSet rs = st.executeQuery();
            if(rs.next()){
                System.out.println(rs.getString("password")+request.getParameter("password"));
                if(rs.getString("password").equals(request.getParameter("password"))){
                    response.sendRedirect("registration.html");
                }
                else{
                    response.sendRedirect("successFail.html");
                }
            }

          
            
            // Close all the connections
            st.close();
            conn.close();
  
            // Get a writer pointer 
            // to display the successful result
            
            out.println("<html><body><b>Successfully Inserted"
                        + "</b></body></html>");
        }
        catch (Exception e) {
            out.println("<html><body><b> not Successfully Inserted"
            + "</b></body></html>");
            e.printStackTrace();
        }
   }
}
