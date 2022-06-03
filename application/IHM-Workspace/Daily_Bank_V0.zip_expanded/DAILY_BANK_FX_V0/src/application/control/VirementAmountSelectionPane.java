package application.control;

import application.DailyBankApp;
import application.DailyBankState;
import application.tools.EditionMode;
import application.tools.StageManagement;
import application.view.CompteEditorPaneController;
import application.view.OperationsManagementController;
import application.view.VirementAmountSelectionController;
import application.view.VirementManagementController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.data.Client;
import model.data.CompteCourant;

public class VirementAmountSelectionPane {

	private Stage primaryStage;
	private VirementAmountSelectionController vasc;

	public VirementAmountSelectionPane(Stage _parentStage, OperationsManagementController op) {

		try {
			FXMLLoader loader = new FXMLLoader(CompteEditorPaneController.class.getResource("virementamountseletion.fxml"));
			BorderPane root = loader.load();

			Scene scene = new Scene(root, root.getPrefWidth()+20, root.getPrefHeight()+10);
			scene.getStylesheets().add(DailyBankApp.class.getResource("application.css").toExternalForm());

			this.primaryStage = new Stage();
			this.primaryStage.initModality(Modality.WINDOW_MODAL);
			this.primaryStage.initOwner(_parentStage);
			StageManagement.manageCenteringStage(_parentStage, this.primaryStage);
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("Virement compte à compte");
			this.primaryStage.setResizable(false);

			this.vasc = loader.getController();
			this.vasc.initContext(this.primaryStage, op);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doVirementEditorDialog() {
		this.vasc.displayDialog();
	}
}