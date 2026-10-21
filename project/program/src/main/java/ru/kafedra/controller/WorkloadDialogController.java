package ru.kafedra.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kafedra.dao.GroupDao;
import ru.kafedra.dao.StandardDao;
import ru.kafedra.dao.TeacherDao;
import ru.kafedra.dao.WorkloadDao;
import ru.kafedra.model.Group;
import ru.kafedra.model.StandardInfo;
import ru.kafedra.model.Teacher;
import ru.kafedra.model.WorkloadInfo;

public class WorkloadDialogController extends Controller{
    @FXML private ComboBox<Teacher> _teacherCombo;
    @FXML private ComboBox<Group> _groupCombo;
    @FXML private ComboBox<StandardInfo> _standardCombo;
    @FXML private TextField _hoursField;

    private final TeacherDao _teacherDao = new TeacherDao();
    private final GroupDao _groupDao = new GroupDao();
    private final StandardDao _standardDao = new StandardDao();
    private final WorkloadDao _workloadDao = new WorkloadDao();

    private Runnable _onSaveCallback;
    private WorkloadInfo _workloadToEdit = null;

    @FXML
    private void initialize() {
        _teacherCombo.setItems(FXCollections.observableArrayList(_teacherDao.findAll()));
        _groupCombo.setItems(FXCollections.observableArrayList(_groupDao.findAll()));
        _standardCombo.setItems(FXCollections.observableArrayList(_standardDao.findAll()));
    }

    public void setOnSaveCallback(Runnable onSaveCallback) {
        _onSaveCallback = onSaveCallback;
    }

    public void setWorkloadToEdit(WorkloadInfo workloadToEdit) {
        _workloadToEdit = workloadToEdit;
        if (_workloadToEdit != null) {
            _hoursField.setText(String.valueOf(_workloadToEdit.getHours()));
            for (Teacher t : _teacherCombo.getItems()) {
                if (t.getTeacherId() == _workloadToEdit.getTeacherId()) { _teacherCombo.setValue(t); break; }
            }
            for (Group g : _groupCombo.getItems()) {
                if (g.getGroupId() == _workloadToEdit.getGroupId()) { _groupCombo.setValue(g); break; }
            }
            for (StandardInfo s : _standardCombo.getItems()) {
                if (s.getStandardId() == _workloadToEdit.getStandardId()) { _standardCombo.setValue(s); break; }
            }
        }
    }

    @FXML
    private void handleSave() {
        Teacher teacher = _teacherCombo.getValue();
        Group group = _groupCombo.getValue();
        StandardInfo standard = _standardCombo.getValue();
        String hoursStr = _hoursField.getText().trim();
        if (teacher == null || group == null || standard == null || hoursStr.isEmpty()) {
            showAlert("Ошибка", "Заполните все поля!");
            return;
        }
        try {
            int hours = Integer.parseInt(hoursStr);
            if (hours <= 0) {
                showAlert("Ошибка", "Часы должны быть больше нуля!");
                return;
            }
            boolean success;
            if (_workloadToEdit == null) {
                success = _workloadDao.insert(teacher.getTeacherId(), group.getGroupId(), standard.getStandardId(), hours);
            } else {
                success = _workloadDao.update(_workloadToEdit.getWorkloadId(), teacher.getTeacherId(), group.getGroupId(), standard.getStandardId(), hours);
            }
            if (success) {
                if (_onSaveCallback != null) _onSaveCallback.run();
                closeWindow();
            } else {
                showAlert("Ошибка", "Не удалось сохранить (возможно, такая запись уже есть).");
            }
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Часы должны быть числом!");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) _hoursField.getScene().getWindow();
        stage.close();
    }
}