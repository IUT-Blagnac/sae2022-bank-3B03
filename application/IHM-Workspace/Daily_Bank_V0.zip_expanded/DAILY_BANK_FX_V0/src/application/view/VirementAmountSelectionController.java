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
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.data.transports.CompteCourantData;

public class VirementAmountSelectionController implements Initializable{
	


	// Fenêtre physique
	private Stage primaryStage;
	private OperationsManagementController op;


	
	// Manipulation de la fenêtre
		public void initContext(Stage _primaryStage, OperationsManagementController op) {
			this.primaryStage = _primaryStage;
			this.op = op;
			this.configure();
		}

		private void configure() {
			this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));
		}

		public void displayDialog() {
			this.primaryStage.showAndWait();
		}

		// Gestion du stage
		private Object closeWindow(WindowEvent e) {
			e.consume();
			return null;
		}

	@FXML
	private TextField somme;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	public double getSomme() {
		return Double.parseDouble(this.somme.getText());
	}
	
	@FXML
	public void valider() {
		this.op.setMontantAVirer(this.getSomme());
		this.primaryStage.close();
	}

}