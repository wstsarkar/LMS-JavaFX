package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ResourceBundle;

import been.Books;

/**
 * 
 * @author Williyam
 * 
 */
public class MainController implements Initializable {

	private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=Assignment";
	private static final String USER = "sa";
	private static final String PASS = "sonic600";

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
	private Button clearButton;

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

	@FXML
	private void insertButton() {
		if (checkValidation()) {

			if (!getIsIDPresent()) {
				String query = "insert into Books values(" + idField.getText() + ",'" + titleField.getText() + "','"
						+ authorField.getText() + "'," + yearField.getText() + "," + pagesField.getText() + ")";
				executeQuery(query);

			} else {
				errorMessage.setText("ID already present");
			}
		}
		showBooks();

	}

	@FXML
	private void updateButton() {
		if (checkValidation()) {

			if (getIsIDPresent()) {

				String query = "UPDATE Books SET Title='" + titleField.getText() + "',Author='" + authorField.getText()
						+ "',Year=" + yearField.getText() + ",Pages=" + pagesField.getText() + " WHERE ID="
						+ idField.getText();
				executeQuery(query);
			} else {
				errorMessage.setText("ID is not present");
			}
		}
		showBooks();
	}

	@FXML
	private void deleteButton() {
		if (getIsIDPresent()) {
			String query = "DELETE FROM books WHERE ID=" + idField.getText() + "";
			executeQuery(query);
		} else {
			errorMessage.setText("ID is not present");
		}
		showBooks();
	}

	@FXML
	private void clearButton() {

		idField.setText("");
		titleField.setText("");
		authorField.setText("");
		yearField.setText("");
		pagesField.setText("");
		errorMessage.setText("");

		showBooks();
	}

	private void setBooksObjettToUI(Books books) {

		idField.setText(books.getId() + "");
		titleField.setText(books.getTitle());
		authorField.setText(books.getAuthor());
		yearField.setText(books.getYear() + "");
		pagesField.setText(books.getCount() + "");

		showBooks();
	}

	public void executeQuery(String query) {
		Connection conn = getConnection();
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate(query);
			clearButton();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		showBooks();
		tableItemClickListener();
	}

	public Connection getConnection() {
		Connection conn;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ObservableList<Books> getBooksList() {
		ObservableList<Books> booksList = FXCollections.observableArrayList();
		Connection connection = getConnection();
		String query = "SELECT * FROM Books ";
		Statement st;
		ResultSet rs;

		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			Books books;
			while (rs.next()) {
				books = new Books(rs.getInt("Id"), rs.getString("Title"), rs.getString("Author"), rs.getInt("Year"),
						rs.getInt("Pages"));
				booksList.add(books);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return booksList;
	}

	public boolean getIsIDPresent() {
		boolean flag = false;
		Connection connection = getConnection();
		String query = "SELECT * FROM Books WHERE ID =" + getIdTextBox();
		Statement st;
		ResultSet rs;
		try {
			st = connection.createStatement();
			rs = st.executeQuery(query);
			if (rs.next()) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	// I had to change ArrayList to ObservableList I didn't find another option
	// to do this but this works :)
	public void showBooks() {
		ObservableList<Books> list = getBooksList();

		idColumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("id"));
		titleColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
		authorColumn.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
		yearColumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("year"));
		pagesColumn.setCellValueFactory(new PropertyValueFactory<Books, Integer>("pages"));

		TableView.setItems(list);
	}

	public boolean checkValidation() {
		boolean flag = false;
		if (getIdTextBox() == -1)
			errorMessage.setText("Id must be number");
		else if (titleField.getText().equals(""))
			errorMessage.setText("Title must be filled");
		else if (authorField.getText().equals(""))
			errorMessage.setText("Author must be filled");
		else if (getYearTextBox() == -1)
			errorMessage.setText("Year must be number");
		else if (getPagesTextBox() == -1)
			errorMessage.setText("Pages must be number");
		else
			flag = true;

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

	private void tableItemClickListener() {
		TableView.setOnMousePressed(new EventHandler<Event>() {

			@SuppressWarnings("unchecked")
			public void handle(Event event) {

				Node node = ((Node) event.getTarget()).getParent();
				TableRow<Books> row;
				if (node instanceof TableRow) {
					row = (TableRow<Books>) node;
				} else {
					// clicking on text part
					row = (TableRow<Books>) node.getParent();
				}
				setBooksObjettToUI(row.getItem());
			}
		});

	}
}
