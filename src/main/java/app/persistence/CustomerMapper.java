package app.persistence;

import app.entities.Customer;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper
{

    public static Customer login(String customerEmail, String customerPassword, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "select * from customer where customer_email=? and customer_password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, customerEmail);
            ps.setString(2, customerPassword);


            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int id = rs.getInt("customer_id");
               // String role = rs.getString("role");
                return new Customer(id, customerEmail, customerPassword);
            } else
            {
                throw new DatabaseException("Fejl i login. Prøv igen"); // printes ud både på hjemmesiden og i serverkonsollen, når vi skriver forkert login
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("DB fejl", e.getMessage());
        }
    }

    public static void createCustomer(String customerEmail, String customerPassword, String customerName, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "insert into customer (customer_email, customer_password, customer_name) values (?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(4, customerEmail);
            ps.setString(3, customerPassword);
            ps.setString(2, customerName);


            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1)
            {
                throw new DatabaseException("Fejl ved oprettelse af ny kunde");
            }
        }
        catch (SQLException e)
        {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value "))
            {
                msg = "Kundenavnet findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }
}