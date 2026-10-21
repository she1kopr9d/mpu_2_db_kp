package ru.kafedra.model;

public class Subject {
    private int _subjectId;
    private String _subjectName;

    public Subject() {}

    public Subject(int subjectId, String subjectName) {
        _subjectId = subjectId;
        _subjectName = subjectName;
    }

    public int getSubjectId() { return _subjectId; }
    public void setSubjectId(int subjectId) { _subjectId = subjectId; }

    public String getSubjectName() { return _subjectName; }
    public void setSubjectName(String subjectName) { _subjectName = subjectName; }

    @Override
    public String toString() { return _subjectName; }
}