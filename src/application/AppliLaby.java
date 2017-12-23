package application;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import modele.Cellule;
import modele.Direction;
import modele.EtatIntrus;
import modele.Intrus;
import modele.Robot;
import modele.Terrain;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AppliLaby extends Application {
	/**terrain liee a cet objet graphique*/
	private static Terrain terrain;
	/**nb de robots en mode facile*/
	int nbRobotsFacile = 5;
	/**nb de robots en mode moyen*/
	int nbRobotsMoyen = 10;
	/**nb de robots en mode difficile*/
	int nbRobotsDifficile = 15;
	/** taux d'obstacles en mode facile */
	double tauxObstaclesFacile = 0.05;
	/** taux d'obstacles en mode moyen */
	double tauxObstaclesMoyen = 0.1;
	/** taux d'obstacles en mode difficile */
	double tauxObstaclesDifficile = 0.15;
	/** champ de perception de l'intrus en mode facile */
	int champPerceptionIntrusFacile = 6;
	/** champ de perception de l'intrus en mode moyen */
	int champPerceptionIntrusMoyen = 4;
	/** champ de perception de l'intrus en mode difficile */
	int champPerceptionIntrusDifficile = 2;
	/** champ de perception des robots en mode facile */
	int champPerceptionRobotsFacile = 2;
	/** champ de perception des robots en mode moyen */
	int champPerceptionRobotsMoyen = 4;
	/** champ de perception des robots en mode difficile */
	int champPerceptionRobotsDifficile = 6;
	/** nombre de robots */
	int nbRobots;
	/** taux d'obstacles */
	double tauxObstacles;
	/** champ de perception de l'intrus*/
	int champPerceptionIntrus;
	/** champ de perception des robots */
	int champPerceptionRobots;
	/** difficulte choisie */
	String difficulte = "";
	/**vitesse de simulation*/
	static double tempo = 300;
	/**taille de la terrain*/
	static private int taille;
	/**taille d'une cellule en pixel*/
	static private int espace = 10;
	/**dessins des cellules*/
	private  static Rectangle [][] environnement; 
	/**dessin des robots*/
	public static Circle[] robots;
	/**points de base des éléments graphiques*/
	Group root;
	/**cycle de l'activation*/
	public static Timeline littleCycle;
	/**cycle de l'animation robot*/
	static Timeline timelineRobots;


	/**couleur d'un obstacle*/
	public static Color COLOBSTACLE = Color.LIGHTGREEN;
	/**couleur d'un sol*/
	public static Color COLSOL= Color.DIMGRAY;
	/**couleur d'un robot*/
	public static Color COLROBOT = Color.TOMATO;
	/**couleur d'un intrus*/
	public static Color COLINTRUS = Color.DODGERBLUE;
	/**couleur d'un tresor*/
	public static Color COLTRESOR = Color.YELLOW;
	/** Couleur d'un porte */
	public static Color COLSORTIE = Color.WHITE;
	
	@Override
	/**initialisation de l'application graphique*/
	public void start(Stage primaryStage) {
		
		int tailleTerrain = 65;
		
		 primaryStage.setTitle("Accueil - Poursuite de Robots");
		 
		 /** Options de jeu pour choisir le niveau de difficulté */
		 Button btnFacile = new Button();
		 btnFacile.setText("Facile");
		 
		 Button btnMoyen = new Button();
		 btnMoyen.setText("Moyen");
		 
		 Button btnDifficile = new Button();
		 btnDifficile.setText("Difficile");
		 
		 Button btnSauvegarde = new Button();
		 btnSauvegarde.setText("Charger une partie");
		 
		 /** Lancement du jeu en mode facile */
		 btnFacile.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 
				 difficulte = "facile";
				 champPerceptionIntrus = champPerceptionIntrusFacile;
				 champPerceptionRobots = champPerceptionRobotsFacile;
				 nbRobots = nbRobotsFacile;
				 tauxObstacles = tauxObstaclesFacile;
				 robots = new Circle[nbRobots];
				 terrain = new Terrain(tailleTerrain, nbRobots, tauxObstacles);
				 taille = terrain.getTaille();
				 construireScenePourRobots(primaryStage);
			 }
		 });
		 
		 /** Lancement du jeu en mode moyen */
		 btnMoyen.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 
				 difficulte = "moyen";
				 champPerceptionIntrus = champPerceptionIntrusMoyen;
				 champPerceptionRobots = champPerceptionRobotsMoyen;
				 nbRobots = nbRobotsMoyen;
				 tauxObstacles = tauxObstaclesMoyen;
				 robots = new Circle[nbRobots];
				 terrain = new Terrain(tailleTerrain, nbRobots, tauxObstacles);
				 taille = terrain.getTaille();
				 construireScenePourRobots(primaryStage);
			 }
		 });

		 /** Lancement du jeu en mode difficile */
		 btnDifficile.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 
				 difficulte = "difficile";
				 champPerceptionIntrus = champPerceptionIntrusDifficile;
				 champPerceptionRobots = champPerceptionRobotsDifficile;
				 nbRobots = nbRobotsDifficile;
				 tauxObstacles = tauxObstaclesDifficile;
				 robots = new Circle[nbRobots];
				 terrain = new Terrain(tailleTerrain, nbRobots, tauxObstacles);
				 taille = terrain.getTaille();
				 construireScenePourRobots(primaryStage);
			 }
		 });
		 
		 /** Lancmement du jeu selon la plus récente sauvegarde */
		 btnSauvegarde.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 
				 ArrayList<String> liste = new ArrayList<String>();
				 Charset charset = Charset.forName("UTF-8");
				 Path path = Paths.get("sauvegarde.txt");
				 
				 try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
					 
				     String ligne = null;
				     
				     while ((ligne = reader.readLine()) != null) {
				         liste.add(ligne);
				     }
				     
				     // On récupère les infos du fichier pour lancer une nouvelle partie selon la sauvegarde
				     difficulte = liste.get(9);
				     champPerceptionIntrus = Integer.valueOf(liste.get(3));
					 champPerceptionRobots = Integer.valueOf(liste.get(5));
					 int nbRobots = Integer.valueOf(liste.get(4));
					 Point pointTresor = new Point(Integer.valueOf(liste.get(7)), Integer.valueOf(liste.get(8)));
					 robots = new Circle[nbRobots];
					 terrain = new Terrain(tailleTerrain, nbRobots, Double.valueOf(liste.get(6)), Integer.valueOf(liste.get(1)), Integer.valueOf(liste.get(2)), EtatIntrus.valueOf(liste.get(0)), pointTresor);
					 taille = terrain.getTaille();
					 construireScenePourRobots(primaryStage); 
				 } catch (IOException x) {
				     System.err.format("IOException: %s%n", x);
				 }
			 }
		 });
	     
		 // Affichage des boutons
		 VBox vBox = new VBox(btnFacile, btnMoyen, btnDifficile, btnSauvegarde);
		 vBox.setStyle("-fx-alignment: center;");
		 vBox.setSpacing(30.0);
		  
		 // Construction de la scène
		 StackPane root = new StackPane();
		 root.getChildren().add(vBox);
		 primaryStage.setScene(new Scene(root, 2*espace + tailleTerrain*espace, 2*espace + tailleTerrain*espace));
		 primaryStage.show();
	}
	
	// Ecran de fin de partie, on propose une partie plus difficile
	void proposerPartieDifficile(Stage primaryStage) {
		
		littleCycle.stop();
		int tailleTerrain = 65;
		
		StackPane root = new StackPane();
		
		Label label = new Label("Félicitations, vous avez gagné !");
		HBox hBox = new HBox(label);
		hBox.setStyle("-fx-alignment: center;");
		
		Button btnRelancer = new Button();
		btnRelancer.setText("Rejouer");
		
		Button btnQuitter = new Button();
		btnQuitter.setText("Quitter");
		
		// On relance une nouvelle parie
		btnRelancer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				switch(difficulte) {

				case "facile":
					difficulte = "moyen";
					champPerceptionIntrus = champPerceptionIntrusMoyen;
					champPerceptionRobots = champPerceptionRobotsMoyen;
					nbRobots = nbRobotsMoyen;
					tauxObstacles = tauxObstaclesMoyen;
					robots = new Circle[nbRobots];
					terrain = new Terrain(tailleTerrain, nbRobots, tauxObstacles);
					taille = terrain.getTaille();
					construireScenePourRobots(primaryStage);
					System.out.println("Je rejoue moyen");
					break;
				case "moyen":
					difficulte = "difficile";
					champPerceptionIntrus = champPerceptionIntrusDifficile;
					champPerceptionRobots = champPerceptionRobotsDifficile;
					nbRobots = nbRobotsDifficile;
					tauxObstacles = tauxObstaclesDifficile;
					robots = new Circle[nbRobots];
					terrain = new Terrain(tailleTerrain, nbRobots, tauxObstacles);
					taille = terrain.getTaille();
					construireScenePourRobots(primaryStage);
					System.out.println("Je rejoue difficile");
					break;
				case "difficile":
					difficulte = "difficile";
					champPerceptionIntrus = champPerceptionIntrusDifficile;
					champPerceptionRobots = champPerceptionRobotsDifficile;
					nbRobots = nbRobotsDifficile;
					tauxObstacles = tauxObstaclesDifficile;
					robots = new Circle[nbRobots];
					terrain = new Terrain(tailleTerrain, nbRobots, tauxObstacles);
					taille = terrain.getTaille();
					construireScenePourRobots(primaryStage);
					System.out.println("Je rejoue difficile");
					break;
				default:
					System.out.println("ERREUR : difficulté inconnue");
					primaryStage.close();
					break;
				}
			}
		});
		 
		// On quitte l'application
		btnQuitter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				primaryStage.close();
			}
		});
		
		HBox hBoxBtn = new HBox(btnRelancer, btnQuitter);
		hBoxBtn.setStyle("-fx-alignment: center;");
		hBoxBtn.setSpacing(15.0);
		
		VBox vBox = new VBox(hBox, hBoxBtn);
		vBox.setStyle("-fx-alignment: center;");
		vBox.setSpacing(30.0);
		
		root.getChildren().add(vBox);
		
		Scene scene = new Scene(root, 2*espace + taille*espace, 2*espace + taille*espace);
		primaryStage.setTitle("Fin de partie - Poursuite de Robots");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/** Ecran de fin de partie, on propose une partie plus facile */
	void proposerPartieFacile(Stage primaryStage) {
		
		littleCycle.stop();
		int tailleTerrain = 65;
		
		StackPane root = new StackPane();
		
		Label label = new Label("Oh non, vous avez perdu !");
		HBox hBox = new HBox(label);
		hBox.setStyle("-fx-alignment: center;");
		
		Button btnRelancer = new Button();
		btnRelancer.setText("Rejouer");
		
		Button btnQuitter = new Button();
		btnQuitter.setText("Quitter");
		
		// On relance une partie plus facile selon la difficulté courante
		btnRelancer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				switch(difficulte) {

				case "facile":
					difficulte = "facile";
					champPerceptionIntrus = champPerceptionIntrusFacile;
					champPerceptionRobots = champPerceptionRobotsFacile;
					nbRobots = nbRobotsFacile;
					tauxObstacles = tauxObstaclesFacile;
					robots = new Circle[nbRobots];
					terrain = new Terrain(tailleTerrain, nbRobots, tauxObstacles);
					taille = terrain.getTaille();
					construireScenePourRobots(primaryStage);
					System.out.println("Je rejoue facile");
					break;
				case "moyen":
					difficulte = "facile";
					champPerceptionIntrus = champPerceptionIntrusFacile;
					champPerceptionRobots = champPerceptionRobotsFacile;
					nbRobots = nbRobotsFacile;
					tauxObstacles = tauxObstaclesFacile;
					robots = new Circle[nbRobots];
					terrain = new Terrain(tailleTerrain, nbRobots, tauxObstacles);
					taille = terrain.getTaille();
					construireScenePourRobots(primaryStage);
					System.out.println("Je rejoue facile");
					break;
				case "difficile":
					difficulte = "moyen";
					champPerceptionIntrus = champPerceptionIntrusMoyen;
					champPerceptionRobots = champPerceptionRobotsMoyen;
					nbRobots = nbRobotsMoyen;
					tauxObstacles = tauxObstaclesMoyen;
					robots = new Circle[nbRobots];
					terrain = new Terrain(tailleTerrain, nbRobots, tauxObstacles);
					taille = terrain.getTaille();
					construireScenePourRobots(primaryStage);
					System.out.println("Je rejoue moyen");
					break;
				default:
					System.out.println("ERREUR : difficulté inconnue");
					primaryStage.close();
					break;
				}
			}
		});

		// On quitte l'application
		btnQuitter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				primaryStage.close();
			}
		});
		
		HBox hBoxBtn = new HBox(btnRelancer, btnQuitter);
		hBoxBtn.setStyle("-fx-alignment: center;");
		hBoxBtn.setSpacing(15.0);
		
		VBox vBox = new VBox(hBox, hBoxBtn);
		vBox.setStyle("-fx-alignment: center;");
		vBox.setSpacing(30.0);
		
		root.getChildren().add(vBox);
		
		Scene scene = new Scene(root, 2*espace + taille*espace, 2*espace + taille*espace);
		primaryStage.setTitle("Fin de partie - Poursuite de Robots");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	



	/**construction du théâtre et de la scène */
	void construireScenePourRobots(Stage primaryStage) 
	{
		//definir la scene principale
		 root = new Group();
		 
		Scene scene = new Scene(root, 2*espace + taille*espace, 2*espace + taille*espace, Color.BLACK);
		primaryStage.setTitle("Jeu - Poursuite de Robots");
		primaryStage.setScene(scene);
		//definir les acteurs et les habiller
		AppliLaby.environnement = new Rectangle[taille][taille];
		dessinEnvironnement( root);
		 timelineRobots = new Timeline();
		//afficher le theatre
		primaryStage.show();
		

		//-----lancer le timer pour faire vivre les fourmis et l'environnement
		 littleCycle = new Timeline(new KeyFrame(Duration.millis(tempo), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
			
				timelineRobots.getKeyFrames().clear();
				terrain.animGrille();
				timelineRobots.play();
				terrain.getIntrus().getDessin().requestFocus();
				
				// Fin de partie
				if(terrain.getIntrus().getEtat().equals(EtatIntrus.GAGNE)) {
					proposerPartieDifficile(primaryStage);
				}
				else if(terrain.getIntrus().getEtat().equals(EtatIntrus.PERDU)) {
					proposerPartieFacile(primaryStage);
				}
			}
		}));
		littleCycle.setCycleCount(Timeline.INDEFINITE);
		littleCycle.play();

		//creation de l'intrus et ajout de la gstion d evenement clavier associe
		Intrus intrus = terrain.getIntrus();
		Circle dessinIntrus = intrus.getDessin();
		
		
		
		dessinIntrus.requestFocus();
		dessinIntrus.setOnKeyPressed(e->{
			switch(e.getCode())
			{
			case UP: intrus.setDirection(Direction.NORD); intrus.bougerVersDirection(); break;
			case LEFT: intrus.setDirection(Direction.OUEST); intrus.bougerVersDirection(); break;
			case DOWN: intrus.setDirection(Direction.SUD); intrus.bougerVersDirection(); break;
			case RIGHT: intrus.setDirection(Direction.EST); intrus.bougerVersDirection(); break;
			default:
			}
		});
	}

	/**deplace un robot vers une position
	 * @param roby le robot a deplacer
	 * @param to les coordonnes dans le la case terrain*/
	static public void moveRobot(Robot roby,  Point  to)
	{
		Circle dessin = roby.getDessin();
		double x = (espace*(2*to.getX()+ 3)/2);
		double y = (espace*(2*to.getY()+ 3)/2);
		/**cycle de l'animation*/
		timelineRobots.getKeyFrames().addAll(
				new KeyFrame(new Duration(tempo), 
						new KeyValue(dessin.centerXProperty(), x, Interpolator.EASE_BOTH  ),
						new KeyValue(dessin.centerYProperty(), y, Interpolator.EASE_BOTH  )
						)
				);
	}
	
	/**deplace un intrus vers une position
	 * @param intrus l'intrus a deplacer
	 * @param to les coordonnes dans le la case terrain*/
	
	static public void moveIntrus(Intrus intrus, Point  to) {
		
		Circle dessin = intrus.getDessin();

		double x = (espace*(2*to.getX()+ 3)/2);
		double y = (espace*(2*to.getY()+ 3)/2);
		/**cycle de l'animation*/
		Timeline timeline = new Timeline();
		timeline.getKeyFrames().addAll(
				new KeyFrame(new Duration(tempo/2), 
						new KeyValue(dessin.centerXProperty(), x  ),
						new KeyValue(dessin.centerYProperty(), y  )
						)
				);
		timeline.play();
		if(intrus.getEtat() == EtatIntrus.RETOUR) 
			dessin.setFill(Color.YELLOW);

			
		update();
		oublierChemin();
	}
	
	/**
	 * Diminue l'opacité des cases visitées en fonction du nombre d'actions réalisées par l'intrus
	 */
	public static void oublierChemin() {
		
		Cellule[][] grille = terrain.getGrille();
		
		for(int i = 0; i < terrain.getTaille(); i++) {
			
			for(int j = 0; j < terrain.getTaille(); j++) {
				
				Cellule cell = grille[i][j];
				
				if(cell.isVisitee()) {
					
					// On diminue l'opacité d'une cellule
					AppliLaby.environnement[i][j].setOpacity(cell.getOpacite());
					cell.setOpacite(cell.getOpacite()-0.02);
					if(cell.getOpacite() <= 0) {
						
						cell.setOpacite(1);
						cell.setVisitee(false);
					}
				}
			}
		}
	}
	
	/** 
	 *creation des cellules et de leurs habits
	 *@param root groupe graphique auquel doivent etre attache les dessins
	 */
	void dessinEnvironnement(Group root)
	{
		// chaque parcelle de l'environnement est verte
		for(int i=0; i<taille; i++)
			for(int j=0; j<taille; j++)
			{
				
				AppliLaby.environnement[i][j] = new Rectangle((i+1)*(espace), (j+1)*(espace), espace, espace);
				root.getChildren().add(AppliLaby.environnement[i][j]);
			}
		
		//création des robots
		for(Robot  r : terrain.getLesRobots())
		{
			double x = r.getPoint().getX();
			double y = r.getPoint().getY();
			r.setDessin(new Circle(espace*(2*x+3)/2 ,espace*(2*y+3)/2, espace/2, AppliLaby.COLROBOT));
			r.setChampPerception(champPerceptionRobots);
			root.getChildren().add(r.getDessin());
		}

		//creation du dessin de l'intrus
		Intrus intrus = terrain.getIntrus();
		intrus.setChampPerception(champPerceptionIntrus);
		double x = intrus.getPoint().getX();
		double y = intrus.getPoint().getY();
		intrus.setDessin(new Circle(espace*(2*x+3)/2 ,espace*(2*y+3)/2, espace/1.2, COLINTRUS));
		
		root.getChildren().add(intrus.getDessin());
		
		Button btnSauvegarde = new Button();
		btnSauvegarde.setText("Sauvegarder");
		btnSauvegarde.setStyle("-fx-focus-color: transparent;");
		
		 btnSauvegarde.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 
				 try {
					 
					 if(!terrain.getIntrus().getEtat().equals(EtatIntrus.GAGNE) && !terrain.getIntrus().getEtat().equals(EtatIntrus.PERDU))
						 sauvegarderPartie();
					
				} catch (FileNotFoundException | UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		 });
		 
		root.getChildren().add(btnSauvegarde);
		 
		//petit effet de flou général
		root.setEffect(new BoxBlur(1, 1, 3));
	}
	
	/**
	 * Sauvegarde les données importantes d'une partie depuis le fichier sauvegarde.txt
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	public void sauvegarderPartie() throws FileNotFoundException, UnsupportedEncodingException {
		
		Intrus intrus = terrain.getIntrus();
		PrintWriter writer = new PrintWriter("sauvegarde.txt", "UTF-8");
		// Sauvegarde de l'état de l'intrus 
		writer.println(intrus.getEtat());
		// Sauvegarde de la position de l'intrus
		writer.println(intrus.getPoint().x);
		writer.println(intrus.getPoint().y);
		// Sauvegarde du champ de perception de l'intrus
		writer.println(intrus.getChampPerception());
		// Sauvegarde du nombre de robots 
		writer.println(terrain.getNbRobots());
		// Sauvegarde du champ de perception des robots 
		writer.println(champPerceptionRobots);
		// Sauvegarde du taux d'obstacles
		writer.println(terrain.getTauxObstacles());
		// Sauvegarde de la position du tresor
		writer.println(terrain.getTresor().x);
		writer.println(terrain.getTresor().y);
		// Sauvegarde de la difficulté 
		writer.println(difficulte);
		
		writer.close();
	}
	
	/** 
	 * mise a jour de l'environnement autour de l'intrus
	 */
	static void update() {
		
		Cellule[][] grille = terrain.getGrille();
		int champPerception = terrain.getIntrus().getChampPerception();

		for(int i=-champPerception; i<=champPerception; i++)
			for(int j=-champPerception; j<=champPerception; j++)
			{
				int xi = ((terrain.getIntrus().getPoint().x + i)+taille)%taille;
				int yj = ((terrain.getIntrus().getPoint().y + j)+taille)%taille;
				Cellule cell = grille[xi][yj];
				
				//cell.setVisitee(true);
				cell.setOpacite(1);
				
				if(cell.isVisitee())
				{
					if(cell.isObstacle()) 
						AppliLaby.environnement[xi][yj].setFill(AppliLaby.COLOBSTACLE);
					else if(cell.isRobot()) 
						AppliLaby.environnement[xi][yj].setFill(AppliLaby.COLROBOT);
					else if(cell.isIntrus()) 
						AppliLaby.environnement[xi][yj].setFill(AppliLaby.COLINTRUS);
					else if(cell.isTresor())
						AppliLaby.environnement[xi][yj].setFill(AppliLaby.COLTRESOR);
					else if(cell.isSortie())
						AppliLaby.environnement[xi][yj].setFill(AppliLaby.COLSORTIE);
					else 	AppliLaby.environnement[xi][yj].setFill(AppliLaby.COLSOL);		
				}	
			}
	}




	/**lancement de l'application*/
	public static void main(String[] args) {
		launch(args);
	}
}