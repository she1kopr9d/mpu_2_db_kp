package ru.kafedra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.kafedra.db.DatabaseManager;
import ru.kafedra.model.Subject;

public class SubjectDao {
    private static final String SQL_FIND_ALL = "SELECT * FROM `Subject`";
    private static final String SQL_INSERT = "INSERT INTO `Subject` (subject_name) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE `Subject` SET subject_name=? WHERE subject_id=?";
    private static final String SQL_DELETE = "DELETE FROM `Subject` WHERE subject_id=?";

    public List<Subject> findAll() {
        List<Subject> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_FIND_ALL)) {
            while (rs.next()) {
                list.add(new Subject(rs.getInt("subject_id"), rs.getString("subject_name")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean insert(String name) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(int id, String name) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, name);
            stmt.setInt(2, id);
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