package ru.kafedra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.kafedra.db.DatabaseManager;
import ru.kafedra.model.AcademycYear;

public class AcademycYearDao {
    private static final String SQL_FIND_ALL = "SELECT * FROM `AcademycYear`";
    private static final String SQL_INSERT = "INSERT INTO `AcademycYear` (academic_year_name) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE `AcademycYear` SET academic_year_name=? WHERE academic_year_id=?";
    private static final String SQL_DELETE = "DELETE FROM `AcademycYear` WHERE academic_year_id=?";

    public List<AcademycYear> findAll() {
        List<AcademycYear> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_FIND_ALL)) {
            while (rs.next()) list.add(new AcademycYear(rs.getInt("academic_year_id"), rs.getString("academic_year_name")));
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