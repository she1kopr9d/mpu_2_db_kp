package ru.kafedra.model;

public class Workload {
    private int _workloadId;
    private int _teacherId;
    private int _groupId;
    private int _standardId;
    private int _hours;

    public Workload() {}

    public Workload(int workloadId, int teacherId, int groupId, int standardId, int hours) {
        _workloadId = workloadId;
        _teacherId = teacherId;
        _groupId = groupId;
        _standardId = standardId;
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

    public int getHours() { return _hours; }
    public void setHours(int hours) { _hours = hours; }

    @Override
    public String toString() { return "Workload ID: " + _workloadId; }
}