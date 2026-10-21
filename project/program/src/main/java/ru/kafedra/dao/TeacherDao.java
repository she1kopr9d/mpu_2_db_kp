package ru.kafedra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.kafedra.db.DatabaseManager;
import ru.kafedra.model.Teacher;

public class TeacherDao {
    private static final String SQL_FIND_ALL = "SELECT * FROM `Teacher`";
    private static final String SQL_CREATE_TEACHER = "INSERT INTO `Teacher` (first_name, last_name, middle_name, academic_degree, work_experience, work_position) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_INSERT = "INSERT INTO `Teacher` (first_name, last_name, middle_name, academic_degree, work_experience, work_position) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE `Teacher` SET first_name=?, last_name=?, middle_name=?, academic_degree=?, work_experience=?, work_position=? WHERE teacher_id=?";
    private static final String SQL_DELETE = "DELETE FROM `Teacher` WHERE teacher_id=?";

    public List<Teacher> findAll() {
        List<Teacher> teachers = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_FIND_ALL)) {
            while (rs.next()) {
                Teacher t = new Teacher();
                t.setTeacherId(rs.getInt("teacher_id"));
                t.setFirstName(rs.getString("first_name"));
                t.setLastName(rs.getString("last_name"));
                t.setMiddleName(rs.getString("middle_name"));
                t.setAcademicDegree(rs.getString("academic_degree"));
                t.setWorkExperience(rs.getInt("work_experience"));
                t.setWorkPosition(rs.getString("work_position"));
                teachers.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public int createTeacher(Teacher teacher) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_CREATE_TEACHER, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, teacher.getFirstName());
            stmt.setString(2, teacher.getLastName());
            stmt.setString(3, teacher.getMiddleName());
            stmt.setString(4, teacher.getAcademicDegree());
            stmt.setInt(5, teacher.getWorkExperience());
            stmt.setString(6, teacher.getWorkPosition());
            if (stmt.executeUpdate() > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean insert(Teacher t) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, t.getFirstName());
            stmt.setString(2, t.getLastName());
            stmt.setString(3, t.getMiddleName());
            stmt.setString(4, t.getAcademicDegree());
            stmt.setInt(5, t.getWorkExperience());
            stmt.setString(6, t.getWorkPosition());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(Teacher t) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, t.getFirstName());
            stmt.setString(2, t.getLastName());
            stmt.setString(3, t.getMiddleName());
            stmt.setString(4, t.getAcademicDegree());
            stmt.setInt(5, t.getWorkExperience());
            stmt.setString(6, t.getWorkPosition());
            stmt.setInt(7, t.getTeacherId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(int id) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}