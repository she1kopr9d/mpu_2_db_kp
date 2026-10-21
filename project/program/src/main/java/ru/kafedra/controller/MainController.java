package ru.kafedra.controller;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kafedra.dao.AcademycYearDao;
import ru.kafedra.dao.ClassTypeDao;
import ru.kafedra.dao.GroupDao;
import ru.kafedra.dao.StandardDao;
import ru.kafedra.dao.SubjectDao;
import ru.kafedra.dao.TeacherDao;
import ru.kafedra.dao.UserDao;
import ru.kafedra.dao.WorkloadDao;
import ru.kafedra.model.AcademycYear;
import ru.kafedra.model.ClassType;
import ru.kafedra.model.Group;
import ru.kafedra.model.StandardInfo;
import ru.kafedra.model.Subject;
import ru.kafedra.model.Teacher;
import ru.kafedra.model.User;
import ru.kafedra.model.WorkloadInfo;
import ru.kafedra.util.SessionManager;

public class MainController extends Controller {
    @FXML private Label _welcomeLabel;
    @FXML private TabPane _tabPane;
    @FXML private Tab _adminTab;
    @FXML private Tab _myWorkloadTab;
    @FXML private HBox _adminButtonsBox;

    @FXML private TableView<WorkloadInfo> _workloadTable;
    @FXML private TableColumn<WorkloadInfo, String> _colTeacher;
    @FXML private TableColumn<WorkloadInfo, String> _colSubject;
    @FXML private TableColumn<WorkloadInfo, String> _colClassType;
    @FXML private TableColumn<WorkloadInfo, String> _colGroup;
    @FXML private TableColumn<WorkloadInfo, String> _colYear;
    @FXML private TableColumn<WorkloadInfo, Integer> _colHours;

    @FXML private TableView<Teacher> _teacherTable;
    @FXML private TableColumn<Teacher, String> _colTLastName;
    @FXML private TableColumn<Teacher, String> _colTFirstName;
    @FXML private TableColumn<Teacher, String> _colTMiddleName;
    @FXML private TableColumn<Teacher, String> _colTDegree;
    @FXML private TableColumn<Teacher, Integer> _colTExperience;
    @FXML private TableColumn<Teacher, String> _colTPosition;

    @FXML private TableView<Subject> _subjectTable;
    @FXML private TableColumn<Subject, String> _colSubjectName;

    @FXML private TableView<Group> _groupTable;
    @FXML private TableColumn<Group, String> _colGroupName;

    @FXML private TableView<StandardInfo> _standardTable;
    @FXML private TableColumn<StandardInfo, String> _colStandardName;

    @FXML private TableView<ClassType> _classTypeTable;
    @FXML private TableColumn<ClassType, String> _colClassTypeName;

    @FXML private TableView<AcademycYear> _yearTable;
    @FXML private TableColumn<AcademycYear, String> _colYearName;

    @FXML private TableView<User> _userTable;
    @FXML private TableColumn<User, String> _colUserLogin;
    @FXML private TableColumn<User, String> _colUserRole;
    @FXML private TableColumn<User, String> _colUserTeacher;

    private final WorkloadDao _workloadDao = new WorkloadDao();
    private final TeacherDao _teacherDao = new TeacherDao();
    private final SubjectDao _subjectDao = new SubjectDao();
    private final GroupDao _groupDao = new GroupDao();
    private final StandardDao _standardDao = new StandardDao();
    private final ClassTypeDao _classTypeDao = new ClassTypeDao();
    private final AcademycYearDao _yearDao = new AcademycYearDao();
    private final UserDao _userDao = new UserDao();

    @FXML
    private void initialize() {
        User user = SessionManager.getCurrentUser();
        if (user != null) {
            String role = user.getRoleId() == 1 ? "Администратор" : "Преподаватель";
            _welcomeLabel.setText("Вы вошли как: " + user.getLogin() + " (" + role + ")");
            if (user.getRoleId() != 1) {
                _tabPane.getTabs().remove(_adminTab);
                _colTeacher.setVisible(false);
                _adminButtonsBox.setVisible(false);
                _adminButtonsBox.setManaged(false);
            }
            setupWorkloadTable();
            loadWorkloadData(user);
            if (user.getRoleId() == 1) {
                setupDictionaryTables();
            }
        }
    }

    private void setupWorkloadTable() {
        _colTeacher.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        _colSubject.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        _colClassType.setCellValueFactory(new PropertyValueFactory<>("classTypeName"));
        _colGroup.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        _colYear.setCellValueFactory(new PropertyValueFactory<>("academicYearName"));
        _colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
    }

    private void loadWorkloadData(User user) {
        List<WorkloadInfo> data;
        if (user.getRoleId() == 1) {
            data = _workloadDao.findAll();
        } else {
            if (user.getTeacherId() != null) {
                data = _workloadDao.findByTeacherId(user.getTeacherId());
            } else {
                data = java.util.Collections.emptyList();
            }
        }
        _workloadTable.setItems(FXCollections.observableArrayList(data));
    }

    private void setupDictionaryTables() {
        _colTLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        _colTFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        _colTMiddleName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        _colTDegree.setCellValueFactory(new PropertyValueFactory<>("academicDegree"));
        _colTExperience.setCellValueFactory(new PropertyValueFactory<>("workExperience"));
        _colTPosition.setCellValueFactory(new PropertyValueFactory<>("workPosition"));
        _teacherTable.setItems(FXCollections.observableArrayList(_teacherDao.findAll()));
        _colSubjectName.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        _subjectTable.setItems(FXCollections.observableArrayList(_subjectDao.findAll()));
        _colGroupName.setCellValueFactory(new PropertyValueFactory<>("groupName"));
        _groupTable.setItems(FXCollections.observableArrayList(_groupDao.findAll()));
        _colStandardName.setCellValueFactory(new PropertyValueFactory<>("displayName"));
        _standardTable.setItems(FXCollections.observableArrayList(_standardDao.findAll()));
        _colClassTypeName.setCellValueFactory(new PropertyValueFactory<>("classTypeName"));
        _classTypeTable.setItems(FXCollections.observableArrayList(_classTypeDao.findAll()));
        _colYearName.setCellValueFactory(new PropertyValueFactory<>("academicYearName"));
        _yearTable.setItems(FXCollections.observableArrayList(_yearDao.findAll()));
        _colUserLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        _colUserRole.setCellValueFactory(cd -> new SimpleStringProperty(
                cd.getValue().getRoleId() == 1 ? "Администратор" : "Преподаватель"));
        _colUserTeacher.setCellValueFactory(cd -> {
            Integer tid = cd.getValue().getTeacherId();
            if (tid == null) return new SimpleStringProperty("—");
            for (Teacher t : _teacherTable.getItems()) {
                if (t.getTeacherId() == tid) {
                    return new SimpleStringProperty(t.getLastName() + " " + t.getFirstName());
                }
            }
            return new SimpleStringProperty("ID: " + tid);
        });
        _userTable.setItems(FXCollections.observableArrayList(_userDao.findAll()));
    }

    @FXML
    private void handleAddWorkload() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/workload_dialog.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Добавление нагрузки");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            WorkloadDialogController controller = loader.getController();
            controller.setOnSaveCallback(() -> loadWorkloadData(SessionManager.getCurrentUser()));
            stage.showAndWait();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleEditWorkload() {
        WorkloadInfo selected = _workloadTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Выберите строку для редактирования!").showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/workload_dialog.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Редактирование нагрузки");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            WorkloadDialogController controller = loader.getController();
            controller.setWorkloadToEdit(selected);
            controller.setOnSaveCallback(() -> loadWorkloadData(SessionManager.getCurrentUser()));
            stage.showAndWait();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleDeleteWorkload() {
        WorkloadInfo selected = _workloadTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Выберите строку для удаления!").showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Удалить выбранную нагрузку?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                if (_workloadDao.delete(selected.getWorkloadId())) {
                    loadWorkloadData(SessionManager.getCurrentUser());
                } else {
                    new Alert(Alert.AlertType.ERROR, "Не удалось удалить!").showAndWait();
                }
            }
        });
    }

    @FXML
    private void handleAddTeacher() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/teacher_dialog.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Добавление преподавателя");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            TeacherDialogController controller = loader.getController();
            controller.setOnSaveCallback(() -> _teacherTable.setItems(FXCollections.observableArrayList(_teacherDao.findAll())));
            stage.showAndWait();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleEditTeacher() {
        Teacher selected = _teacherTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Выберите преподавателя для редактирования!").showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/teacher_dialog.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Редактирование преподавателя");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            TeacherDialogController controller = loader.getController();
            controller.setTeacherToEdit(selected);
            controller.setOnSaveCallback(() -> _teacherTable.setItems(FXCollections.observableArrayList(_teacherDao.findAll())));
            stage.showAndWait();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleDeleteTeacher() {
        Teacher selected = _teacherTable.getSelectionModel().getSelectedItem();
        if (selected != null && _teacherDao.delete(selected.getTeacherId())) {
            _teacherTable.setItems(FXCollections.observableArrayList(_teacherDao.findAll()));
        }
    }

    @FXML
    private void handleAddSubject() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Добавление предмета");
        dialog.setHeaderText("Введите название предмета");
        dialog.showAndWait().ifPresent(name -> {
            if (_subjectDao.insert(name)) _subjectTable.setItems(FXCollections.observableArrayList(_subjectDao.findAll()));
        });
    }

    @FXML
    private void handleEditSubject() {
        Subject sel = _subjectTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        TextInputDialog d = new TextInputDialog(sel.getSubjectName());
        d.setHeaderText("Изменить название предмета");
        d.showAndWait().ifPresent(name -> {
            if (_subjectDao.update(sel.getSubjectId(), name)) {
                _subjectTable.setItems(FXCollections.observableArrayList(_subjectDao.findAll()));
            }
        });
    }

    @FXML
    private void handleDeleteSubject() {
        Subject selected = _subjectTable.getSelectionModel().getSelectedItem();
        if (selected != null && _subjectDao.delete(selected.getSubjectId())) {
            _subjectTable.setItems(FXCollections.observableArrayList(_subjectDao.findAll()));
        }
    }

    @FXML
    private void handleAddGroup() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Добавление группы");
        dialog.setHeaderText("Введите название группы");
        dialog.showAndWait().ifPresent(name -> {
            if (_groupDao.insert(name)) _groupTable.setItems(FXCollections.observableArrayList(_groupDao.findAll()));
        });
    }

    @FXML
    private void handleEditGroup() {
        Group sel = _groupTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        TextInputDialog d = new TextInputDialog(sel.getGroupName());
        d.setHeaderText("Изменить название группы");
        d.showAndWait().ifPresent(name -> {
            if (_groupDao.update(sel.getGroupId(), name)) {
                _groupTable.setItems(FXCollections.observableArrayList(_groupDao.findAll()));
            }
        });
    }

    @FXML
    private void handleDeleteGroup() {
        Group selected = _groupTable.getSelectionModel().getSelectedItem();
        if (selected != null && _groupDao.delete(selected.getGroupId())) {
            _groupTable.setItems(FXCollections.observableArrayList(_groupDao.findAll()));
        }
    }

    @FXML
    private void handleAddStandard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/standard_dialog.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Добавление норматива");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            StandardDialogController controller = loader.getController();
            controller.setOnSaveCallback(() -> _standardTable.setItems(FXCollections.observableArrayList(_standardDao.findAll())));
            stage.showAndWait();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleEditStandard() {
        StandardInfo sel = _standardTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/standard_dialog.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Редактирование норматива");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            StandardDialogController controller = loader.getController();
            controller.setStandardToEdit(sel);
            controller.setOnSaveCallback(() -> _standardTable.setItems(FXCollections.observableArrayList(_standardDao.findAll())));
            stage.showAndWait();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleDeleteStandard() {
        StandardInfo selected = _standardTable.getSelectionModel().getSelectedItem();
        if (selected != null && _standardDao.delete(selected.getStandardId())) {
            _standardTable.setItems(FXCollections.observableArrayList(_standardDao.findAll()));
        }
    }

    @FXML
    private void handleAddClassType() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Добавление вида занятия");
        dialog.setHeaderText("Введите название (например, Семинар)");
        dialog.showAndWait().ifPresent(name -> {
            if (_classTypeDao.insert(name)) _classTypeTable.setItems(FXCollections.observableArrayList(_classTypeDao.findAll()));
        });
    }

    @FXML
    private void handleEditClassType() {
        ClassType sel = _classTypeTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        TextInputDialog d = new TextInputDialog(sel.getClassTypeName());
        d.setHeaderText("Изменить вид занятия");
        d.showAndWait().ifPresent(name -> {
            if (_classTypeDao.update(sel.getClassTypeId(), name)) {
                _classTypeTable.setItems(FXCollections.observableArrayList(_classTypeDao.findAll()));
            }
        });
    }

    @FXML
    private void handleDeleteClassType() {
        ClassType selected = _classTypeTable.getSelectionModel().getSelectedItem();
        if (selected != null && _classTypeDao.delete(selected.getClassTypeId())) {
            _classTypeTable.setItems(FXCollections.observableArrayList(_classTypeDao.findAll()));
        }
    }

    @FXML
    private void handleAddYear() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Добавление учебного года");
        dialog.setHeaderText("Введите год (например, 2025/2026)");
        dialog.showAndWait().ifPresent(name -> {
            if (_yearDao.insert(name)) _yearTable.setItems(FXCollections.observableArrayList(_yearDao.findAll()));
        });
    }

    @FXML
    private void handleEditYear() {
        AcademycYear sel = _yearTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        TextInputDialog d = new TextInputDialog(sel.getAcademicYearName());
        d.setHeaderText("Изменить учебный год");
        d.showAndWait().ifPresent(name -> {
            if (_yearDao.update(sel.getAcademicYearId(), name)) {
                _yearTable.setItems(FXCollections.observableArrayList(_yearDao.findAll()));
            }
        });
    }

    @FXML
    private void handleDeleteYear() {
        AcademycYear selected = _yearTable.getSelectionModel().getSelectedItem();
        if (selected != null && _yearDao.delete(selected.getAcademicYearId())) {
            _yearTable.setItems(FXCollections.observableArrayList(_yearDao.findAll()));
        }
    }

    @FXML
    private void handleLinkUser() {
        User selected = _userTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Выберите пользователя!").showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/user_dialog.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Привязка пользователя");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            UserDialogController controller = loader.getController();
            controller.setUser(selected);
            controller.setOnSaveCallback(() -> _userTable.setItems(FXCollections.observableArrayList(_userDao.findAll())));
            stage.showAndWait();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleDeleteUser() {
        User sel = _userTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        if (sel.getUserId() == SessionManager.getCurrentUser().getUserId()) {
            new Alert(Alert.AlertType.ERROR, "Нельзя удалить самого себя!").showAndWait();
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Удалить пользователя " + sel.getLogin() + "?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(r -> {
            if (r == ButtonType.YES && _userDao.delete(sel.getUserId())) {
                _userTable.setItems(FXCollections.observableArrayList(_userDao.findAll()));
            }
        });
    }

    @FXML
    private void handleLogout() {
        SessionManager.clear();
        try {
            Stage stage = (Stage) _welcomeLabel.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/login.fxml"));
            stage.setScene(new Scene(loader.load(), 400, 350));
            stage.setTitle("Авторизация");
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void handleProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ru/kafedra/fxml/profile_dialog.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Профиль");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            User user = SessionManager.getCurrentUser();
            if (user != null) {
                String role = user.getRoleId() == 1 ? "Администратор" : "Преподаватель";
                _welcomeLabel.setText("Вы вошли как: " + user.getLogin() + " (" + role + ")");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}