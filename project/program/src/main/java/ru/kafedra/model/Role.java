package ru.kafedra.model;

public class Role {
    private int _roleId;
    private String _roleName;

    public Role() {}

    public Role(int roleId, String roleName) {
        _roleId = roleId;
        _roleName = roleName;
    }

    public int getRoleId() { return _roleId; }
    public void setRoleId(int roleId) { _roleId = roleId; }

    public String getRoleName() { return _roleName; }
    public void setRoleName(String roleName) { _roleName = roleName; }

    @Override
    public String toString() { return _roleName; }
}