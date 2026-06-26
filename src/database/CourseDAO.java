package database;

import model.course.Course;
import model.course.CourseTime;
import model.user.Professor;
import model.user.User;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public void create(Course course) throws SQLException {
        String sql = "INSERT INTO courses (course_id, title, credits, capacity, day_of_week, start_time, end_time, professor_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getCourseID());
            stmt.setString(2, course.getTitle());
            stmt.setInt(3, course.getCredits());
            stmt.setInt(4, course.getCapacity());
            stmt.setString(5, course.getSchedule().getDay().name());
            stmt.setString(6, course.getSchedule().getStartTime().toString());
            stmt.setString(7, course.getSchedule().getEndTime().toString());
            stmt.setString(8, course.getProfessor().getId());
            stmt.executeUpdate();
        }
    }

    public List<Course> findAll() throws SQLException {
        String sql = "SELECT * FROM courses";
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                courses.add(mapToCourse(rs));
            }
        }
        return courses;
    }

    public List<Course> findByProfessor(String professorId) throws SQLException {
        String sql = "SELECT * FROM courses WHERE professor_id = ?";
        List<Course> courses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, professorId);
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
}