package fsd.assignment.assignment1.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StudentData {
    private static final String filename = "student-data.txt";
    private static StudentData instance = new StudentData();
    private ObservableList<Student> students;

    public static StudentData getInstance() {
        if (instance == null) {
            instance = new StudentData();
        }
        return instance;
    }

    public ObservableList<Student> getStudents() {
        return students;
    }

    public void loadStudentData() throws IOException {
        students = FXCollections.observableArrayList();
        Path path = Paths.get(filename);

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String input;
            while ((input = br.readLine()) != null) {
                String[] studentItem = input.split("\t");
                String studId = studentItem[0];
                String yearStudy = studentItem[1];
                String mod1 = studentItem[2];
                String mod2 = studentItem[3];
                String mod3 = studentItem[4];
                Student studDataItem = new Student(studId, yearStudy, mod1, mod2, mod3);
                students.add(studDataItem);
            }
        }
    }

    public void storeStudentData() throws IOException {
        Path path = Paths.get(filename);
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            for (Student item : students) {
                String studId = item.getStudId();
                String year = item.getYearOfStudy();
                String mod1 = item.getModule1();
                String mod2 = item.getModule2();
                String mod3 = item.getModule3();
                String student = studId + "\t" + year + "\t" + mod1 + "\t" + mod2 + "\t" + mod3;
                bw.write(student);
                bw.newLine();
            }
        }
    }

    public void addStudentData(Student studentToAdd) {
        students.add(studentToAdd);
    }

    public void deleteStudent(Student stu) {
        students.remove(stu);
    }
}
