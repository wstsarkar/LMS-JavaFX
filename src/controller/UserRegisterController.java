package controller;

import java.net.URL;
import java.util.ResourceBundle;

import been.Users;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import repository.UserRepository;

/**
 * 
 * @author Williyam
 * 
 */
public class UserRegisterController implements Initializable {
	UserRepository repository ; 

	public void initialize(URL arg0, ResourceBundle arg1) {
		repository = new UserRepository();
	}
	
	@FXML
	private Label errorMessage;

	@FXML
	private TextField nameField;

	@FXML
	private TextField userNameField;

	@FXML
	private TextField emailField;

	@FXML
	private PasswordField passwordField;
	
	@FXML
	private PasswordField confirmPasswordField;

	@FXML
	private Button cancelButton, loginButton;
	
	@FXML
	private void cancelButtonAction(){
		setError("");
		this.nameField.setText("");
		this.userNameField.setText("");
		this.emailField.setText("");
		this.passwordField.setText("");
		this.confirmPasswordField.setText("");
	}
	
	
	
	@FXML
	private void saveButtonAction(){
		String name = this.nameField.getText();
		String userName = this.userNameField.getText();
		String pass = this.passwordField.getText();
		String passCoinfirm = this.confirmPasswordField.getText();

		if(name.equals("")){
			setError("Name required!");			
			return;
		}
		if(userName.equals("")){
			setError("User name required!");			
			return;
		}
		if(pass.equals("")){
			setError("Password name required!");			
			return;
		}
		
		if(!pass.equals(passCoinfirm)){
			setError("password does not match!");			
			return;
		}

		Users users = repository.getUser(userName);
		if(users != null){
			setError("User name already exists!");
		}else{
			users = new Users(0, userName, this.emailField.getText(), pass, name);
			if(repository.insert(users) > 0){
				setSuccess("Added Successfully");
			}else{
				setError("Failed");
			}
			
		}
	}
	
	private void setError(String message){
		this.errorMessage.setText(message);
		this.errorMessage.setTextFill(Paint.valueOf("RED"));
	}
	private void setSuccess(String message){
		this.errorMessage.setText(message);
		this.errorMessage.setTextFill(Paint.valueOf("Green"));
	}
	

}
