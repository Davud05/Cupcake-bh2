package app.persistence;

import app.entities.Order;
import app.entities.Productline;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static List<Order> viewShoppingCart(int customerId, List<Productline> productlineList, ConnectionPool connectionPool) throws DatabaseException
    {
        List<Order> orderList = new ArrayList<>();
        String sql = "select * from order where customer_id=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                int orderId = rs.getInt("order_id");
                int orderAmount = rs.getInt("order_amount");
                String orderDate = String.valueOf(rs.getDate("CURRENT_DATE"));
                int orderPrice = rs.getInt("productline_price");
                orderList.add(new Order(orderId, customerId, orderAmount, orderDate, orderPrice));

            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl!!!!", e.getMessage());
        }
        return orderList;
    }
//    public static List<Task> viewAllOrders(int userId, ConnectionPool connectionPool) throws DatabaseException
//    {
//        List<Task> taskList = new ArrayList<>();
//        String sql = "select * from task where user_id=? order by name";
//
//        try (
//                Connection connection = connectionPool.getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql)
//        )
//        {
//            ps.setInt(1, userId);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next())
//            {
//                int id = rs.getInt("task_id");
//                String name = rs.getString("name");
//                Boolean done = rs.getBoolean("done");
//                taskList.add(new Task(id, name, done, userId));
//            }
//        }
//        catch (SQLException e)
//        {
//            throw new DatabaseException("Fejl!!!!", e.getMessage());
//        }
//        return taskList;
//    }
//
//    public static Task saveOrder(User user, String taskName, ConnectionPool connectionPool) throws DatabaseException
//    {
//        Task newTask = null;
//
//        String sql = "insert into task (name, done, user_id) values (?,?,?)";
//
//        try (
//                Connection connection = connectionPool.getConnection();
//                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
//        )
//        {
//            ps.setString(1, taskName);
//            ps.setBoolean(2, false);
//            ps.setInt(3, user.getUserId());
//            int rowsAffected = ps.executeUpdate();
//            if (rowsAffected == 1)
//            {
//                ResultSet rs = ps.getGeneratedKeys();
//                rs.next();
//                int newId = rs.getInt(1);
//                newTask = new Task(newId, taskName, false, user.getUserId());
//            } else
//            {
//                throw new DatabaseException("Fejl under indsætning af task: " + taskName);
//            }
//        }
//        catch (SQLException e)
//        {
//            throw new DatabaseException("Fejl i DB connection", e.getMessage());
//        }
//        return newTask;
//    }

    public static void payForOrder(Order newOrder, int customerId, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "update customer set customer.customer_balance where customer_id=?" +
                "INNER JOIN order on order.customer_id = customer=customer_id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setBoolean(1, done);
            ps.setInt(2, taskId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl i opdatering af en task");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl i opdatering af en task");
        }
    }
}
