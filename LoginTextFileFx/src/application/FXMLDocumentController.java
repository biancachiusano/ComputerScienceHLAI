package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable {

	
	@FXML private Button signUpButton;
	@FXML private Button loginButton;
	@FXML private TextField nameTextField;
	@FXML private TextField passwordTextField;
	@FXML private Label signUpStatus;

	


	
	
	public void signUpButtonPushed(ActionEvent event) throws IOException {
		
		Person newUser = new Person();
		newUser.name = nameTextField.getText();
		newUser.password = passwordTextField.getText();
		
		
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/application/Profile.fxml"));
		Parent profileWindow = loader.load();
		
		
		Scene newProfileScene = new Scene(profileWindow);
		  
		//Access the controller and call a method
		ProfileController controller = loader.getController();
		controller.initData(newUser);
		//get stage info
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(newProfileScene);
		window.show();
		
	}
	
		public void loginButtonPushed(ActionEvent event) {
		
			
			String file = nameTextField.getText().toString();
			String fileName = file + ".bin";
	
			try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
			Person p = (Person) is.readObject();
			is.close();
			if(passwordTextField.getText().equals(p.password)) {
				signUpStatus.setText("Welcome back " + p.name );
				
				FXMLLoader loginLoader = new FXMLLoader();
				loginLoader.setLocation(getClass().getResource("/application/PlaylistGenerator.fxml"));
				Parent playlistWindow = loginLoader.load();
				
				//Reading Correct Csv for User that has logged In
				String csvFile = nameTextField.getText().toString();
				String csvFileName = csvFile + ".csv";
				
				CsvReaderWriter.readCsv(csvFileName);
				
				Scene playlistScene = new Scene(playlistWindow);
				  
				//Access the controller and call a method
				PlaylistGeneratorController controller = loginLoader.getController();
				controller.initData(p);
				//get stage info
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
				window.setScene(playlistScene);
				window.show();
				
			} else {
				signUpStatus.setText("Incorrect Password");
			}
			//is.close();
			
		} catch (FileNotFoundException e) {
			signUpStatus.setText("Username not found, Sign Up");
			this.signUpButton.setDisable(false);
			//e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Disable the Sign Up Button 
		this.signUpButton.setDisable(true);
	}

}
