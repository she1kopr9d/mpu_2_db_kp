package ru.kafedra.model;

public class Standard {
    private int _standardId;
    private int _subjectId;
    private int _classTypeId;
    private int _academicYearId;
    private int _hours;

    public Standard() {}

    public Standard(int standardId, int subjectId, int classTypeId, int academicYearId, int hours) {
        _standardId = standardId;
        _subjectId = subjectId;
        _classTypeId = classTypeId;
        _academicYearId = academicYearId;
        _hours = hours;
    }

    public int getStandardId() { return _standardId; }
    public void setStandardId(int standardId) { _standardId = standardId; }

    public int getSubjectId() { return _subjectId; }
    public void setSubjectId(int subjectId) { _subjectId = subjectId; }

    public int getClassTypeId() { return _classTypeId; }
    public void setClassTypeId(int classTypeId) { _classTypeId = classTypeId; }

    public int getAcademicYearId() { return _academicYearId; }
    public void setAcademicYearId(int academicYearId) { _academicYearId = academicYearId; }

    public int getHours() { return _hours; }
    public void setHours(int hours) { _hours = hours; }

    @Override
    public String toString() { return "Standard ID: " + _standardId; }
}