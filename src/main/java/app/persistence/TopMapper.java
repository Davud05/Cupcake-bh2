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

    public List<Top> searchTopsByName(String topName, ConnectionPool connectionPool) throws DatabaseException {
        List<Top> tops = new ArrayList<>();
        String sql = "SELECT * FROM top WHERE topName LIKE ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + topName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tops.add(new Top(rs.getInt("topId"), rs.getInt("topPrice"), rs.getString("topName")));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not search tops by name", e.getMessage());
        }
        return tops;
    }
}