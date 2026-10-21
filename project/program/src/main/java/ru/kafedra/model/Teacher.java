package ru.kafedra.model;

public class Teacher {
    private int _teacherId;
    private String _firstName;
    private String _lastName;
    private String _middleName;
    private String _academicDegree;
    private int _workExperience;
    private String _workPosition;

    public Teacher() {}

    public Teacher(int teacherId, String firstName, String lastName, String middleName, String academicDegree, int workExperience, String workPosition) {
        _teacherId = teacherId;
        _firstName = firstName;
        _lastName = lastName;
        _middleName = middleName;
        _academicDegree = academicDegree;
        _workExperience = workExperience;
        _workPosition = workPosition;
    }

    public int getTeacherId() { return _teacherId; }
    public void setTeacherId(int teacherId) { _teacherId = teacherId; }

    public String getFirstName() { return _firstName; }
    public void setFirstName(String firstName) { _firstName = firstName; }

    public String getLastName() { return _lastName; }
    public void setLastName(String lastName) { _lastName = lastName; }

    public String getMiddleName() { return _middleName; }
    public void setMiddleName(String middleName) { _middleName = middleName; }

    public String getAcademicDegree() { return _academicDegree; }
    public void setAcademicDegree(String academicDegree) { _academicDegree = academicDegree; }

    public int getWorkExperience() { return _workExperience; }
    public void setWorkExperience(int workExperience) { _workExperience = workExperience; }

    public String getWorkPosition() { return _workPosition; }
    public void setWorkPosition(String workPosition) { _workPosition = workPosition; }

    @Override
    public String toString() { return _lastName + " " + _firstName + " " + (_middleName != null ? _middleName : ""); }
}