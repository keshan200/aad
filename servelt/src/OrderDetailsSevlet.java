import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.IOException;
import java.sql.*;


@WebServlet(urlPatterns = "/od")
public class OrderDetailsSevlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String oid = req.getParameter("oid");
        String itemCode = req.getParameter("itemCode");



        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/c", "root", "Ijse@123");
            PreparedStatement pstm = connection.prepareStatement("insert into orderdetails values(?,?)");
            pstm.setString(1, oid);
            pstm.setString(2, itemCode);

            pstm.executeUpdate();

            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("oid", oid);
            objectBuilder.add("itemCode", itemCode);


            resp.setContentType("application/json");
            resp.getWriter().write(objectBuilder.build().toString());

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/c", "root", "Ijse@123");

            PreparedStatement item = connection.prepareStatement("select description from item");
            PreparedStatement customer = connection.prepareStatement("select id from customer");

            ResultSet itemSet = item.executeQuery();
            JsonArrayBuilder itemArray = Json.createArrayBuilder();

            ResultSet customerSet = customer.executeQuery();
            JsonArrayBuilder customerArray = Json.createArrayBuilder();

            while (itemSet.next()) {
                String description = itemSet.getString("description");
                JsonObjectBuilder itemObj = Json.createObjectBuilder();
                itemObj.add("description", description);

                itemArray.add(itemObj);

            }


            while (customerSet.next()) {
                String customerId = customerSet.getString("id");
                JsonObjectBuilder customerObj = Json.createObjectBuilder();
                customerObj.add("customerId", customerId);

                customerArray.add(customerObj);
            }


            JsonObjectBuilder finlaObject = Json.createObjectBuilder();
            finlaObject.add("itemArray", itemArray);
            finlaObject.add("customerArray", customerArray);

            resp.setContentType("application/json");
            resp.getWriter().write(finlaObject.build().toString());

        }catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
