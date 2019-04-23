package controller;

import java.net.URL;
import java.util.ResourceBundle;

import been.Books;
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
import repository.BookRepository;

/**
 * 
 * @author Williyam
 * 
 */
public class BookListController implements Initializable {

	BookRepository repository;

	private Books book;

	@FXML
	private Label errorMessage;

	@FXML
	private TextField idField;

	@FXML
	private TextField titleField;

	@FXML
	private TextField authorField;

	@FXML
	private TextField yearField;

	@FXML
	private TextField pagesField;

	@FXML
	private Button insertButton;

	@FXML
	private Button updateButton;

	@FXML
	private Button deleteButton;

	@FXML
	private TableView<Books> TableView;

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
		repository = new BookRepository();
		showBooks();
		tableItemClickListener();
	}

	public ObservableList<Books> getBooksList() {
		ObservableList<Books> booksList = FXCollections.observableArrayList();

		booksList.addAll(repository.getBookList());
		return booksList;
	}

	// I had to change ArrayList to ObservableList I didn't find another option
	// to do this but this works :)
	public void showBooks() {
		ObservableList<Books> list = getBooksList();

		idColumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("id"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("year"));
		pagesColumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("count"));

		TableView.setItems(list);
	}

	private void tableItemClickListener() {
		TableView.setOnMousePressed(new EventHandler<Event>() {

			@SuppressWarnings("unchecked")
			public void handle(Event event) {

				clearButton();
				Node node = ((Node) event.getTarget()).getParent();
				TableRow<Books> row;
				if (node instanceof TableRow) {
					row = (TableRow<Books>) node;
				} else {
					// clicking on text part
					row = (TableRow<Books>) node.getParent();
				}
				book = row.getItem();
				setBooksObjettToUI(book);
			}
		});

	}

	private void setBooksObjettToUI(Books books) {
		if (books != null) {

			idField.setText(books.getId() + "");
			titleField.setText(books.getTitle());
			authorField.setText(books.getAuthor());
			yearField.setText(books.getYear() + "");
			pagesField.setText(books.getCount() + "");
		}
		showBooks();
	}

	@FXML
	private void insertButton() {
		if (checkValidation()) {

			if (book != null) {
				if (repository.getBook(getIdTextBox()) == null) {
					repository.insert(book);
					clearButton();

				} else {
					errorMessage.setText("Id already present");
				}

			} else {
				errorMessage.setText("Nothing to insert");
			}
		}
		showBooks();
	}

	@FXML
	private void updateButton() {
		if (checkValidation()) {

			if (book != null) {
				if (repository.getBook(getIdTextBox()) != null) {
					repository.update(book);
					clearButton();
				} else {
					errorMessage.setText("book not present");
				}

			} else {
				errorMessage.setText("Nothing to insert");
			}
		}
		showBooks();
	}

	@FXML
	private void deleteButton() {
		if (book != null) {
			repository.delete(book.getId());
			clearButton();
			showBooks();
			book = null;
		}else {
			errorMessage.setText("Nothing to delete");
		}
	}

	@FXML
	private void clearButton() {

		idField.setText("");
		titleField.setText("");
		authorField.setText("");
		yearField.setText("");
		pagesField.setText("");
		errorMessage.setText("");
		
		this.book = null;
		showBooks();
	}

	public boolean checkValidation() {
		boolean flag = false;
		int Id = getIdTextBox();
		int Year = getYearTextBox();
		int Pages = getPagesTextBox();
		String Title = titleField.getText();
		String Author = authorField.getText();

		if (titleField.getText().equals(""))
			errorMessage.setText("Title must be filled");
		else if (authorField.getText().equals(""))
			errorMessage.setText("Author must be filled");
		else if (getYearTextBox() == -1)
			errorMessage.setText("Year must be number");
		else if (getPagesTextBox() == -1)
			errorMessage.setText("Count must be number");
		else {
			errorMessage.setText("");
			this.book = new Books(Id, Title, Author, Year, Pages);
			flag = true;
		}
		return flag;
	}

	private int getIdTextBox() {
		int id = -1;
		try {
			id = Integer.parseInt(idField.getText());
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		}
		return id;
	}

	private int getYearTextBox() {
		int year = -1;
		try {
			year = Integer.parseInt(yearField.getText());
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		}
		return year;
	}

	private int getPagesTextBox() {
		int pages = -1;
		try {
			pages = Integer.parseInt(pagesField.getText());
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		}
		return pages;
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
