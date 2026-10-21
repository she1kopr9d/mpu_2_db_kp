package ru.kafedra.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kafedra.dao.AcademycYearDao;
import ru.kafedra.dao.ClassTypeDao;
import ru.kafedra.dao.StandardDao;
import ru.kafedra.dao.SubjectDao;
import ru.kafedra.model.AcademycYear;
import ru.kafedra.model.ClassType;
import ru.kafedra.model.StandardInfo;
import ru.kafedra.model.Subject;

public class StandardDialogController extends Controller {
    @FXML private ComboBox<Subject> _subjectCombo;
    @FXML private ComboBox<ClassType> _classTypeCombo;
    @FXML private ComboBox<AcademycYear> _yearCombo;
    @FXML private TextField _hoursField;

    private final SubjectDao _subjectDao = new SubjectDao();
    private final ClassTypeDao _classTypeDao = new ClassTypeDao();
    private final AcademycYearDao _yearDao = new AcademycYearDao();
    private final StandardDao _standardDao = new StandardDao();
    
    private Runnable _onSaveCallback;
    private StandardInfo _standardToEdit = null;

    @FXML
    private void initialize() {
        _subjectCombo.setItems(FXCollections.observableArrayList(_subjectDao.findAll()));
        _classTypeCombo.setItems(FXCollections.observableArrayList(_classTypeDao.findAll()));
        _yearCombo.setItems(FXCollections.observableArrayList(_yearDao.findAll()));
    }

    public void setOnSaveCallback(Runnable onSaveCallback) { 
        _onSaveCallback = onSaveCallback; 
    }

    public void setStandardToEdit(StandardInfo standardToEdit) {
        _standardToEdit = standardToEdit;
        _hoursField.setText(String.valueOf(_standardToEdit.getHours()));
        
        for (Subject s : _subjectCombo.getItems()) {
            if (s.getSubjectId() == _standardToEdit.getSubjectId()) { 
                _subjectCombo.setValue(s); 
                break; 
            }
        }
        for (ClassType c : _classTypeCombo.getItems()) {
            if (c.getClassTypeId() == _standardToEdit.getClassTypeId()) { 
                _classTypeCombo.setValue(c); 
                break; 
            }
        }
        for (AcademycYear y : _yearCombo.getItems()) {
            if (y.getAcademicYearId() == _standardToEdit.getAcademicYearId()) { 
                _yearCombo.setValue(y); 
                break; 
            }
        }
    }

    @FXML
    private void handleSave() {
        Subject sub = _subjectCombo.getValue();
        ClassType ct = _classTypeCombo.getValue();
        AcademycYear year = _yearCombo.getValue();
        String hStr = _hoursField.getText().trim();
        if (sub == null || ct == null || year == null || hStr.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Заполните все поля!").showAndWait();
            return;
        }
        try {
            int hours = Integer.parseInt(hStr);
            boolean success;
            if (_standardToEdit == null) {
                success = _standardDao.insert(sub.getSubjectId(), ct.getClassTypeId(), year.getAcademicYearId(), hours);
            } else {
                success = _standardDao.update(_standardToEdit.getStandardId(), sub.getSubjectId(), ct.getClassTypeId(), year.getAcademicYearId(), hours);
            }
            if (success) {
                if (_onSaveCallback != null) _onSaveCallback.run();
                ((Stage) _hoursField.getScene().getWindow()).close();
            } else {
                new Alert(Alert.AlertType.ERROR, "Ошибка (возможно, такой норматив уже есть).").showAndWait();
            }
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Часы должны быть числом!").showAndWait();
        }
    }

    @FXML 
    private void handleCancel() { 
        ((Stage) _hoursField.getScene().getWindow()).close(); 
    }
}