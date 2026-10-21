package ru.kafedra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.kafedra.db.DatabaseManager;
import ru.kafedra.model.WorkloadInfo;

public class WorkloadDao {
    private static final String SQL_FIND_ALL = 
            "SELECT w.workload_id, w.teacher_id, w.group_id, w.standard_id, w.hours, " +
            "CONCAT(t.last_name, ' ', t.first_name, ' ', COALESCE(t.middle_name, '')) AS teacher_name, " +
            "g.group_name, s.subject_name, ct.class_type_name, ay.academic_year_name " +
            "FROM `Workload` w " +
            "JOIN `Teacher` t ON w.teacher_id = t.teacher_id " +
            "JOIN `Group` g ON w.group_id = g.group_id " +
            "JOIN `Standard` st ON w.standard_id = st.standard_id " +
            "JOIN `Subject` s ON st.subject_id = s.subject_id " +
            "JOIN `ClassType` ct ON st.class_type_id = ct.class_type_id " +
            "JOIN `AcademycYear` ay ON st.academic_year_id = ay.academic_year_id";

    private static final String SQL_FIND_BY_TEACHER = SQL_FIND_ALL + " WHERE w.teacher_id = ?";
    private static final String SQL_INSERT = "INSERT INTO `Workload` (teacher_id, group_id, standard_id, hours) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE `Workload` SET teacher_id=?, group_id=?, standard_id=?, hours=? WHERE workload_id=?";
    private static final String SQL_DELETE = "DELETE FROM `Workload` WHERE workload_id = ?";

    public List<WorkloadInfo> findAll() {
        return executeQuery(SQL_FIND_ALL, null);
    }

    public List<WorkloadInfo> findByTeacherId(int teacherId) {
        return executeQuery(SQL_FIND_BY_TEACHER, teacherId);
    }

    private List<WorkloadInfo> executeQuery(String query, Integer teacherId) {
        List<WorkloadInfo> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (teacherId != null) stmt.setInt(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new WorkloadInfo(
                            rs.getInt("workload_id"),
                            rs.getInt("teacher_id"),
                            rs.getInt("group_id"),
                            rs.getInt("standard_id"),
                            rs.getString("teacher_name"),
                            rs.getString("group_name"),
                            rs.getString("subject_name"),
                            rs.getString("class_type_name"),
                            rs.getString("academic_year_name"),
                            rs.getInt("hours")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(int teacherId, int groupId, int standardId, int hours) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {
            stmt.setInt(1, teacherId);
            stmt.setInt(2, groupId);
            stmt.setInt(3, standardId);
            stmt.setInt(4, hours);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(int workloadId, int teacherId, int groupId, int standardId, int hours) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            stmt.setInt(1, teacherId);
            stmt.setInt(2, groupId);
            stmt.setInt(3, standardId);
            stmt.setInt(4, hours);
            stmt.setInt(5, workloadId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int workloadId) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setInt(1, workloadId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}