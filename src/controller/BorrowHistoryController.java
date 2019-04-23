package controller;

import java.net.URL;
import java.util.ResourceBundle;

import been.Student;
import dto.BorrowDTO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import repository.BookRepository;
import repository.BorrowRepository;
import repository.StudentRepository;

/**
 * 
 * @author Williyam
 * 
 */
public class BorrowHistoryController implements Initializable {

	BorrowRepository repository;

	StudentRepository studentRepository;
	BookRepository bookRepository;

	private Student student;
	
	@FXML
	private Label studentList;

	@FXML
	private TableView<Student> StudentTableView;

	@FXML
	private TableColumn<Student, String> studentIdColumn;

	@FXML
	private TableColumn<Student, String> nameColumn;

	@FXML
	private TableColumn<Student, String> emailColumn;

	@FXML
	private TableView<BorrowDTO> BookTableView;

	@FXML
	private TableColumn<BorrowDTO, String> titleColumn;

	@FXML
	private TableColumn<BorrowDTO, String> authorColumn;

	@FXML
	private TableColumn<BorrowDTO, Integer> yearColumn;

	@FXML
	private TableColumn<BorrowDTO, String> borrowOnColumn;
	@FXML
	private TableColumn<BorrowDTO, String> returnedOnColumn;

	public void initialize(URL location, ResourceBundle resources) {
		repository = new BorrowRepository();
		studentRepository = new StudentRepository();
		bookRepository = new BookRepository();
		
		showStudents();
		tableItemClickListener();
	}

	public ObservableList<Student> getStudentList() {
		ObservableList<Student> list = FXCollections.observableArrayList();

		list.addAll(studentRepository.getStudentList());
		return list;
	}

	public ObservableList<BorrowDTO> getAvailableBooksList() {
		ObservableList<BorrowDTO> booksList = FXCollections.observableArrayList();
		if (student != null) {
			booksList.addAll(repository.getBorrowHistory(student.getId()));
		}
		return booksList;
	}

	public void showStudents() {
		ObservableList<Student> list = getStudentList();

		studentIdColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("studentId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));

		StudentTableView.setItems(list);
	}

	public void showAvailableBooks() {
		ObservableList<BorrowDTO> list = getAvailableBooksList();

		titleColumn.setCellValueFactory(new PropertyValueFactory<BorrowDTO, String>("title"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<BorrowDTO, String>("author"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<BorrowDTO, Integer>("year"));
		borrowOnColumn.setCellValueFactory(new PropertyValueFactory<BorrowDTO, String>("borrowOn"));
		returnedOnColumn.setCellValueFactory(new PropertyValueFactory<BorrowDTO, String>("returnedOn"));

		BookTableView.setItems(list);
	}

	private void tableItemClickListener() {

		StudentTableView.setOnMousePressed(new EventHandler<Event>() {

			@SuppressWarnings("unchecked")
			public void handle(Event event) {

				Node node = ((Node) event.getTarget()).getParent();
				TableRow<Student> row;
				if (node instanceof TableRow) {
					row = (TableRow<Student>) node;
				} else {
					row = (TableRow<Student>) node.getParent();
				}
				student = row.getItem();
				showAvailableBooks();
			}
		});

	}

	@FXML
	private void logoutButton() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setTitle("Login");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

		closeWindow();
	}

	@FXML
	private void backButton() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Menu.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setTitle("Menu");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

		closeWindow();
	}

	private void closeWindow() {
		Stage stage = (Stage) this.studentList.getScene().getWindow();
		stage.close();
	}

}
