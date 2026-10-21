package ru.kafedra.model;

public class ClassType {
    private int _classTypeId;
    private String _classTypeName;

    public ClassType() {}

    public ClassType(int classTypeId, String classTypeName) {
        _classTypeId = classTypeId;
        _classTypeName = classTypeName;
    }

    public int getClassTypeId() { return _classTypeId; }
    public void setClassTypeId(int classTypeId) { _classTypeId = classTypeId; }

    public String getClassTypeName() { return _classTypeName; }
    public void setClassTypeName(String classTypeName) { _classTypeName = classTypeName; }

    @Override
    public String toString() { return _classTypeName; }
}