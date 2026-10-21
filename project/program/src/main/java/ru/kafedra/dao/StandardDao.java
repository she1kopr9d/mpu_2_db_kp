package ru.kafedra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ru.kafedra.db.DatabaseManager;
import ru.kafedra.model.StandardInfo;

public class StandardDao {
    private static final String SQL_FIND_ALL =
            "SELECT st.standard_id, st.hours, st.subject_id, st.class_type_id, st.academic_year_id, " +
            "s.subject_name, ct.class_type_name, ay.academic_year_name " +
            "FROM `Standard` st " +
            "JOIN `Subject` s ON st.subject_id = s.subject_id " +
            "JOIN `ClassType` ct ON st.class_type_id = ct.class_type_id " +
            "JOIN `AcademycYear` ay ON st.academic_year_id = ay.academic_year_id";

    private static final String SQL_INSERT = "INSERT INTO `Standard` (subject_id, class_type_id, academic_year_id, hours) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE `Standard` SET subject_id=?, class_type_id=?, academic_year_id=?, hours=? WHERE standard_id=?";
    private static final String SQL_DELETE = "DELETE FROM `Standard` WHERE standard_id=?";

    public List<StandardInfo> findAll() {
        List<StandardInfo> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_FIND_ALL)) {
            while (rs.next()) {
                String display = rs.getString("subject_name") + " - " +
                                 rs.getString("class_type_name") + " (" +
                                 rs.getString("academic_year_name") + ")";
                list.add(new StandardInfo(
                        rs.getInt("standard_id"),
                        rs.getInt("subject_id"),
                        rs.getInt("class_type_id"),
                        rs.getInt("academic_year_id"),
                        display,
                        rs.getInt("hours")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(int subjectId, int classTypeId, int yearId, int hours) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setInt(1, subjectId);
            stmt.setInt(2, classTypeId);
            stmt.setInt(3, yearId);
            stmt.setInt(4, hours);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(int id, int subjectId, int classTypeId, int yearId, int hours) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setInt(1, subjectId);
            stmt.setInt(2, classTypeId);
            stmt.setInt(3, yearId);
            stmt.setInt(4, hours);
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}