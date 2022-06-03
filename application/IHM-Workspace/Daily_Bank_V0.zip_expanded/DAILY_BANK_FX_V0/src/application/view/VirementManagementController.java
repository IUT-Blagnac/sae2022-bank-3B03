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
import model.data.CompteCourant;
import model.data.transports.CompteCourantData;

public class VirementManagementController implements Initializable{
	
	// Etat application
	private DailyBankState dbs;
	private ClientsManagement cm;

	// Fenêtre physique
	private Stage primaryStage;

	// Données de la fenêtre
	private ObservableList<CompteCourantData> olc;
	
	// Manipulation de la fenêtre
		public void initContext(Stage _primaryStage, DailyBankState _dbstate) {
			this.primaryStage = _primaryStage;
			this.dbs = _dbstate;
			this.configure();
		}

		private void configure() {
			this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

			this.olc = FXCollections.observableArrayList();
			this.lvComptes.setItems(this.olc);
			this.lvComptes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			this.lvComptes.getFocusModel().focus(-1);
		}

		public void displayDialog(CompteCourant cpte) {
			this.primaryStage.showAndWait();
		}

		// Gestion du stage
		private Object closeWindow(WindowEvent e) {
			this.doCancel();
			e.consume();
			return null;
		}

	@FXML
	private TextField txtNum;
	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtPrenom;
	@FXML
	private ListView<CompteCourantData> lvComptes;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void doCancel() {
		this.primaryStage.close();
	}
	
	@FXML
	private void doRechercher() {
		int numCompte;
		try {
			String nc = this.txtNum.getText();
			if (nc.equals("")) {
				numCompte = -1;
			} else {
				numCompte = Integer.parseInt(nc);
				if (numCompte < 0) {
					this.txtNum.setText("");
					numCompte = -1;
				}
			}
		} catch (NumberFormatException nfe) {
			this.txtNum.setText("");
			numCompte = -1;
		}

		String debutNom = this.txtNom.getText();
		String debutPrenom = this.txtPrenom.getText();

		if (numCompte != -1) {
			this.txtNom.setText("");
			this.txtPrenom.setText("");
		} else {
			if (debutNom.equals("") && !debutPrenom.equals("")) {
				this.txtPrenom.setText("");
			}
		}

		// Recherche des clients en BD. cf. AccessClient > getClients(.)
		// numCompte != -1 => recherche sur numCompte
		// numCompte != -1 et debutNom non vide => recherche nom/prenom
		// numCompte != -1 et debutNom vide => recherche tous les clients
		ArrayList<CompteCourantData> listeComptes;
		listeComptes = this.cm.getComptesData(numCompte, debutNom, debutPrenom);

		this.olc.clear();
		for (CompteCourantData compte : listeComptes) {
			this.olc.add(compte);
		}
	}

}