package application.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.DailyBankState;
import application.control.ClientsManagement;
import application.control.EmployesManagement;
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
	private EmployesManagement em ;
	
	private Stage primaryStage;
	
	private ObservableList<Employe> ole;
	
	// Manipulation de la fenêtre
		public void initContext(Stage _primaryStage, EmployesManagement _em, DailyBankState _dbstate) {
			this.em = _em;
			this.primaryStage = _primaryStage;
			this.dbs = _dbstate;
			this.configure();
		}

		private void configure() {
			this.primaryStage.setOnCloseRequest(e -> this.closeWindow(e));

			this.ole = FXCollections.observableArrayList();
			this.listeEmployes.setItems(this.ole);
			this.listeEmployes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			this.listeEmployes.getFocusModel().focus(-1);
			this.listeEmployes.getSelectionModel().selectedItemProperty().addListener(e -> this.validateComponentState());
			this.validateComponentState();
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
	Button btnModEmp ;
	@FXML
	Button btnDesacEmp ;
	
	@FXML
	TextField textRole ;
	@FXML
	TextField textNom ;
	@FXML
	TextField textPrenom ;
	
	@FXML
	ListView<Employe> listeEmployes ;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void doCancel() {
		this.primaryStage.close();
	}

	/*
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

		this.validateComponentState();
	}


	@FXML
	private void doComptesClient() {
		int selectedIndice = this.listeEmployes.getSelectionModel().getSelectedIndex();
		if (selectedIndice >= 0) {
			Client client = this.ole.get(selectedIndice);
		}
	}

	@FXML
	private void doModifierClient() {

		int selectedIndice = this.listeEmployes.getSelectionModel().getSelectedIndex();
		if (selectedIndice >= 0) {
			Client cliMod = this.olc.get(selectedIndice);
			Client result = this.cm.modifierClient(cliMod);
			if (result != null) {
				this.olc.set(selectedIndice, result);
			}
		}
	}

	@FXML
	private void doDesactiverClient() {
	}
	*/
	
	@FXML
	private void doNouvelEmploye() {
		Employe employe;
		employe = this.em.nouvelEmploye();
		if (employe != null) {
			this.ole.add(employe);
		}
	}
	
	private void validateComponentState() {
		// Non implémenté => désactivé
		this.btnDesacEmp.setDisable(true);
		int selectedIndice = this.listeEmployes.getSelectionModel().getSelectedIndex();
		if (selectedIndice >= 0) {
			this.btnModEmp.setDisable(false);
		} else {
			this.btnModEmp.setDisable(true);
		}
	}
}
