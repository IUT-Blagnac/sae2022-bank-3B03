package application;

import application.model.BlackBot;
import application.view.UIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BlackJackApp extends Application {

	// STAGE PRINCIPALE
	private static Stage primaryStage;
	// root
	private BorderPane root;
	// BOT
	private BlackBot engine;

	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BlackJackApp.primaryStage = primaryStage;
		this.root = new BorderPane();
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(BlackJackApp.class.getResource("ressource/css/uistyle.css").toExternalForm());
		
		primaryStage.setTitle("Black Forest");
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image(BlackJackApp.class.getResourceAsStream("ressource/image/blackforest.png")));
		
		this.game();	
		this.UI();

		
		primaryStage.show();		
	}
	
	public void UI() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(BlackJackApp.class.getResource("view/UI.fxml"));
			BorderPane uiPane = loader.load();
			
			UIController ctrlUI = loader.getController();
			ctrlUI.setStage(BlackJackApp.primaryStage);
			ctrlUI.setEngine(this.engine);
			ctrlUI.setMoney(100, 100);

			this.root.setCenter(uiPane);
		}catch (Exception e) {
			System.out.println("Ressource FXML Indisponible : UI.fxml");
			e.printStackTrace();
		}
	}
	
	private void game() {
		engine = new BlackBot(2);
		System.out.println(this.engine);
	}
}
