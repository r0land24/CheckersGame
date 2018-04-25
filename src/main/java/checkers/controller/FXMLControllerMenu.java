package checkers.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FXMLControllerMenu implements Initializable {
	
	@FXML
	private Button button, button1, button2;
    
    @FXML
    private void handleButtonActionNewGame(ActionEvent event) {
    	System.out.println("You clicked me!");
        
    	Stage stage =(Stage) button.getScene().getWindow();
 
    	CheckersMainApp cma = new CheckersMainApp();
		Scene scene = new Scene(cma.createContent());
		
		stage.setTitle("Dámajáték");
		stage.setScene(scene);
		stage.show();
    }
    
    @FXML
    private void handleButtonActionLoadGame(ActionEvent event) {
    	System.out.println("You clicked me!");
    }
    
    @FXML
    private void handleButtonActionRank(ActionEvent event) {
    	try {
			System.out.println("You clicked me!");
			
			Stage stage =(Stage) button.getScene().getWindow();
			
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/SceneRank.fxml"));
			Scene scene = new Scene(root);
			
			stage.setTitle("Dámajáték");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
