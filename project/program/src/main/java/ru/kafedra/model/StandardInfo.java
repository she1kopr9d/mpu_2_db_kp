package ru.kafedra.model;

public class StandardInfo {
    private int _standardId;
    private int _subjectId;
    private int _classTypeId;
    private int _academicYearId;
    private String _displayName;
    private int _hours;

    public StandardInfo(int standardId, int subjectId, int classTypeId, int academicYearId, String displayName, int hours) {
        _standardId = standardId;
        _subjectId = subjectId;
        _classTypeId = classTypeId;
        _academicYearId = academicYearId;
        _displayName = displayName;
        _hours = hours;
    }

    public int getStandardId() { return _standardId; }
    public int getSubjectId() { return _subjectId; }
    public int getClassTypeId() { return _classTypeId; }
    public int getAcademicYearId() { return _academicYearId; }
    public String getDisplayName() { return _displayName; }
    public int getHours() { return _hours; }

    @Override
    public String toString() { return _displayName; }
}