package modele;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

/**terrain des cellules pour l'appli  Labyrinthe
 * @author Emmanuel Adam*/
public class Terrain {
	/** grille a l'instant t*/
	private Cellule [][] grille;
	/** taille de la grille*/
	private int taille;
	/** nombre de robots */
	private int nbRobots = 1;
	/**taux d'obstacle*/
	private double tauxObstacles = 0.2;
	/**tableau des robots*/
	private Robot[] lesRobots;
	/**l'Intrus*/
	Intrus intrus;
	/**position du tresor*/
	Point pointTresor;

	public Terrain()
	{
		grille = new  Cellule[20][20];
		taille = 20;
	}


	/** 	constructeur par defaut, initialise la taille, le nombre de cellules initiales,  
    ainsi que les grilles a l'instnat t et t-1
	 * @param taille taille d'un côté du terrain
	 * @param _nbRobots nb de robots à créer
	 */
	public Terrain(int taille, int _nbRobots)
	{
		this.taille= taille;
		grille = new Cellule[taille][taille];
		this.nbRobots = _nbRobots ;
		init();
		initObstacle(tauxObstacles);
		initRobots(nbRobots);
		initIntrus();
		initTresor();
	}


	/** 
	 * initialise de la grille
	 */
	private void init()
	{
		for(int i=0; i<taille; i++)
			for(int j=0; j<taille; j++)
				grille[i][j] = new Cellule(grille, i, j);
	}
	
	/**cree des obstacles dans le terraint
	 * @param tauxObstacles pourcentage (entre 0 et 1) d'obstacles a creer*/
	private void initObstacle(double tauxObstacles)
	{
		Random r = new Random();
		for(int i=0; i<taille; i++)
			for(int j=0; j<taille; j++)
				if(r.nextDouble()<tauxObstacles) grille[i][j].setObstacle(true);
	}

	

	/**cree la caisse à rechercher */
	private void initTresor()
	{
		Random r = new Random();
		 pointTresor = new Point(r.nextInt(taille), r.nextInt(taille/2));
		 grille[pointTresor.x][pointTresor.y].setTresor(true);
	}
	
	/**cree l'intrus */
	private void initIntrus()
	{
		Random r = new Random();
		intrus = new Intrus(r.nextInt(taille), taille-10, this);
	}
	
	
	/**cree les robots
	 * @param nb nombre de robots*/
	private void initRobots(int nb)
	{
		Random r = new Random();
		lesRobots = new Robot[nb];
		Arrays.setAll(lesRobots, i->new Robot(r.nextInt(taille), r.nextInt(taille), this));
	}

	/**  
	 * demande a tous les robots du terrain  d'evoluer, 
	 */
	public void animGrille()
	{
		for(Robot f:lesRobots) f.evoluer();
	}

	/**
	 * @return the grille
	 */
	public Cellule[][] getGrille() {
		return grille;
	}


	/**
	 * @return the taille
	 */
	public int getTaille() {
		return taille;
	}

	/**
	 * @return nb de robots
	 */
	public int getNbRobots() {
		return nbRobots;
	}


	/**
	 * @return l'ensemble des robots
	 */
	public Robot[] getLesRobots() {
		return lesRobots;
	}


	public Intrus getIntrus() {
		return intrus;
	}


	public void setIntrus(Intrus intrus) {
		this.intrus = intrus;
	}
}
