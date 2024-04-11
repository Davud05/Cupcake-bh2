package app.persistence;

import app.entities.Admin;
import app.entities.Customer;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminMapper
{

    public static Admin login(String adminEmail, String adminPassword, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "select * from admin where admin_email=? and admin_password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, adminEmail);
            ps.setString(2, adminPassword);


            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                int id = rs.getInt("admin_id");
                // String role = rs.getString("role");
                return new Admin(id, adminEmail, adminPassword);
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

    public static void createAdmin(String adminEmail, String adminPassword, String adminName, ConnectionPool connectionPool) throws DatabaseException
    {
        String sql = "insert into admin (admin_email, admin_password, admin_name) values (?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        )
        {
            ps.setString(1, adminEmail);
            ps.setString(2, adminPassword);
            ps.setString(3, adminName);


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
                msg = "Admin kunne ikke findes allerede. Vælg et andet";
            }
            throw new DatabaseException(msg, e.getMessage());
        }
    }
}