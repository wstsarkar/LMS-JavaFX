package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import repository.UserRepository;

/**
 * 
 * @author Williyam
 * 
 */
public class MenuController implements Initializable {
	UserRepository repository;

	@FXML
	private Button booklist;

	public void initialize(URL arg0, ResourceBundle arg1) {

		repository = new UserRepository();
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
	private void studentListButton() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/StudentList.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setTitle("Book List");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

		closeWindow();
	}

	@FXML
	private void bookListButton() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/BookList.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setTitle("Book List");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

		closeWindow();
	}

	@FXML
	private void borrowButton() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/BorrowForm.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setTitle("Borrow Book");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeWindow();
	}

	@FXML
	private void returnButton() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ReturnForm.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setTitle("Borrow Book");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeWindow();
	}

	@FXML
	private void borrowHistoryButton() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/BorrowHistory.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setTitle("Borrow History");
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeWindow();
	}

	private void closeWindow() {
		Stage stage = (Stage) this.booklist.getScene().getWindow();
		stage.close();
	}
}
