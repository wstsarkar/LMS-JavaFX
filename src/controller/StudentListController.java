package controller;

import java.net.URL;
import java.util.ResourceBundle;

import been.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import repository.StudentRepository;

/**
 * 
 * @author Williyam
 * 
 */
public class StudentListController implements Initializable {

	StudentRepository repository;

	private Student student;

	@FXML
	private Label errorMessage;

	@FXML
	private TextField studentIdField;

	@FXML
	private TextField nameField;

	@FXML
	private TextField emailField;

	@FXML
	private Button insertButton;

	@FXML
	private Button updateButton;

	@FXML
	private Button deleteButton;

	@FXML
	private TableView<Student> TableView;

	@FXML
	private TableColumn<Student, String> studentIdColumn;

	@FXML
	private TableColumn<Student, String> nameColumn;

	@FXML
	private TableColumn<Student, String> emailColumn;


	public void initialize(URL location, ResourceBundle resources) {
		repository = new StudentRepository();
		showStudents();
		tableItemClickListener();
	}

	public ObservableList<Student> getStudentList() {
		ObservableList<Student> list = FXCollections.observableArrayList();

		list.addAll(repository.getStudentList());
		return list;
	}

	// I had to change ArrayList to ObservableList I didn't find another option
	// to do this but this works :)
	public void showStudents() {
		ObservableList<Student> list = getStudentList();

		studentIdColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("studentId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));

		TableView.setItems(list);
	}

	private void tableItemClickListener() {
		TableView.setOnMousePressed(new EventHandler<Event>() {

			@SuppressWarnings("unchecked")
			public void handle(Event event) {

				clearButton();
				Node node = ((Node) event.getTarget()).getParent();
				TableRow<Student> row;
				if (node instanceof TableRow) {
					row = (TableRow<Student>) node;
				} else {
					// clicking on text part
					row = (TableRow<Student>) node.getParent();
				}
				student = row.getItem();
				setStudentObjettToUI(student);
			}
		});

	}

	private void setStudentObjettToUI(Student student) {
		if (student != null) {
			studentIdField.setText(student.getStudentId());
			nameField.setText(student.getName());
			emailField.setText(student.getEmail() + "");
		}
		showStudents();
	}

	@FXML
	private void insertButton() {
		if (checkValidation()) {
			Student student = getStudent();
			if (student != null) {
				if (repository.getStudentBySID(student.getStudentId()) == null ) {
					repository.insert(student);
					clearButton();
				} else {
					errorMessage.setText("Id already present");
				}

			} else {
				errorMessage.setText("Nothing to insert");
			}
		}

	}

	@FXML
	private void updateButton() {
		if (checkValidation()) {
			Student student = getStudent();

			if (student != null) {
				if (repository.getStudentBySID(student.getStudentId()) != null) {
					repository.update(student);
					clearButton();

				} else {
					errorMessage.setText("Student not present");
				}

			} else {
				errorMessage.setText("Nothing to insert");
			}
		}
	}
	
	@FXML
	private void deleteButton() {
		if (student != null) {
			repository.delete(student.getId());
			clearButton();
			student = null;
		}
	}

	@FXML
	private void clearButton() {
		studentIdField.setText("");
		nameField.setText("");
		emailField.setText("");
		errorMessage.setText("");

		showStudents();
	}

	public boolean checkValidation() {
		boolean flag = false;

		if (studentIdField.getText().equals(""))
			errorMessage.setText("Student must be filled and unique");
		else if (nameField.getText().equals(""))
			errorMessage.setText("Name must be filled");
		else if (emailField.getText().equals(""))
			errorMessage.setText("Email must be field");
		else {
			errorMessage.setText("");
			flag = true;
		}
		return flag;
	}
	
	private Student getStudent(){
		String studentId = studentIdField.getText();
		String name = nameField.getText();
		String email = emailField.getText();


		Student nStudent = new Student(0,studentId, name, email);
		return nStudent;
	}


	@FXML
	private void logoutButton(){
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1));  
	        stage.setTitle("Login");
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		
		closeWindow();
	}
	
	@FXML
	private void backButton(){
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1));  
	        stage.setTitle("Menu");
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
		
		closeWindow();
	}

	private void closeWindow(){
	    Stage stage = (Stage) this.errorMessage.getScene().getWindow();
	    stage.close();
	}
	
}
