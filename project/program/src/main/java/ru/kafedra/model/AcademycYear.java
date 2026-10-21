package ru.kafedra.model;

public class AcademycYear {
    private int _academicYearId;
    private String _academicYearName;

    public AcademycYear() {}

    public AcademycYear(int academicYearId, String academicYearName) {
        _academicYearId = academicYearId;
        _academicYearName = academicYearName;
    }

    public int getAcademicYearId() { return _academicYearId; }
    public void setAcademicYearId(int academicYearId) { _academicYearId = academicYearId; }

    public String getAcademicYearName() { return _academicYearName; }
    public void setAcademicYearName(String academicYearName) { _academicYearName = academicYearName; }

    @Override
    public String toString() { return _academicYearName; }
}