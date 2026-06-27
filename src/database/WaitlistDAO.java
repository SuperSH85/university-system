package database;

import java.sql.*;

public class WaitlistDAO {

    public void add(String studentId, String courseId) throws SQLException {
        String sql = "INSERT INTO waitlist (student_id, course_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            stmt.executeUpdate();
        }
    }

    public void remove(String studentId, String courseId) throws SQLException {
        String sql = "DELETE FROM waitlist WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            stmt.executeUpdate();
        }
    }

    public String getFirst(String courseId) throws SQLException {
        String sql = "SELECT student_id FROM waitlist WHERE course_id = ? " +
                "ORDER BY request_time ASC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("student_id");
            }
        }
        return null;
    }
}