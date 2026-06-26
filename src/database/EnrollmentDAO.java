package database;

import model.course.Course;
import model.course.CourseTime;
import model.user.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
    final int maxCredit = 20;
    public void enroll(String studentId, String courseId) throws SQLException {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            stmt.executeUpdate();
        }
    }

    public void drop(String studentId, String courseId) throws SQLException {
        String sql = "DELETE FROM enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            stmt.executeUpdate();
        }
    }

    public List<Course> findCoursesByStudent(String studentId) throws SQLException {
        String sql = "SELECT c.* FROM courses c " +
                "JOIN enrollments e ON c.course_id = e.course_id " +
                "WHERE e.student_id = ?";
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(mapToCourse(rs));
            }
        }
        return courses;
    }

    private Course mapToCourse(ResultSet rs) throws SQLException {
        String courseId = rs.getString("course_id");
        String title = rs.getString("title");
        int credits = rs.getInt("credits");
        int capacity = rs.getInt("capacity");
        DayOfWeek day = DayOfWeek.valueOf(rs.getString("day_of_week"));
        LocalTime startTime = LocalTime.parse(rs.getString("start_time"));
        LocalTime endTime = LocalTime.parse(rs.getString("end_time"));
        String professorId = rs.getString("professor_id");

        CourseTime schedule = new CourseTime(day, startTime, endTime);

        UserDAO userDAO = new UserDAO();
        Professor professor = (Professor) userDAO.findById(professorId);

        return new Course(courseId, title, credits, capacity, schedule, professor);
    }

    public boolean hasCapacity(String courseId) throws SQLException {
        String sql = "SELECT c.capacity, COUNT(e.student_id) as enrolled " +
                "FROM courses c LEFT JOIN enrollments e ON c.course_id = e.course_id " +
                "WHERE c.course_id = ? GROUP BY c.course_id";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("enrolled") < rs.getInt("capacity");
            }
        }
        return false;
    }


    public boolean isEnrolled(String studentId, String courseId) throws SQLException{
        String sql = "SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            stmt.setString(2, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    public int getTotalCredits(String studentId) throws SQLException {
        String sql = "SELECT COALESCE(SUM(c.credits), 0) as total " +
                "FROM courses c JOIN enrollments e ON c.course_id = e.course_id " +
                "WHERE e.student_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }
}
