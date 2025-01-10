import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String oid = req.getParameter("oid");
        String date = req.getParameter("date");
        String cusID = req.getParameter("customerID");


        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/c", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("insert into orders values(?,?,?)");
            pstm.setString(1, oid);
            pstm.setString(2, date);
            pstm.setString(3, cusID);
            pstm.executeUpdate();

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("oid", oid);
            objectBuilder.add("date", date);
            objectBuilder.add("customerID", cusID);


            resp.setContentType("application/json");
            resp.getWriter().write(objectBuilder.build().toString());

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
