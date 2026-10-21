package ru.kafedra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.kafedra.db.DatabaseManager;
import ru.kafedra.model.ClassType;

public class ClassTypeDao {
    private static final String SQL_FIND_ALL = "SELECT * FROM `ClassType`";
    private static final String SQL_INSERT = "INSERT INTO `ClassType` (class_type_name) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE `ClassType` SET class_type_name=? WHERE class_type_id=?";
    private static final String SQL_DELETE = "DELETE FROM `ClassType` WHERE class_type_id=?";

    public List<ClassType> findAll() {
        List<ClassType> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_FIND_ALL)) {
            while (rs.next()) list.add(new ClassType(rs.getInt("class_type_id"), rs.getString("class_type_name")));
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

    public boolean delete(int id) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean update(int id, String name) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setString(1, name); stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}