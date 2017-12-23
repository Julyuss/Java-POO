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
	private int nbRobots;
	/**taux d'obstacle*/
	private double tauxObstacles;
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
	public Terrain(int taille, int _nbRobots, double _tauxObstacles)
	{
		this.taille= taille;
		this.tauxObstacles = _tauxObstacles;
		grille = new Cellule[taille][taille];
		this.nbRobots = _nbRobots ;
		init();
		initTresor();
		initSortie();
		initObstacle(tauxObstacles);
		initRobots(nbRobots);
		initIntrus();
	}
	
	/**
	 * Constructeur utile pour charger le terrain depuis une sauvegarde
	 * @param _taille Taille du terrain
	 * @param _nbRobots Nombre de robots
	 * @param _tauxObstacles Taux d'obstacles
	 * @param _x Position en x de l'intrus
	 * @param _y Position en y de l'intrus
	 * @param etat Etat de l'intrus
	 * @param pointTresor Position du tresor
	 */
	public Terrain(int _taille, int _nbRobots, double _tauxObstacles, int _x, int _y, EtatIntrus etat, Point pointTresor) {
		
		taille = _taille;
		tauxObstacles = _tauxObstacles;
		grille = new Cellule[taille][taille];
		nbRobots = _nbRobots;
		init();
		initTresor(pointTresor);
		initSortie();
		initObstacle(tauxObstacles);
		initRobots(nbRobots);
		initIntrus(_x, _y, etat);
	}


	/** 
	 * initialise les sorties du terrain
	 */
	private void initSortie() {
		
		grille[0][taille/2].setSortie(true);
		grille[taille/2][0].setSortie(true);
		grille[taille-1][taille/2].setSortie(true);
		grille[taille/2][taille-1].setSortie(true);
	}
	
	
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
				if(r.nextDouble()<tauxObstacles && !grille[i][j].isSortie() && !grille[i][j].isTresor()) 
					grille[i][j].setObstacle(true);
	}

	

	/**cree la caisse à rechercher */
	private void initTresor()
	{
		Random r = new Random();
		 pointTresor = new Point(r.nextInt(taille), r.nextInt(taille/2));
		 grille[pointTresor.x][pointTresor.y].setTresor(true);
	}
	
	private void initTresor(Point _pointTresor) {
		
		pointTresor = _pointTresor;
		grille[pointTresor.x][pointTresor.y].setTresor(true);
	}
	
	/**cree l'intrus */
	private void initIntrus()
	{
		Random r = new Random();
		intrus = new Intrus(r.nextInt(taille), taille-10, this);
	}
	
	private void initIntrus(int x, int y, EtatIntrus etat) {
		
		intrus = new Intrus(x, y, this, etat);
	}
	
	
	/**cree les robots
	 * @param nb nombre de robots*/
	private void initRobots(int nb)
	{
		Random r = new Random();
		lesRobots = new Robot[nb];
		Arrays.setAll(lesRobots, i->new Robot(r.nextInt(taille), r.nextInt(taille), this));
	}
	
	public void changerEtatIntrus(EtatIntrus etat) {
		
		intrus.setEtat(etat);
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
	
	public double getTauxObstacles() {
		
		return tauxObstacles;
	}
	
	public Point getTresor() {
		
		return pointTresor;
	}
}
