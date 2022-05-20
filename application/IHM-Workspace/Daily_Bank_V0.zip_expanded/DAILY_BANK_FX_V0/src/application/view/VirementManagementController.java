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
import model.data.Client;

public class VirementManagementController implements Initializable{
	
	// Etat application
	private DailyBankState dbs;
	private ClientsManagement cm;

	// Fenêtre physique
	private Stage primaryStage;

	// Données de la fenêtre
	private ObservableList<Client> olc;
	
	// Manipulation de la fenêtre
		public void initContext(Stage _primaryStage, ClientsManagement _cm, DailyBankState _dbstate) {
			this.cm = _cm;
			this.primaryStage = _primaryStage;
			this.dbs = _dbstate;
			this.configure();
		}

		private void configure() {
			this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

			this.olc = FXCollections.observableArrayList();
			this.lvClients.setItems(this.olc);
			this.lvClients.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			this.lvClients.getFocusModel().focus(-1);
		}

		public void displayDialog() {
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
	private ListView<Client> lvClients;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
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
		ArrayList<Client> listeCli;
		listeCli = this.cm.getlisteComptes(numCompte, debutNom, debutPrenom);

		this.olc.clear();
		for (Client cli : listeCli) {
			this.olc.add(cli);
		}
	}

}