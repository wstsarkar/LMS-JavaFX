package controller;

import java.net.URL;
import java.util.ResourceBundle;

import been.Users;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.UserRepository;

/**
 * 
 * @author Williyam
 * 
 */
public class LoginController implements Initializable {
	UserRepository repository ; 

	public void initialize(URL arg0, ResourceBundle arg1) {

		repository = new UserRepository();
	}
	
	@FXML
	private Label errorMessage;

	@FXML
	private TextField userNameField;

	@FXML
	private PasswordField passwordField;

	@FXML
	private Button cancelButton, loginButton;
	
	@FXML
	private void cencelButtonAction(){
		this.errorMessage.setText("");
		this.userNameField.setText("");
		this.passwordField.setText("");
	}
	@FXML
	private void loginButtonAction(){
		String userName = this.userNameField.getText();
		String pass = this.passwordField.getText();

		Users users = repository.getUser(userName, pass);
		if(users == null){
			this.errorMessage.setText("User name or password invalid!");
		}else{
			loadMenuWindow();
			closeWindow();
		}
	}

	private void closeWindow(){
	    Stage stage = (Stage) this.loginButton.getScene().getWindow();
	    stage.close();
	}
	
	private void loadMenuWindow(){
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
	}
	
	@FXML
	private void loadRegisterWindow(){
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/UserRegister.fxml"));
	        Parent root1 = (Parent) fxmlLoader.load();
	        Stage stage = new Stage();
	        stage.setScene(new Scene(root1));  
	        stage.setTitle("User Registration");
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	

}
