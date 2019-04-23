package controller;

import java.net.URL;
import java.util.ResourceBundle;

import been.Books;
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
public class BorrowController implements Initializable {

	BorrowRepository repository;

	StudentRepository studentRepository;
	BookRepository bookRepository;

	private Books book;
	private Student student;

	@FXML
	private Button borrowButton;
	
	@FXML
	private Label errorMessage;

	@FXML
	private TableView<Student> StudentTableView;

	@FXML
	private TableColumn<Student, String> studentIdColumn;

	@FXML
	private TableColumn<Student, String> nameColumn;

	@FXML
	private TableColumn<Student, String> emailColumn;

	@FXML
	private TableView<Books> BookTableView;

	@FXML
	private TableColumn<Books, Integer> idColumn;

	@FXML
	private TableColumn<Books, String> titleColumn;

	@FXML
	private TableColumn<Books, String> authorColumn;

	@FXML
	private TableColumn<Books, Integer> yearColumn;

	@FXML
	private TableColumn<Books, Integer> pagesColumn;

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

	public ObservableList<Books> getAvailableBooksList() {
		ObservableList<Books> booksList = FXCollections.observableArrayList();
		if (student != null) {
			booksList.addAll(bookRepository.getAvailableBookList(student.getId()));
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
		ObservableList<Books> list = getAvailableBooksList();

		idColumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("id"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("year"));
		pagesColumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("count"));

		BookTableView.setItems(list);
	}

	private void tableItemClickListener() {
		BookTableView.setOnMousePressed(new EventHandler<Event>() {

			@SuppressWarnings("unchecked")
			public void handle(Event event) {

				Node node = ((Node) event.getTarget()).getParent();
				TableRow<Books> row;
				if (node instanceof TableRow) {
					row = (TableRow<Books>) node;
				} else {
					row = (TableRow<Books>) node.getParent();
				}
				book = row.getItem();
			}
		});

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
	private void borrowButtonAction() {
		if(book != null && student != null){
			repository.borrowBook(student.getId(), book.getId());	
			book.setCount(book.getCount() - 1);
			bookRepository.update(book);
			showAvailableBooks();
			book = null;
		}else{
			this.errorMessage.setText("Borrow failed");
		}
	}
	
	@FXML
	private void returnButtonAction() {
		if(book != null && student != null){
			repository.returnBook(student.getId(), book.getId());	
			book.setCount(book.getCount() + 1);
			bookRepository.update(book);
			showAvailableBooks();
			book = null;
		}else{
			this.errorMessage.setText("Return failed");
		}
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
		Stage stage = (Stage) this.errorMessage.getScene().getWindow();
		stage.close();
	}
	

}
