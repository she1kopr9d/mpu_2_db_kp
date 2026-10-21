package ru.kafedra.model;

public class Group {
    private int _groupId;
    private String _groupName;

    public Group() {}

    public Group(int groupId, String groupName) {
        _groupId = groupId;
        _groupName = groupName;
    }

    public int getGroupId() { return _groupId; }
    public void setGroupId(int groupId) { _groupId = groupId; }

    public String getGroupName() { return _groupName; }
    public void setGroupName(String groupName) { _groupName = groupName; }

    @Override
    public String toString() { return _groupName; }
}