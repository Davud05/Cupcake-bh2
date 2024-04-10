package app.persistence;

import app.entities.Top;
import app.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TopMapper {



    public List<Top> findAllTops(ConnectionPool connectionPool) throws DatabaseException {
        List<Top> tops = new ArrayList<>();
        String sql = "SELECT * FROM top";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tops.add(new Top(rs.getInt("topId"), rs.getInt("topPrice"), rs.getString("topName")));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not retrieve all tops", e.getMessage());
        }
        return tops;
    }
}

