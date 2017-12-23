package application;
import java.awt.Point;


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
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AppliLaby extends Application {
	/**terrain liee a cet objet graphique*/
	private static Terrain terrain;
	/**nb de robots*/
	int nbRobots = 10;
	/**vitesse de simulation*/
	static double tempo = 300;
	/**taille de la terrain*/
	static private int taille;
	/**taille d'une cellule en pixel*/
	static private int espace = 10;
	/**dessins des cellules*/
	private  static Rectangle [][] environnement; 
	/**dessin des robots*/
	public static Circle[]robots;
	/**points de base des éléments graphiques*/
	Group root ;
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
	


	@Override
	/**initialisation de l'application graphique*/
	public void start(Stage primaryStage) {
		int tailleTerrain = 65;
		robots = new Circle[nbRobots];
		terrain = new Terrain(tailleTerrain, nbRobots);
		taille = terrain.getTaille();
		construireScenePourRobots( primaryStage);

	}



	/**construction du théâtre et de la scène */
	void construireScenePourRobots(Stage primaryStage) 
	{
		//definir la scene principale
		 root = new Group();
		Scene scene = new Scene(root, 2*espace + taille*espace, 2*espace + taille*espace, Color.BLACK);
		primaryStage.setTitle("Life...");
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
	
	static public void moveIntrus(Intrus intrus, Point  to)
	{
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
		if(intrus.getEtat() == EtatIntrus.RETOUR) dessin.setFill(Color.YELLOW);
		update();

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
		for(Robot  f : terrain.getLesRobots())
		{
			double x = f.getPoint().getX();
			double y = f.getPoint().getY();
			f.setDessin(new Circle(espace*(2*x+3)/2 ,espace*(2*y+3)/2, espace/2, AppliLaby.COLROBOT));
			root.getChildren().add(f.getDessin());
		}

		//creation du dessin de l'intrus
		Intrus intrus = terrain.getIntrus();
		double x = intrus.getPoint().getX();
		double y = intrus.getPoint().getY();
		intrus.setDessin(new Circle(espace*(2*x+3)/2 ,espace*(2*y+3)/2, espace/1.2, AppliLaby.COLINTRUS));
		root.getChildren().add(intrus.getDessin());


		//petit effet de flou général
		root.setEffect(new BoxBlur(1, 1, 3));
	}

	
	
	/** 
	 * mise a jour de l'environnement autour de l'intrus
	 */
	static void update()
	{
		Cellule[][] grille = terrain.getGrille();

		for(int i=-2; i<=2; i++)
			for(int j=-2; j<=2; j++)
			{
				int xi = ((terrain.getIntrus().getPoint().x + i)+taille)%taille;
				int yj = ((terrain.getIntrus().getPoint().y + j)+taille)%taille;
				Cellule cell = grille[xi][yj];
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
					else 	AppliLaby.environnement[xi][yj].setFill(AppliLaby.COLSOL);					
				}
			}

	}




	/**lancement de l'application*/
	public static void main(String[] args) {
		launch(args);
	}
}