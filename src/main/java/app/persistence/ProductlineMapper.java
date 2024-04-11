 package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductlineMapper {

    public static List<Productline> search(String topName, String bottomName, int productlineAmount, ConnectionPool connectionPool) throws DatabaseException {
        List<Productline> productlineList = new ArrayList<>();
        String sql = "SELECT * FROM productline INNER JOIN cupcake_top as c_top ON c_top.top_id = productline.top_id " +
                "INNER JOIN cupcake_bottom as c_bot ON c_bot.bottom_id = productline.bottom_id WHERE c_top.top_name=? AND c_bot.bottom_name=?";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, topName);
            ps.setString(2, bottomName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int topId = rs.getInt("top_id");
                int bottomId = rs.getInt("bottom_id");
                int productlinePrice = rs.getInt("top_price") + rs.getInt("bottom_price");
                int productlineId = rs.getInt("productline_id");
                productlineList.add(new Productline(productlineId, productlineAmount, topId, bottomId, productlinePrice));
            }
        } catch (SQLException e) {
            throw new DatabaseException("The database search failed", e.getMessage());
        }
        return productlineList;
    }
  /*  public static Order addToOrder(int customerId, ConnectionPool connectionPool) throws DatabaseException
    {
        Order newOrder = null;

        String sql = "INSERT INTO public.order (customer_id, order_date, order_price)\n" +
                "SELECT \n" +
                "    customer.customer_id, \n" +
                "    CURRENT_DATE, \n" +
                "    SUM(productline.productline_price) \n" +
                "FROM \n" +
                "    public.customer\n" +
                "INNER JOIN \n" +
                "    public.order ON customer.customer_id = public.order.customer_id WHERE customer.customer_id=? \n" +
                "INNER JOIN \n" +
                "    public.productline ON public.productline.order_id = public.order.order_id \n" +
                "GROUP BY \n" +
                "    customer.customer_id;";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        )
        {   ResultSet rs = ps.executeQuery();
            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            ps.getGeneratedKeys();
            if (rowsAffected == 1)
                while (rs.next()) {
                    int orderId = rs.getInt("order_id");
                    int orderAmount = rs.getInt("order_amount");
                    String orderDate = String.valueOf(rs.getDate("CURRENT_DATE"));
                    int orderPrice = rs.getInt("productline_price");
                    newOrder = new Order(orderId, customerId, orderAmount, orderDate, orderPrice);
                }


             else
            {
                throw new DatabaseException("Error while adding an order " + newOrder);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Error in database connection", e.getMessage());
        }
        return newOrder;
    }

   */
    public static Order addToOrder(int customerId, ConnectionPool connectionPool) throws DatabaseException {
        Order newOrder = null;

        String sql = "INSERT INTO public.order (customer_id, order_date, order_price)\n" +
                "SELECT \n" +
                "    customer.customer_id, \n" +
                "    CURRENT_DATE, \n" +
                "    SUM(productline.productline_price) \n" +
                "FROM \n" +
                "    public.customer\n" +
                "INNER JOIN \n" +
                "    public.order ON customer.customer_id = public.order.customer_id WHERE customer.customer_id=? \n" +
                "INNER JOIN \n" +
                "    public.productline ON public.productline.order_id = public.order.order_id \n" +
                "GROUP BY \n" +
                "    customer.customer_id;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rowsAffected == 1 && rs.next()) {
                int orderId = rs.getInt("order_id");
                int orderAmount = rs.getInt("order_amount"); // Assuming this field exists in the result set
                String orderDate = String.valueOf(rs.getDate("CURRENT_DATE"));
                int orderPrice = rs.getInt("productline_price"); // Assuming this field exists in the result set
                newOrder = new Order(orderId, customerId, orderAmount, orderDate, orderPrice);
            } else {
                throw new DatabaseException("Error while adding an order: " + newOrder);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error in database connection", e.getMessage());
        }

        return newOrder;
    }


}
