package ru.kafedra.controller;

import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kafedra.dao.UserDao;
import ru.kafedra.model.User;

public class RegisterController extends Controller {
    @FXML private TextField _loginField;
    @FXML private PasswordField _passwordField;

    private final UserDao _userDao = new UserDao();
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}$");

    @FXML
    private void handleRegister() {
        String login = _loginField.getText().trim();
        String password = _passwordField.getText();

        if (login.isEmpty() || password.isEmpty()) {
            showAlert("Ошибка", "Заполните логин и пароль!");
            return;
        }
        
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            showAlert("Ошибка", "Пароль должен содержать минимум 8 символов, 1 цифру, 1 заглавную букву и 1 спецсимвол.");
            return;
        }
        
        if (_userDao.findByLogin(login) != null) {
            showAlert("Ошибка", "Пользователь с таким логином уже существует!");
            return;
        }

        User user = new User();
        user.setLogin(login);
        user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        user.setRoleId(2);
        user.setTeacherId(null);
        
        if (_userDao.createUser(user)) {
            showAlert("Успех", "Регистрация прошла успешно!");
            handleBack();
        } else {
            showAlert("Ошибка", "Не удалось создать пользователя.");
        }
    }

    @FXML
    private void handleBack() {
        try {
            Stage stage = (Stage) _loginField.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/login.fxml"));
            stage.setScene(new Scene(loader.load(), 400, 350));
            stage.setTitle("Авторизация");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}