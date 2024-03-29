package fsd.assignment.assignment1;

import fsd.assignment.assignment1.datamodel.Student;
import fsd.assignment.assignment1.datamodel.StudentData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;


public class EditStudentController {
    private final String[] modChoices = {"OOP", "Data Algo", "DS", "Maths", "AI", "Adv Programming", "Project"};
    @FXML
    private Label yearStudyDisplay;
    @FXML
    private Label mod1Edit;
    @FXML
    private Label mod2Edit;
    @FXML
    private Label mod3Edit;
    @FXML
    private ChoiceBox<String> mod1ChoiceEdit;
    @FXML
    private ChoiceBox<String> mod2ChoiceEdit;
    @FXML
    private ChoiceBox<String> mod3ChoiceEdit;
    private String mod1S, mod2S, mod3S;

    public void initialize() {
        mod1ChoiceEdit.getItems().addAll(modChoices);
        mod2ChoiceEdit.getItems().addAll(modChoices);
        mod3ChoiceEdit.getItems().addAll(modChoices);

        mod1ChoiceEdit.setOnAction(this::getChoiceEdit);
        mod2ChoiceEdit.setOnAction(this::getChoiceEdit);
        mod3ChoiceEdit.setOnAction(this::getChoiceEdit);
    }

    public void setToEdit(Student stu) {
        yearStudyDisplay.setText(stu.getYearOfStudy());
        mod1Edit.setText(stu.getModule1());
        mod2Edit.setText(stu.getModule2());
        mod3Edit.setText(stu.getModule3());

        if (mod1ChoiceEdit.getSelectionModel().getSelectedItem() != null) {
            mod1S = mod1ChoiceEdit.getSelectionModel().getSelectedItem();
        } else {
            mod1S = mod1Edit.getText();
        }

        if (mod2ChoiceEdit.getSelectionModel().getSelectedItem() != null) {
            mod2S = mod2ChoiceEdit.getSelectionModel().getSelectedItem();
        } else {
            mod2S = mod2Edit.getText();
        }

        if (mod3ChoiceEdit.getSelectionModel().getSelectedItem() != null) {
            mod3S = mod3ChoiceEdit.getSelectionModel().getSelectedItem();
        } else {
            mod3S = mod3Edit.getText();
        }
    }

    public Student processEdit(Student stu) {
        ObservableList<Student> allStudents = StudentData.getInstance().getStudents();
        String studIdS = stu.getStudId();
        String yearStudyS = stu.getYearOfStudy();
        allStudents.remove(stu);
        Student changedStu = new Student(studIdS, yearStudyS, mod1S, mod2S, mod3S);
        StudentData.getInstance().addStudentData(changedStu);
        return changedStu;
    }

    public void getChoiceEdit(ActionEvent event) {
        Object source = event.getSource();
        ChoiceBox<?> cb;
        if (source instanceof ChoiceBox<?>) {
            cb = (ChoiceBox<?>) event.getSource();
            switch (cb.getId()) {
                case "mod1ChoiceEdit" -> {
                    mod1S = mod1ChoiceEdit.getSelectionModel().getSelectedItem();
                    if (mod2ChoiceEdit.getSelectionModel().getSelectedItem() != null) {
                        mod2S = mod2ChoiceEdit.getSelectionModel().getSelectedItem();
                    } else {
                        mod2S = mod2Edit.getText();
                    }
                    if (mod3ChoiceEdit.getSelectionModel().getSelectedItem() != null) {
                        mod3S = mod3ChoiceEdit.getSelectionModel().getSelectedItem();
                    } else {
                        mod3S = mod3Edit.getText();
                    }
                }
                case "mod2ChoiceEdit" -> {
                    mod2S = mod2ChoiceEdit.getSelectionModel().getSelectedItem();
                    if (mod1ChoiceEdit.getSelectionModel().getSelectedItem() != null) {
                        mod1S = mod1ChoiceEdit.getSelectionModel().getSelectedItem();
                    } else {
                        mod1S = mod1Edit.getText();
                    }
                    if (mod3ChoiceEdit.getSelectionModel().getSelectedItem() != null) {
                        mod3S = mod3ChoiceEdit.getSelectionModel().getSelectedItem();
                    } else {
                        mod3S = mod3Edit.getText();
                    }
                }
                case "mod3ChoiceEdit" -> {
                    mod3S = mod3ChoiceEdit.getSelectionModel().getSelectedItem();
                    if (mod1ChoiceEdit.getSelectionModel().getSelectedItem() != null) {
                        mod1S = mod1ChoiceEdit.getSelectionModel().getSelectedItem();
                    } else {
                        mod1S = mod1Edit.getText();
                    }
                    if (mod2ChoiceEdit.getSelectionModel().getSelectedItem() != null) {
                        mod2S = mod2ChoiceEdit.getSelectionModel().getSelectedItem();
                    } else {
                        mod2S = mod2Edit.getText();
                    }
                }
            }
        } else {
            System.out.println("[ERROR] The object passed to the event was not of the expected type.");
            System.out.println("[INFO] Expected type: ChoiceBox<?>, Received: " + source.getClass());
        }
    }
}

