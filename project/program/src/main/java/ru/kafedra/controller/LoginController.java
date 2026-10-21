package ru.kafedra.controller;

import org.mindrot.jbcrypt.BCrypt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kafedra.dao.UserDao;
import ru.kafedra.model.User;
import ru.kafedra.util.SessionManager;

public class LoginController extends Controller {
    @FXML private TextField _loginField;
    @FXML private PasswordField _passwordField;
    @FXML private Button _loginButton;
    @FXML private Button _registerButton;

    private final UserDao _userDao = new UserDao();

    @FXML
    private void handleLogin() {
        String login = _loginField.getText().trim();
        String password = _passwordField.getText();
        if (login.isEmpty() || password.isEmpty()) {
            showAlert("Ошибка", "Заполните все поля!");
            return;
        }
        User user = _userDao.findByLogin(login);
        if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
            SessionManager.setCurrentUser(user);
            openMainWindow();
        } else {
            showAlert("Ошибка", "Неверный логин или пароль!");
        }
    }

    @FXML
    private void handleRegister() {
        try {
            Stage stage = (Stage) _loginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/register.fxml"));
            stage.setScene(new Scene(loader.load(), 400, 350));
            stage.setTitle("Регистрация");
            stage.centerOnScreen();
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }

    private void openMainWindow() {
        try {
            Stage stage = (Stage) _loginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/main.fxml"));
            stage.setScene(new Scene(loader.load(), 800, 600));
            stage.setTitle("Моя Кафедра - Главное окно");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}