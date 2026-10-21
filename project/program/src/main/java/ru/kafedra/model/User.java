package ru.kafedra.model;

public class User {
    private int _userId;
    private String _login;
    private String _passwordHash;
    private int _roleId;
    private Integer _teacherId;

    public User() {}

    public User(int userId, String login, String passwordHash, int roleId, Integer teacherId) {
        _userId = userId;
        _login = login;
        _passwordHash = passwordHash;
        _roleId = roleId;
        _teacherId = teacherId;
    }

    public int getUserId() { return _userId; }
    public void setUserId(int userId) { _userId = userId; }

    public String getLogin() { return _login; }
    public void setLogin(String login) { _login = login; }

    public String getPasswordHash() { return _passwordHash; }
    public void setPasswordHash(String passwordHash) { _passwordHash = passwordHash; }

    public int getRoleId() { return _roleId; }
    public void setRoleId(int roleId) { _roleId = roleId; }

    public Integer getTeacherId() { return _teacherId; }
    public void setTeacherId(Integer teacherId) { _teacherId = teacherId; }

    @Override
    public String toString() { return _login; }
}