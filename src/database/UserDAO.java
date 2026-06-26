package database;

import model.user.Admin;
import model.user.Professor;
import model.user.Student;
import model.user.User;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public void create(User user) throws SQLException {
        String sql = "INSERT INTO users (id, name, password, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getId());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getRole());
            stmt.executeUpdate();
        }
    }

    public User findByNameAndPassword(String name, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapToUser(rs);
            }
        }
        return null;
    }

    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapToUser(rs));
            }
        }
        return users;
    }

    private User mapToUser(ResultSet rs) throws SQLException {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String password = rs.getString("password");
        String role = rs.getString("role");

        return switch (role) {
            case "STUDENT" -> new Student(name, password);
            case "PROFESSOR" -> new Professor(name, password);
            case "ADMIN" -> new Admin(name, password);
            default -> throw new SQLException("Unknown role: " + role);
        };
    }
}