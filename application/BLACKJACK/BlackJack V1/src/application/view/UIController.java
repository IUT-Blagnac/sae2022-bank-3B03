package application.view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import application.BlackJackApp;
import application.model.BlackBot;
import application.model.EtatBlackBot;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class UIController implements Initializable{

	private Stage primaryStage;
	private BlackBot engine;
	/**
	 * FXML Partie
	 */
	@FXML
	private Button lancerPartie;
	
	@FXML
	private Label argentJoueur1;
	private IntegerProperty a1 = new SimpleIntegerProperty();
	
	@FXML
	private Label argentJoueur2;
	private IntegerProperty a2 = new SimpleIntegerProperty();
	
	private int mises[] = new int[2];
	
	/**
	 * Croupier
	 */
	@FXML
	private TextArea affichageCroupier;
	
	/**
	 * FXML Iteractif du joueur 1
	 */
	@FXML
	private Button miseJoueur1;
	@FXML
	private TextField textFieldMiseJoueur1;
	@FXML
	private Button resterJoueur1;
	@FXML
	private Button tirerJoueur1;
	/**
	 * FXML Affichage du joueur 1
	 */
	@FXML
	private TextArea affichageJoueur1;
	
	/**
	 * FXML Iteractif du joueur 2
	 */
	@FXML
	private Button miseJoueur2;
	@FXML
	private TextField textFieldMiseJoueur2;
	@FXML
	private Button resterJoueur2;
	@FXML
	private Button tirerJoueur2;
	/**
	 * FXML Affichage du joueur 1
	 */
	@FXML
	private TextArea affichageJoueur2;
	
	/**
	 * Variables
	 */
	private BooleanBinding mise1;
	private BooleanBinding mise2;
	
	private boolean hasMised1;
	private boolean hasMised2;
	
	private boolean hasPlayed1;
	private boolean hasPlayed2;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.resterJoueur1.disableProperty().set(true);
		this.tirerJoueur1.disableProperty().set(true);
		this.resterJoueur2.disableProperty().set(true);
		this.tirerJoueur2.disableProperty().set(true);
		//BINDS
		this.hasMised1 = new Boolean(false);
		this.mise1 = this.textFieldMiseJoueur1.textProperty().isEmpty();
		this.mise2 = this.textFieldMiseJoueur2.textProperty().isEmpty();
		this.miseJoueur1.disableProperty().bind(this.mise1);
		this.miseJoueur2.disableProperty().bind(this.mise2);
		this.argentJoueur1.textProperty().bindBidirectional(a1, new NumberStringConverter());
		this.argentJoueur2.textProperty().bindBidirectional(a2, new NumberStringConverter());

		//LISTENER
		this.textFieldMiseJoueur1.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					textFieldMiseJoueur1.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		this.textFieldMiseJoueur2.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					textFieldMiseJoueur2.setText(newValue.replaceAll("[^\\d]", ""));
		        }
			}
		});
		
	}
	
	public void setEngine(BlackBot e) {
		this.engine = e;
		System.out.println(this.engine);
	}
	
	public void setStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setOnCloseRequest(event -> {event.consume(); quit();});
	}
	
	public void setMoney(int argentJ1, int argentJ2) {
		this.a1.set(argentJ1);
		this.a2.set(argentJ2);
	}
	
	public boolean canStart() {
		if(this.hasMised1 == true || this.hasMised2 == true) return true;
		return false;
	}

	@SuppressWarnings("unused")
	private void errorState(EtatBlackBot e, String s) {
		if(e == EtatBlackBot.GAIN) System.out.println("Le jeu était en état GAIN mais :"+s);
		if(e == EtatBlackBot.MISE) System.out.println("Le jeu était en état MISE mais :"+s);
		if(e == EtatBlackBot.JEU) System.out.println("Le jeu était en état JEU mais :"+s);
		Alert error = new Alert(AlertType.WARNING); error.initOwner(this.primaryStage);
		error.setTitle("Erreur");
		error.setContentText("Une erreur vous empêche de continuer.\nFermeture de l'application...");
		error.showAndWait();
		this.primaryStage.close();
	}
	
	@FXML
	public void miseJ1() {
		if(this.engine.getEtat() == EtatBlackBot.MISE) {
			int miseTmp = Integer.parseInt(this.textFieldMiseJoueur1.getText());
			if(miseTmp > this.a1.getValue()) {
				petitCoquin("Tu essaies de miser plus que ton porte-monnaie ;)");
			}else if(miseTmp == 0) {
				this.hasMised1 = false; this.mises[0] = 0;
			}
			else{this.engine.miser(0, miseTmp); this.hasMised1 = true; this.mises[0] = miseTmp;}
		}else {petitCoquin("Vous ne pouvez plus miser !");}
		
	}
	
	@FXML
	public void miseJ2() {
		if(this.engine.getEtat() == EtatBlackBot.MISE) {
			int miseTmp = Integer.parseInt(this.textFieldMiseJoueur2.getText());
			if(miseTmp > this.a2.getValue()) {
				petitCoquin("Tu essaies de miser plus que ton porte-monnaie ;)");
			}else if(miseTmp == 0) {
				this.hasMised2 = false; this.mises[1] = 0;
			}
			else{this.engine.miser(1, miseTmp); this.hasMised2 = true; this.mises[1] = miseTmp;}
		}else {petitCoquin("Vous ne pouvez plus miser !");}
		
	}
	
	@FXML
	public void rester1() {
		if(this.engine.getEtat() == EtatBlackBot.JEU && this.engine.getFinJoueurs(0) == false) {
			this.engine.terminer(0);
			this.resterJoueur1.disableProperty().set(true);
			this.tirerJoueur1.disableProperty().set(true);
			if(this.hasPlayed2 == true || this.hasMised2 == false) this.endTour();
			this.actualizeText();
		}else {petitCoquin("Vous ne pouvez pas rester !");}
	}
	
	@FXML
	public void rester2() {
		if(this.engine.getEtat() == EtatBlackBot.JEU && this.engine.getFinJoueurs(1) == false) {
			this.engine.terminer(1);
			this.resterJoueur2.disableProperty().set(true);
			this.tirerJoueur2.disableProperty().set(true);
			if(this.hasPlayed1 == true || this.hasMised1 == false) this.endTour();
			this.actualizeText();
		}else {petitCoquin("Vous ne pouvez pas rester !");}
	}
	
	@FXML
	public void tirer1() {
		if(this.engine.getEtat() == EtatBlackBot.JEU && this.engine.getFinJoueurs(0) == false) {
			this.engine.tirer(0);
			if(this.engine.getFinJoueurs(0) == true) {
				this.resterJoueur1.disableProperty().set(true);
				this.tirerJoueur1.disableProperty().set(true);
				if(this.hasPlayed2 == true || this.hasMised2 == false) this.endTour();
			}
			this.actualizeText();
		}else {petitCoquin("Vous ne pouvez pas tirer !");}
	}
	
	@FXML
	public void tirer2() {
		if(this.engine.getEtat() == EtatBlackBot.JEU && this.engine.getFinJoueurs(1) == false) {
			this.engine.tirer(1);
			if(this.engine.getFinJoueurs(1) == true) {
				this.resterJoueur2.disableProperty().set(true);
				this.tirerJoueur2.disableProperty().set(true);
				if(this.hasPlayed1 == true || this.hasMised1 == false) this.endTour();
			}
			this.actualizeText();
		}else {petitCoquin("Vous ne pouvez pas tirer !");}
	}
	
	@FXML
	private void startGame() {
		System.out.println(this.engine);
		if(this.canStart()) {
			this.engine.distribuer();
			this.textFieldMiseJoueur1.setDisable(true);
			this.textFieldMiseJoueur2.setDisable(true);
			this.resterJoueur1.disableProperty().set(false);
			this.tirerJoueur1.disableProperty().set(false);
			this.resterJoueur2.disableProperty().set(false);
			this.tirerJoueur2.disableProperty().set(false);
			this.actualizeText();
		}else {
			petitCoquin("Les deux joueurs n'ont pas misés !");
		}
	}
	
	private void petitCoquin(String s) {
		Alert coquin = new Alert(AlertType.INFORMATION); coquin.setTitle("");
		coquin.setHeaderText("Petit malin..."); coquin.initOwner(primaryStage);
		coquin.setContentText("Vous essayez d'outre-passer les lois de la nature !\n"+s);
		coquin.setGraphic(new ImageView(new Image(BlackJackApp.class.getResource("ressource/image/nature-resize.png").toExternalForm())));
		coquin.showAndWait();
	}
	
	@FXML
	private void quit() {
		Alert quit = new Alert(AlertType.CONFIRMATION);
		quit.setTitle("Quitter"); quit.initOwner(primaryStage);
		quit.setContentText("Voulez-vous vraiment quitter ?");
		quit.setGraphic(new ImageView(new Image(BlackJackApp.class.getResource("ressource/image/nature-resize.png").toExternalForm())));
		Optional<ButtonType> rep = quit.showAndWait();
		if(rep.orElse(null) == ButtonType.OK) this.primaryStage.close();
	}
	
	private void actualizeText() {
		this.affichageJoueur1.setText(this.engine.getMainJoueurs(0).toString());
		this.affichageJoueur2.setText(this.engine.getMainJoueurs(1).toString());
		this.affichageCroupier.setText(this.engine.getMainBanque().toString());
		if(this.engine.getEtat() == EtatBlackBot.GAIN) {
			String gainJ1;
			String gainJ2;
			if(this.engine.getGainJoueurs(0) > 0) {
				gainJ1 = "Vous avez gagné !";
			}else {gainJ1 = "Vous avez perdu...";}
			if(this.engine.getGainJoueurs(1) > 0) {
				gainJ2 = "Vous avez gagné !";
			}else {gainJ2 = "Vous avez perdu...";}
			this.affichageJoueur1.setText(this.affichageJoueur1.getText() + gainJ1);
			this.affichageJoueur2.setText(this.affichageJoueur2.getText() + gainJ2);
			gestGains();
			endTour();
		}
	}
	
	
	
	private void gestGains() {
		if (this.engine.getGainJoueurs(0) > 0) {
			this.a1.set(this.a1.getValue() + this.engine.getGainJoueurs(0));
		} else {
			this.a1.set(this.a1.getValue() - this.mises[0]);
		}
		if (this.engine.getGainJoueurs(1) > 0) {
			this.a2.set(this.a2.getValue() + this.engine.getGainJoueurs(0));
		} else {
			this.a2.set(this.a2.getValue() - this.mises[1]);
		}
	}
	
	private void endTour() {
		this.hasMised1 = false;
		this.hasMised2 = false;
		this.hasPlayed2 = false;
		this.hasPlayed1 = false;
		this.engine.relancerPartie();
		if(this.a1.getValue() > 0) this.textFieldMiseJoueur1.setDisable(false);
		if(this.a2.getValue() > 0) this.textFieldMiseJoueur2.setDisable(false);
	}
}
