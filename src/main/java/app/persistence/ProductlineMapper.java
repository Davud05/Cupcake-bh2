/* package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductlineMapper {

    public static List<Productline> search(String topName, String bottomName, ConnectionPool connectionPool) throws DatabaseException {
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
                productlineList.add(new Productline(productlineId, topId, bottomId, productlinePrice));
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB Fejl", e.getMessage());
        }
        return productlineList;
    }
    public static Order addToOrder(List<Productline> productlineList, int customerId, ConnectionPool connectionPool) throws DatabaseException
    {
        Order newOrder = null;

        String sql = "Insert into order (name, done, user_id) values (?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        )
        {   ResultSet rs = ps.executeQuery();
            ps.setString(1, taskName);
            ps.setBoolean(2, false);
            ps.setInt(3, user.getUserId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1)
                while (rs.next()) {
                    int topId = rs.getInt("top_id");
                    int bottomId = rs.getInt("bottom_id");
                    int productlinePrice = rs.getInt("top_price") + rs.getInt("bottom_price");
                    int productlineId = rs.getInt("productline_id");
                    productlineList.add(new Productline(productlineId, topId, bottomId, productlinePrice));
                }
            {
                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                int newId = rs.getInt(1);
                newTask = new Task(newId, taskName, false, user.getUserId());
            } else
            {
                throw new DatabaseException("Fejl under indsætning af task: " + taskName);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Fejl i DB connection", e.getMessage());
        }
        return newTask;
    }

    public static void setDoneTo(boolean done, int taskId, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "update task set done = ? where task_id = ?";

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
*/