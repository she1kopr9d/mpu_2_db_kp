package ru.kafedra.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.kafedra.dao.TeacherDao;
import ru.kafedra.dao.UserDao;
import ru.kafedra.model.Teacher;
import ru.kafedra.model.User;

public class UserDialogController extends Controller{
    @FXML private Label _loginLabel;
    @FXML private ComboBox<Teacher> _teacherCombo;

    private final TeacherDao _teacherDao = new TeacherDao();
    private final UserDao _userDao = new UserDao();
    private Runnable _onSaveCallback;
    private User _currentUser;

    public void setUser(User user) {
        this._currentUser = user;
        _loginLabel.setText("Пользователь: " + user.getLogin());
        _teacherCombo.setItems(FXCollections.observableArrayList(_teacherDao.findAll()));
        if (user.getTeacherId() != null) {
            for (Teacher t : _teacherCombo.getItems()) {
                if (t.getTeacherId() == user.getTeacherId()) { _teacherCombo.setValue(t); break; }
            }
        }
    }

    public void setOnSaveCallback(Runnable onSaveCallback) { _onSaveCallback = onSaveCallback; }

    @FXML
    private void handleSave() {
        Teacher selected = _teacherCombo.getValue();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Выберите преподавателя!").showAndWait();
            return;
        }
        if (_userDao.updateTeacherLink(_currentUser.getUserId(), selected.getTeacherId())) {
            if (_onSaveCallback != null) _onSaveCallback.run();
            ((Stage) _loginLabel.getScene().getWindow()).close();
        } else {
            new Alert(Alert.AlertType.ERROR, "Не удалось привязать (возможно, у этого преподавателя уже есть аккаунт).").showAndWait();
        }
    }

    @FXML
    private void handleUnlink() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Отвязать преподавателя от пользователя " + _currentUser.getLogin() + "?");
        confirm.showAndWait().ifPresent(r -> {
            if (r == javafx.scene.control.ButtonType.OK) {
                if (_userDao.updateTeacherLink(_currentUser.getUserId(), null)) {
                    if (_onSaveCallback != null) _onSaveCallback.run();
                    ((Stage) _loginLabel.getScene().getWindow()).close();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Не удалось отвязать.").showAndWait();
                }
            }
        });
    }

    @FXML
    private void handleCancel() {
        ((Stage) _loginLabel.getScene().getWindow()).close();
    }
}