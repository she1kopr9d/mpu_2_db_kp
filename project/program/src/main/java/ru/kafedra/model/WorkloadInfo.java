package ru.kafedra.model;

public class WorkloadInfo {
    private int _workloadId;
    private int _teacherId;
    private int _groupId;
    private int _standardId;
    private String _teacherName;
    private String _groupName;
    private String _subjectName;
    private String _classTypeName;
    private String _academicYearName;
    private int _hours;

    public WorkloadInfo() {}

    public WorkloadInfo(int workloadId, int teacherId, int groupId, int standardId, String teacherName, String groupName, String subjectName, String classTypeName, String academicYearName, int hours) {
        _workloadId = workloadId;
        _teacherId = teacherId;
        _groupId = groupId;
        _standardId = standardId;
        _teacherName = teacherName;
        _groupName = groupName;
        _subjectName = subjectName;
        _classTypeName = classTypeName;
        _academicYearName = academicYearName;
        _hours = hours;
    }

    public int getWorkloadId() { return _workloadId; }
    public void setWorkloadId(int workloadId) { _workloadId = workloadId; }
    public int getTeacherId() { return _teacherId; }
    public void setTeacherId(int teacherId) { _teacherId = teacherId; }
    public int getGroupId() { return _groupId; }
    public void setGroupId(int groupId) { _groupId = groupId; }
    public int getStandardId() { return _standardId; }
    public void setStandardId(int standardId) { _standardId = standardId; }
    public String getTeacherName() { return _teacherName; }
    public void setTeacherName(String teacherName) { _teacherName = teacherName; }
    public String getGroupName() { return _groupName; }
    public void setGroupName(String groupName) { _groupName = groupName; }
    public String getSubjectName() { return _subjectName; }
    public void setSubjectName(String subjectName) { _subjectName = subjectName; }
    public String getClassTypeName() { return _classTypeName; }
    public void setClassTypeName(String classTypeName) { _classTypeName = classTypeName; }
    public String getAcademicYearName() { return _academicYearName; }
    public void setAcademicYearName(String academicYearName) { _academicYearName = academicYearName; }
    public int getHours() { return _hours; }
    public void setHours(int hours) { _hours = hours; }
}