package app.persistence;

import app.entities.Bottom;
import app.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BottomMapper {
    public List<Bottom> searchBottomsByName(String bottomName, ConnectionPool connectionPool) throws DatabaseException {
        List<Bottom> bottoms = new ArrayList<>();
        String sql = "SELECT * FROM bottom WHERE bottomName LIKE ?";
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + bottomName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bottoms.add(new Bottom(rs.getInt("bottomId"), rs.getInt("bottomPrice"), rs.getString("bottomName")));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not search bottoms by name", e.getMessage());
        }
        return bottoms;
    }
}
