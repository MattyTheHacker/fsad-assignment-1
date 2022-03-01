package fsd.assignment.assignment1;

import fsd.assignment.assignment1.datamodel.Student;
import fsd.assignment.assignment1.datamodel.StudentData;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public class Controller {
    private final String[] modChoices = {"OOP", "Data Algo", "DS", "Maths", "AI", "Adv Programming", "Project"};
    public Student studentToAdd;
    @FXML
    private TextField studId;
    @FXML
    private TextField yearStudy;
    @FXML
    private ChoiceBox<String> mod1Choice;
    @FXML
    private ChoiceBox<String> mod2Choice;
    @FXML
    private ChoiceBox<String> mod3Choice;
    private String choice1, choice2, choice3;
    @FXML
    private Label validateStudent;
    @FXML
    private ListView<Student> studentListView;
    @FXML
    private Label yearStudyView;
    @FXML
    private Label mod1View;
    @FXML
    private Label mod2View;
    @FXML
    private Label mod3View;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private BorderPane mainWindow;

    public void initialize() {
        studentListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Student student = studentListView.getSelectionModel().getSelectedItem();
                yearStudyView.setText(student.getYearOfStudy());
                mod1View.setText(student.getModule1());
                mod2View.setText(student.getModule2());
                mod3View.setText(student.getModule3());
            }
        });

        mod1Choice.setOnAction(this::getChoice);
        mod2Choice.setOnAction(this::getChoice);
        mod3Choice.setOnAction(this::getChoice);

        mod1Choice.getItems().addAll(modChoices);
        mod2Choice.getItems().addAll(modChoices);
        mod3Choice.getItems().addAll(modChoices);

        listContextMenu = new ContextMenu();
        MenuItem deleteStudent = new MenuItem("Delete?");

        deleteStudent.setOnAction(event -> {
            Student student = studentListView.getSelectionModel().getSelectedItem();
            deleteStudent(student);
        });

        MenuItem editStudent = new MenuItem("Edit??");
        editStudent.setOnAction(event -> {
            Student student = studentListView.getSelectionModel().getSelectedItem();
            editStudent(student);
        });

        listContextMenu.getItems().add(deleteStudent);
        listContextMenu.getItems().add(editStudent);

        studentListView.setCellFactory(new Callback<>() {
            public ListCell<Student> call(ListView<Student> param) {
                ListCell<Student> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(Student stu, boolean empty) {
                        super.updateItem(stu, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(stu.getStudId());
                        }
                    }
                };
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                    if (isNowEmpty) {
                        cell.setContextMenu(null);
                    } else {
                        cell.setContextMenu(listContextMenu);
                    }
                });
                return cell;
            }
        });

        SortedList<Student> sortedByYear = new SortedList<>(StudentData.getInstance().getStudents(), Comparator.comparing(Student::getYearOfStudy));

        studentListView.setItems(sortedByYear);
        studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        studentListView.getSelectionModel().selectFirst();
    }

    public void getChoice(ActionEvent event) {
        ChoiceBox<String> cb = (ChoiceBox<String>) event.getSource();
        switch (cb.getId()) {
            case "mod1Choice" -> {
                choice1 = mod1Choice.getSelectionModel().getSelectedItem();
                if (mod2Choice.getSelectionModel().getSelectedItem() != null) {
                    choice2 = mod2Choice.getSelectionModel().getSelectedItem();
                } else {
                    choice2 = mod2View.getText();
                }
                if (mod3Choice.getSelectionModel().getSelectedItem() != null) {
                    choice3 = mod3Choice.getSelectionModel().getSelectedItem();
                } else {
                    choice3 = mod3View.getText();
                }
            }
            case "mod2Choice" -> {
                choice2 = mod2Choice.getSelectionModel().getSelectedItem();
                if (mod1Choice.getSelectionModel().getSelectedItem() != null) {
                    choice1 = mod1Choice.getSelectionModel().getSelectedItem();
                } else {
                    choice1 = mod1View.getText();
                }
                if (mod3Choice.getSelectionModel().getSelectedItem() != null) {
                    choice3 = mod3Choice.getSelectionModel().getSelectedItem();
                } else {
                    choice3 = mod3View.getText();
                }
            }
            case "mod3Choice" -> {
                choice3 = mod3Choice.getSelectionModel().getSelectedItem();
                if (mod1Choice.getSelectionModel().getSelectedItem() != null) {
                    choice1 = mod1Choice.getSelectionModel().getSelectedItem();
                } else {
                    choice1 = mod1View.getText();
                }
                if (mod2Choice.getSelectionModel().getSelectedItem() != null) {
                    choice2 = mod2Choice.getSelectionModel().getSelectedItem();
                } else {
                    choice2 = mod2View.getText();
                }
            }
        }
    }


    @FXML
    public void addStudentData() {
        String studIdS = studId.getText();
        String yearStudyS = yearStudy.getText();

        if (studIdS == null || yearStudyS == null) {
            validateStudent.setVisible(true);
        } else {
            studentToAdd = new Student(studIdS, yearStudyS, choice1, choice2, choice3);
            StudentData.getInstance().addStudentData(studentToAdd);
            studentListView.getSelectionModel().select(studentToAdd);
            studId.clear();
            yearStudy.clear();
            mod1Choice.setValue(null);
            mod2Choice.setValue(null);
            mod3Choice.setValue(null);
        }
    }

    public void deleteStudent(Student stu) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete a student from the list");
        alert.setHeaderText("Deleting student " + stu.getStudId());
        alert.setContentText("Are you sure you want to delete the student?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            StudentData.getInstance().deleteStudent(stu);
        }
    }

    public void editStudent(Student stu) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Edit a student's details");
        dialog.setHeaderText("Editing student Id: " + stu.getStudId());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("edit-students.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("[WARN] Could not load the dialog");
            e.printStackTrace();
            return;
        }

        EditStudentController ec = fxmlLoader.getController();
        ec.setToEdit(stu);

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Student updateStudent = ec.processEdit(stu);
            studentListView.getSelectionModel().select(updateStudent);
        }
    }
}