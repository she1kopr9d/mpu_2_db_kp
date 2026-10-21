package ru.kafedra.controller;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kafedra.dao.UserDao;
import ru.kafedra.model.User;
import ru.kafedra.util.SessionManager;

public class ProfileDialogController extends Controller {
    @FXML private TextField _loginField;
    @FXML private PasswordField _oldPasswordField;
    @FXML private PasswordField _newPasswordField;

    private final UserDao _userDao = new UserDao();
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$");

    @FXML
    private void initialize() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            _loginField.setText(currentUser.getLogin());
        }
    }

    @FXML
    private void handleSave() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) return;
        String newLogin = _loginField.getText().trim();
        String oldPassword = _oldPasswordField.getText();
        String newPassword = _newPasswordField.getText();
        if (newLogin.isEmpty()) {
            showAlert("Ошибка", "Логин не может быть пустым!");
            return;
        }
        if (!BCrypt.checkpw(oldPassword, currentUser.getPasswordHash())) {
            showAlert("Ошибка", "Старый пароль введен неверно!");
            return;
        }
        String finalHash = currentUser.getPasswordHash();
        if (!newPassword.isEmpty()) {
            if (!PASSWORD_PATTERN.matcher(newPassword).matches()) {
                showAlert("Ошибка", "Новый пароль слишком простой! Требуется 8+ символов, 1 цифра, 1 заглавная, 1 спецсимвол.");
                return;
            }
            finalHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        }
        if (!newLogin.equals(currentUser.getLogin())) {
            User existing = _userDao.findByLogin(newLogin);
            if (existing != null && existing.getUserId() != currentUser.getUserId()) {
                showAlert("Ошибка", "Пользователь с таким логином уже существует!");
                return;
            }
        }
        currentUser.setLogin(newLogin);
        currentUser.setPasswordHash(finalHash);
        if (_userDao.updateUser(currentUser)) {
            showAlert("Успех", "Данные профиля успешно обновлены!");
            closeWindow();
        } else {
            showAlert("Ошибка", "Не удалось обновить данные.");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) _loginField.getScene().getWindow();
        stage.close();
    }
}