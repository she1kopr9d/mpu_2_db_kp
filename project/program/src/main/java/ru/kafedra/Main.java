package ru.kafedra;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ru.kafedra.db.DatabaseManager;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        String statusText = "Проверка БД...";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();   
            try (
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM Teacher");
            ) {
                if (rs.next()) {
                    int teacherCount = rs.getInt(1);
                    statusText = "Преподавателей в базе: " + teacherCount;
                }
            }
        } catch (Exception e) {
            statusText = "Ошибка подключения к БД:\n" + e.getMessage();
            e.printStackTrace();
        }
        Label label = new Label(statusText);
        label.setStyle("-fx-font-size: 16px; -fx-text-alignment: center;");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 600, 300);
        primaryStage.setTitle("Моя Кафедра - Тест БД");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}