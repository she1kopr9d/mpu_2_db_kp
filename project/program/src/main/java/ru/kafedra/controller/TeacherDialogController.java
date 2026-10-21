package ru.kafedra.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kafedra.dao.TeacherDao;
import ru.kafedra.model.Teacher;

public class TeacherDialogController extends Controller {
    @FXML private Label _titleLabel;
    @FXML private TextField _lastNameField, _firstNameField, _middleNameField;
    @FXML private TextField _degreeField, _experienceField, _positionField;

    private final TeacherDao _teacherDao = new TeacherDao();
    private Runnable _onSaveCallback;
    private Teacher _teacherToEdit = null;

    public void setOnSaveCallback(Runnable _onSaveCallback) { this._onSaveCallback = _onSaveCallback; }

    public void setTeacherToEdit(Teacher teacherToEdit) {
        _teacherToEdit = teacherToEdit;
        if (_teacherToEdit != null) {
            _titleLabel.setText("Редактирование преподавателя");
            _lastNameField.setText(_teacherToEdit.getLastName());
            _firstNameField.setText(_teacherToEdit.getFirstName());
            _middleNameField.setText(_teacherToEdit.getMiddleName());
            _degreeField.setText(_teacherToEdit.getAcademicDegree());
            _experienceField.setText(String.valueOf(_teacherToEdit.getWorkExperience()));
            _positionField.setText(_teacherToEdit.getWorkPosition());
        }
    }

    @FXML
    private void handleSave() {
        String lastName = _lastNameField.getText().trim();
        String firstName = _firstNameField.getText().trim();
        if (lastName.isEmpty() || firstName.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Фамилия и Имя обязательны!").showAndWait();
            return;
        }
        Teacher t = new Teacher();
        t.setLastName(lastName);
        t.setFirstName(firstName);
        t.setMiddleName(_middleNameField.getText().trim());
        t.setAcademicDegree(_degreeField.getText().trim());
        t.setWorkPosition(_positionField.getText().trim());
        try {
            String expText = _experienceField.getText().trim();
            t.setWorkExperience(expText.isEmpty() ? 0 : Integer.parseInt(expText));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Стаж должен быть числом!").showAndWait();
            return;
        }
        boolean success;
        if (_teacherToEdit == null) {
            success = _teacherDao.insert(t);
        } else {
            t.setTeacherId(_teacherToEdit.getTeacherId());
            success = _teacherDao.update(t);
        }
        if (success) {
            if (_onSaveCallback != null) _onSaveCallback.run();
            ((Stage) _lastNameField.getScene().getWindow()).close();
        } else {
            new Alert(Alert.AlertType.ERROR, "Не удалось сохранить данные.").showAndWait();
        }
    }

    @FXML
    private void handleCancel() {
        ((Stage) _lastNameField.getScene().getWindow()).close();
    }
}