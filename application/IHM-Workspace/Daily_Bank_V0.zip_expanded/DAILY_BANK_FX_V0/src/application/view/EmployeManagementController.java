package application.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.DailyBankState;
import application.control.ClientsManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.*;

public class EmployeManagementController implements Initializable {

	private DailyBankState dbs;
	
	private Stage primaryStage;
	
	private ObservableList<Employe> ole;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
