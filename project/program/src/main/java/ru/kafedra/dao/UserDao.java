package ru.kafedra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import ru.kafedra.db.DatabaseManager;
import ru.kafedra.model.User;

public class UserDao {
    private static final String SQL_FIND_BY_LOGIN = "SELECT * FROM `User` WHERE login = ?";
    private static final String SQL_CREATE_USER = "INSERT INTO `User` (login, password_hash, role_id, teacher_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE `User` SET login=?, password_hash=? WHERE user_id=?";
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM `User`";
    private static final String SQL_UPDATE_TEACHER_LINK = "UPDATE `User` SET teacher_id=? WHERE user_id=?";
    private static final String SQL_DELETE = "DELETE FROM `User` WHERE user_id=?";

    public User findByLogin(String login) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_FIND_BY_LOGIN)) {
            stmt.setString(1, login);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setLogin(rs.getString("login"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setRoleId(rs.getInt("role_id"));
                    int teacherId = rs.getInt("teacher_id");
                    if (!rs.wasNull()) user.setTeacherId(teacherId);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createUser(User user) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_CREATE_USER)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPasswordHash());
            stmt.setInt(3, user.getRoleId());
            if (user.getTeacherId() != null) stmt.setInt(4, user.getTeacherId());
            else stmt.setNull(4, Types.INTEGER);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUser(User user) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_USER)) {
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPasswordHash());
            stmt.setInt(3, user.getUserId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL_FIND_ALL_USERS)) {
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setLogin(rs.getString("login"));
                u.setPasswordHash(rs.getString("password_hash"));
                u.setRoleId(rs.getInt("role_id"));
                int teacherId = rs.getInt("teacher_id");
                if (!rs.wasNull()) u.setTeacherId(teacherId);
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateTeacherLink(int userId, Integer teacherId) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE_TEACHER_LINK)) {
            if (teacherId != null) stmt.setInt(1, teacherId);
            else stmt.setNull(1, Types.INTEGER);
            stmt.setInt(2, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int userId) {
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_DELETE)) {
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}